import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

@Injectable({
  providedIn: 'root'
})

export class SessionGuardService implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean  {
        const isLoggedIn = localStorage.getItem('access_token');
        if (!isLoggedIn) {
          this.router.navigate(['/login']);
          return false;
        }
        return true;
    }
}
