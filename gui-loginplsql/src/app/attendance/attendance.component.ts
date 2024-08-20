import {Component, HostListener, OnInit} from '@angular/core';
import {DatePipe} from "@angular/common";
import {AttendanceService} from "./attendance.service";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import { NbDialogService } from '@nebular/theme';
import { AttendanceDialogComponent } from '../attendance-dialog/attendance-dialog.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Attendance } from '../model/model';
import { UserService } from '../assets/service/user.service';
import { User } from '../model/user';


@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss']
})
export class AttendanceComponent implements OnInit{
    title: string;
    data: Attendance[] = [];
    month_list: any[] = [];
    form: FormGroup;
    modalRef: NgbModalRef | undefined;
    user_list: User[] = [];

    constructor(private datePipe: DatePipe, private service: AttendanceService,
                private router: Router, private formBuilder: FormBuilder,
                private dialogService: NbDialogService,
                private modalService: NgbModal,
                private userService: UserService ) {
      this.title = "Attendance";
      this.form = this.formBuilder.group({
        mese: new FormControl(null),
        user: new FormControl(null)
      });
    }

    ngOnInit() {
      this.getTheUsers();
    }

  @HostListener('scroll', ['$event'])
    onScroll(event: Event) {
      const container = event.target as HTMLElement;
      const thead = document.getElementById('thead-x');
      if (thead != null) {
        if (container.scrollTop > 0) {
          thead.classList.remove('thead-fix');
          thead.classList.add('thead-sticky');
        } else {
          thead.classList.remove('thead-sticky');
          thead.classList.add('thead-fix');
        }
      }
    }

    openCsvModal() {
      this.modalRef = this.modalService.open(AttendanceDialogComponent);
      this.modalRef.componentInstance.data = this.data;
      this.modalRef.closed.subscribe( ()=> { this.ngOnInit() });
    }

    private getTheUsers() {
      
      this.userService.getAllUsers().subscribe({
        next: (data) => {
          this.user_list = data;
          this.getMesi();
        },
        error: (error) => {
          console.error('Failed to fetch users', error);
        }
      });
    }

    private getMesi() {
      const jsonData = localStorage.getItem('user');
      let userData: User = new User();
      if (jsonData != null)
        userData = JSON.parse(jsonData);
      this.service.getMonthList().subscribe(data => {
        this.month_list = data.monthList;
        this.form.setValue( { 
          mese: this.month_list[this.month_list.length - 1].month_format,
          user: userData.id 
         });
        this.getPresenze();
      });
    }

    getPresenze() {
      const data: string = this.form.get('mese')?.value;
      const user = this.form.get('user')?.value;
      if (data) {
        const request = {
          mese: data.split('-')[1],
          anno: data.split('-')[0],
          idUser: user
        }

        this.service.getAttendanceForSys(request).subscribe({
          next: (data) => {
            this.data = data.attendanceList;
          },
          error: (error) => {
            this.router.navigate(['/login']);
          }
        });
      }
    }

}
