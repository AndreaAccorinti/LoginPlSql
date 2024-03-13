import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {sidebar_list} from "../environments/environments";
import { UserSession } from '../environments/HttpResponseToken';
import { UserSessionService } from '../session/user-session.service';
import { InitialsPipe } from '../pipe/initials.pipe';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.scss']
})
export class SidebarComponent implements OnInit{
    @Output() clicked: EventEmitter<number>;
    userData: UserSession|null = null;
    constructor(
      private userSessionService: UserSessionService
    ) {
      this.clicked = new EventEmitter<number>();
    }
    sidebar_list: any = sidebar_list;

    ngOnInit(): void {
      const jsonData = localStorage.getItem('user');
      if (jsonData != null)
        this.userData = JSON.parse(jsonData);
    }

  emitEvent(event: number) {
      this.clicked.emit(event);
    }

}
