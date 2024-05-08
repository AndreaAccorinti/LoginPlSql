import {Component, OnInit} from '@angular/core';
import {UserService} from "./user.service";
import {User} from "../assets/user";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {RegistryDialogComponent} from "../registry-dialog/registry-dialog.component";

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.scss']
})
export class RegistryComponent implements OnInit {
  users: User[] = [];
  modalRef: NgbModalRef | undefined;

  constructor(private userService: UserService,
              private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getTheUsers();
  }


  private getTheUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        console.error('Failed to fetch users', error);
      }
    });
  }

  openNewUserModal() {
    this.modalRef = this.modalService.open(RegistryDialogComponent);
    this.modalRef.componentInstance.data = this.users;
    this.modalRef.closed.subscribe(() => {
      this.ngOnInit()
    });
  }

  openEditUserModal(user: User): void {
    const modalRef = this.modalService.open(RegistryDialogComponent);
    modalRef.componentInstance.user = user;
    modalRef.result.then((result) => {
      if (result) {
        this.getTheUsers();
      }
    });
  }
}
