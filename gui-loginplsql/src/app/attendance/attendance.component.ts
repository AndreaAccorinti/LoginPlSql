import {Component, HostListener, OnInit} from '@angular/core';
import {DatePipe} from "@angular/common";
import {AttendanceService} from "./attendance.service";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import { NbDialogService } from '@nebular/theme';
import { AttendanceDialogComponent } from '../attendance-dialog/attendance-dialog.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Attendance } from '../model/model';


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
    constructor(private datePipe: DatePipe, private service: AttendanceService,
                private router: Router, private formBuilder: FormBuilder,
                private dialogService: NbDialogService,
                private modalService: NgbModal ) {
      this.title = "Attendance";
      this.form = this.formBuilder.group({
        selectController: new FormControl(null)
      });
    }

    ngOnInit() {
      this.service.getMonthList().subscribe(data => {
        this.month_list = data.monthList;
        this.form.setValue( { selectController: this.month_list[this.month_list.length - 1].month_format });
      });
      this.service.getAttendanceForSys().subscribe(data => {
        this.data = data.attendanceList;
      },
        (error) => {
          this.router.navigate(['/login'])
        });
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

}
