import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../login.service";
import {User} from "../assets/user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  disableLogin: boolean = true;
  user: User;
  loginForm: FormGroup = new FormGroup({
    password: new FormControl<string>('',
      [
        Validators.maxLength(20),
        Validators.required]
    ),
    mail: new FormControl<string>('',
      [Validators.email, Validators.required])
  });
  constructor(private service: LoginService) {
    this.user = new User();
  }

  login(): void {
    this.user.email = this.loginForm.controls['mail'].value;
    this.user.password = this.loginForm.controls['password'].value;

  }

  changeInput(event: Event, eventName: string): void {
    this.disableLogin = !this.loginForm.valid;
  }

}
