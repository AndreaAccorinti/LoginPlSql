import {Component, OnInit} from '@angular/core';
import {AttendanceService} from "./summary.service";
import {User} from "../model/user";
import {UserService} from "../assets/service/user.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.scss']
})
export class SummaryComponent implements OnInit {
  users: User[] = [];
  monthForm: FormGroup;
  month_list = [
    {month_name: 'January', month_number: "01"},
    {month_name: 'February', month_number: "02"},
    {month_name: 'March', month_number: "03"},
    {month_name: 'April', month_number: "04"},
    {month_name: 'May', month_number: "05"},
    {month_name: 'June', month_number: "06"},
    {month_name: 'July', month_number: "07"},
    {month_name: 'August', month_number: "08"},
    {month_name: 'September', month_number: "09"},
    {month_name: 'October', month_number: "10"},
    {month_name: 'November', month_number: "11"},
    {month_name: 'December', month_number: "12"}
  ];

  year_list: number[] = [];

  constructor(private attendanceService: AttendanceService,
              private userService: UserService,
              private fb: FormBuilder) {
    this.monthForm = this.fb.group({
      monthController: [''],
      yearController: ['']
    });

    this.initializeYearList();
  }

  ngOnInit(): void {
    this.getTheUsers();
    this.onDateChange();
  }
  initializeYearList() {
    const currentYear = new Date().getFullYear();
    for (let year = currentYear - 5; year <= currentYear; year++) {
      this.year_list.push(year);
    }
  }
  private getTheUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        console.error('Failed to fetch users', error);
      }
    });
  }

  private onDateChange() {
    this.monthForm.valueChanges.subscribe(({ monthController, yearController }) => {
      if (monthController && yearController) {
        this.updateWorkdaysForMonth(yearController, monthController);
      }
    });
  }

  private updateWorkdaysForMonth(year: number, month: string) {
    const date = `${year}-${month}-01`;
    this.attendanceService.getTotalMonthlyWorkingDays(date).subscribe({
      next: workdays => {
        this.users = this.users.map(user => ({ ...user, monthlyWorkdays: workdays.toString() }));
      },
      error: error => console.error('Error fetching monthly workdays', error)
    });
  }

}
