import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {enviroments} from "../environments/environments";
import {catchError, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private baseUrl = enviroments.urlApi;
  constructor(private http: HttpClient) {}

  getAttendanceForSys(): Observable<any> {
    const token = localStorage.getItem('access_token')
    if (token != null) {
      const header = new HttpHeaders().set('headers', token)
      return this.http.post(this.baseUrl+'attendance_list_systmp', {}, {headers: header})
        .pipe(catchError(err => {return err;}));
    }
    return new Observable<any>();
  }

}
