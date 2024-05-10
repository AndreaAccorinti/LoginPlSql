import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../login.service";
import {User} from "../model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  disableLogin: boolean = true;
  user: User;
  error_message: string;
  loginForm: FormGroup = new FormGroup({
    password: new FormControl<string>('',
      [
        Validators.maxLength(20),
        Validators.required]
    ),
    mail: new FormControl<string>('',
      [Validators.email, Validators.required])
  });
  constructor(private service: LoginService, private route: Router) {
    this.user = new User();
    this.error_message = "";
  }

  login(): void {
    this.user.email = this.loginForm.controls['mail'].value;
    this.user.password = this.loginForm.controls['password'].value;
    this.service.login(this.user).subscribe(
      response => {
        if (response.response == "Login successful") {
          this.route.navigate(['/homepage']);
          this.error_message = "";
        }
        else
          this.error_message = "Invalid username or password!";
      }
    );
  }

  changeInput(event: Event, eventName: string): void {
    this.disableLogin = !this.loginForm.valid;
  }

}
