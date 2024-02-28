import {Component, EventEmitter, Output} from '@angular/core';
import {sidebar_list} from "../environments/environments";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.scss']
})
export class SidebarComponent {
    @Output() clicked: EventEmitter<number>;
    constructor() {
      this.clicked = new EventEmitter<number>();
    }
    sidebar_list: any = sidebar_list;

  emitEvent(event: number) {
      this.clicked.emit(event);
    }

}
