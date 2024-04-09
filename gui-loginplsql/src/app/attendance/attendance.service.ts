import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {enviroments} from "../environments/environments";
import {catchError, Observable} from "rxjs";
import { Attendance } from "../model/model";

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private baseUrl = enviroments.urlApi;
  private token = localStorage.getItem('access_token')
  constructor(private http: HttpClient) {}

  getAttendanceForSys(): Observable<any> {

    if (this.token != null) {
      const header = new HttpHeaders().set('headers', this.token)
      return this.http.post(this.baseUrl+'attendance_list_systmp', {}, {headers: header})
        .pipe(catchError(err => {return err;}));
    }
    return new Observable<any>();
  }

  getMonthList(): Observable<any> {
    if (this.token != null) {
      const header = new HttpHeaders().set('loginResponse', this.token);
      return this.http.get(this.baseUrl+'sysdatetime/month-list', {headers: header})
        .pipe( catchError(err => {return err}));
    }
    return new Observable<any>();
  }

  addAttendances(attendances: Attendance []): Observable<any> | null {
    if (this.token != null) {
      const header = new HttpHeaders().set('response', this.token);
      return this.http.post(this.baseUrl+'add-attendances', attendances, {headers: header})
        .pipe( catchError(err => {return err}));
    }
    return null;
  }

  getSelectStyle(): Observable<any> {
    const url = '../../assets/select-style.json';
    return this.http.get(url);
  }

}
