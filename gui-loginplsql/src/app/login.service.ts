import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {enviroments} from "./environments/environments";
import {catchError, Observable, tap} from "rxjs";
import {User} from "./assets/user";
import {HttpResponseToken} from "./environments/HttpResponseToken";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  baseUrl: String = enviroments.urlApi;
  constructor(
    private http: HttpClient
  ) {}

  insertUser(user: User): Observable<any> {
    return this.http.post(this.baseUrl + "add-user", user,{
      headers: {
        'Access-Control-Allow-Origin': '*'
      }});
  }

  login(user: User): Observable<any> {
    return this.http.post(this.baseUrl+"login", user)
      .pipe(
        tap(response => {
          let r: HttpResponseToken = response as HttpResponseToken;
          localStorage.setItem('access_token', r.token);
        }),
        catchError(err => { return  err.error.response })
      );
  }
}
