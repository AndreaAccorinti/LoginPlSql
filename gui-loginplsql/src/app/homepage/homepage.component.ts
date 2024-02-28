import { Component } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {
  sidebarSelected: number;
  constructor() {
    this.sidebarSelected = 0;
  }
  onChangeSidebar(sidebar_selected : number) {
    this.sidebarSelected = sidebar_selected;
  }
}
