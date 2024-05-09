import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { catchError, Observable } from "rxjs";
import { User } from "../assets/user";
import { environments } from "../environments/environments";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environments.urlApi;

  constructor(private http: HttpClient) {}

  private get headers(): HttpHeaders {
    const token = localStorage.getItem('access_token') || '';
    return new HttpHeaders().set('response', token);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}users_list`, { headers: this.headers })
      .pipe(catchError(err => {
        console.error('Error fetching users', err);
        throw err;
      }));
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}add-user`, user, { headers: this.headers })
      .pipe(catchError(err => {
        console.error('Error adding new user', err);
        throw err;
      }));
  }

  updateUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}update-user/${user.id}`, user, { headers: this.headers })
      .pipe(catchError(err => {
        console.error('Error updating user', err);
        throw err;
      }));
  }

  deleteUser(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}delete-user/${id}`, { headers: this.headers })
      .pipe(catchError(err => {
        console.error('Error deleting user', err);
        throw err;
      }));
  }
}
