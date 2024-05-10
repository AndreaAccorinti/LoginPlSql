import {Component, HostListener, OnInit} from '@angular/core';
import {UserService} from "../assets/service/user.service";
import {User} from "../model/user";
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

  @HostListener('scroll', ['$event'])
  onScroll(event: Event) {
    const container = event.target as HTMLElement;
    const thead = document.getElementById('thead-x');
    if (thead != null) {
      if (container.scrollTop > 0) {
        thead.classList.remove('thead-fix');
        thead.classList.add('thead-sticky');
      } else {
        thead.classList.remove('thead-sticky');
        thead.classList.add('thead-fix');
      }
    }
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

  deleteUser(userId: string) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(userId).subscribe({
        next: () => {
          alert('User deleted successfully');
          this.getTheUsers(); // Refresh the list after deletion
        },
        error: (error) => console.error('Error deleting user', error)
      });
    }
  }
}
