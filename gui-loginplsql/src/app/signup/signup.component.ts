import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../assets/user";
import {LoginService} from "../login.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  user: User;
  disableSingUp: boolean = true;
  singnUpForm: FormGroup = new FormGroup({
    username: new FormControl<string>('',
      [
        Validators.maxLength(20),
        Validators.required ]),
    password: new FormControl<string>('',
      [
        Validators.maxLength(20),
        Validators.required]
    ),
    mail: new FormControl<string>('',
      [Validators.email, Validators.required]),
    name: new FormControl<string>('',
      [Validators.maxLength(20), Validators.required])
  });
  constructor(private service: LoginService) {
    this.user = new User();
  }
  ngOnInit() {}

  singup(): void {
    this.user.email = this.singnUpForm.controls['mail'].value;
    this.user.password = this.singnUpForm.controls['password'].value;
    this.user.username = this.singnUpForm.controls['username'].value;
    this.user.name = this.singnUpForm.controls['name'].value;
    console.log(this.user);
    this.service.insertUser(this.user).subscribe(
      {
        next: (data) => {
          console.log(data)
        },
        error: (err) => {
          console.log(err)
        }
      }
    );

  }

  changeInput(event: Event, eventName: string): void {
    this.disableSingUp = !this.singnUpForm.valid;
  }

}
