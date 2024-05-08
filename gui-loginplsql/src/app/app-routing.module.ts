import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";
import {HomepageComponent} from "./homepage/homepage.component";
import {SessionGuardService} from "./session-guard.service";
import {AppComponent} from "./app.component";

const routes: Routes = [
  {path:"test-lit", component: AppComponent},
  {path: "homepage", component: HomepageComponent, canActivate: [SessionGuardService]},
  {path:"login", component: LoginComponent},
  {path: "signup", component: SignupComponent},
  {path:"**", redirectTo: "login"}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
