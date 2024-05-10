import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { catchError, Observable } from "rxjs";
import { environments } from "../environments/environments";

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private baseUrl = environments.urlApi;

  constructor(private http: HttpClient) {}

  private get headers(): HttpHeaders {
    const token = localStorage.getItem('access_token') ?? '';
    return new HttpHeaders().set('response', token);
  }

  getTotalMonthlyWorkingDays(date: string): Observable<number> {
    const params = { date: date };
    return this.http.get<number>(`${this.baseUrl}get-total-monthly-working-days`, { headers: this.headers, params })
      .pipe(catchError(err => {
        console.error('Error fetching total monthly working days', err);
        throw err;
      }));
  }

  getUserMonthlyAttendance(userId: number, description: string, month: number): Observable<number> {
    const params = { userId: userId.toString(), description, month: month.toString() };
    return this.http.post<number>(`${this.baseUrl}get-user-monthly-attendance-for-description`, {}, { headers: this.headers, params })
      .pipe(catchError(err => {
        console.error('Error fetching user monthly attendance', err);
        throw err;
      }));
  }

  checkUserMonthlyStatus(userId: number, yearMonth: string): Observable<boolean> {
    const params = { userId: userId.toString(), yearMonth };
    return this.http.get<boolean>(`${this.baseUrl}check-user-monthly-status`, { headers: this.headers, params })
      .pipe(catchError(err => {
        console.error('Error checking user monthly status', err);
        throw err;
      }));
  }
}
