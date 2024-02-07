import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {enviroments} from "./environments/environments";
import {Observable} from "rxjs";
import {User} from "./assets/user";

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
}
