import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable} from "rxjs";
import {User} from "../assets/user";
import {environments} from "../environments/environments";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environments.urlApi;
  private token = localStorage.getItem('access_token');

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get<User[]>(`${this.baseUrl}users_list`, { headers: headers })
      .pipe(catchError(err => {
        console.error('Error fetching users', err);
        throw err;
      }));
  }

  addUser(user: User): Observable<User> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post<User>(`${this.baseUrl}add-user`, user, { headers })
      .pipe(catchError(err => {
        console.error('Error adding new user', err);
        throw err;
      }));
  }
}
