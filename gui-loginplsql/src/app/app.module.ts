import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { JwtModule } from '@auth0/angular-jwt';
import { DatePipe } from "@angular/common";
import {
  NbThemeModule,
  NbCardModule,
  NbInputModule,
  NbButtonModule,
  NbLayoutModule,
  NbSidebarModule,
  NbDialogModule
} from "@nebular/theme";
import { LoginComponent } from './login/login.component';
import {RouterOutlet} from "@angular/router";
import {HttpClientModule, HttpHeaders} from "@angular/common/http";
import { SidebarComponent } from './sidebar/sidebar.component';
import {enviroments} from "./environments/environments";
import { HomepageComponent } from './homepage/homepage.component';
import {SessionGuardService} from "./session-guard.service";
import { HeaderComponent } from './header/header.component';
import { AttendanceComponent } from './attendance/attendance.component';
import { TestLitComponent } from './test-lit/test-lit.component';
import {MyLitComponent} from "../assets/my-lit-component";
import { InitialsPipe } from './pipe/initials.pipe';
import { AttendanceDialogComponent } from './attendance-dialog/attendance-dialog.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarModule, DateAdapter } from 'angular-calendar';
//import { adapterFactory } from 'angular-calendar/date-adapters/moment';
import * as moment from 'moment';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

//export function momentAdapterFactory() {
  //return adapterFactory(moment);
//};
export function tokenGetter() {
  return localStorage.getItem('access_token');
}
@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    SidebarComponent,
    HomepageComponent,
    HeaderComponent,
    AttendanceComponent,
    TestLitComponent,
    InitialsPipe,
    AttendanceDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    NbThemeModule.forRoot(),
    NbCardModule,
    NbInputModule,
    NbButtonModule,
    NbLayoutModule,
    NbSidebarModule.forRoot(),
    RouterOutlet,
    HttpClientModule,
    NbDialogModule.forRoot(),
    JwtModule.forRoot( {
      config: {
        tokenGetter,
        allowedDomains: [enviroments.urlApi], // Replace with your backend domain
        disallowedRoutes: [enviroments.urlApi + 'public']
      }
    }),
    NgbModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory  })
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [SessionGuardService, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
