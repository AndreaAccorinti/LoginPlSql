import {Component, HostListener, OnInit} from '@angular/core';
import {mock_attandance} from "../environments/environments";
import {DatePipe} from "@angular/common";
import {AttendanceService} from "./attendance.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss']
})
export class AttendanceComponent implements OnInit{
    title: string;
    data: any[] = [];
    constructor(private datePipe: DatePipe, private service: AttendanceService,
                private router: Router) {
      this.title = "Attendance";

    }

    ngOnInit() {
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

}
