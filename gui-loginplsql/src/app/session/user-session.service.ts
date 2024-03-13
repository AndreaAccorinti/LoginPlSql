import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UserSession } from '../environments/HttpResponseToken';

@Injectable({
  providedIn: 'root'
})
export class UserSessionService {
  private sessionDataSubject = new BehaviorSubject<UserSession|null>(null);
  sessionData$ = this.sessionDataSubject.asObservable();

  setUserData(data: UserSession) {
    this.sessionDataSubject.next(data);
  }

  getUserData(): UserSession|null {
    return this.sessionDataSubject.getValue();
  }
}
