import {Component, HostListener, OnInit} from '@angular/core';
import {DatePipe} from "@angular/common";
import {AttendanceService} from "./attendance.service";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import { NbDialogService } from '@nebular/theme';
import { AttendanceDialogComponent } from '../attendance-dialog/attendance-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss']
})
export class AttendanceComponent implements OnInit{
    title: string;
    data: any[] = [];
    month_list: any[] = [];
    form: FormGroup;
    constructor(private datePipe: DatePipe, private service: AttendanceService,
                private router: Router, private formBuilder: FormBuilder,
                private dialogService: NbDialogService,
                private modalService: NgbModal ) {
      this.title = "Attendance";
      this.form = this.formBuilder.group({
        selectController: new FormControl('')
      });
    }

    ngOnInit() {
      this.service.getSelectStyle().subscribe(data => {
        const style = document.createElement('style');
        style.type = 'text/css';
        style.innerHTML = data;
      });
      this.service.getMonthList().subscribe(data => {
        this.month_list = data.monthList;
        console.log(this.month_list);
      });
      this.service.getAttendanceForSys().subscribe(data => {
        this.data = data.attendanceList;
      },
        (error) => {
          this.router.navigate(['/login'])
        })
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
      this.modalService.open(AttendanceDialogComponent);
    }

}
