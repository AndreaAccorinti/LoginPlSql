import {Component, OnInit} from '@angular/core';
import {UserService} from "./user.service";
import {User} from "../assets/user";

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.scss']
})
  export class RegistryComponent implements OnInit {
  users: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        console.error('Failed to fetch users', error);
      }
    });
  }
}
