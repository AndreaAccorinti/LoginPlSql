import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../model/user";
import {UserService} from "../assets/service/user.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-registry-dialog',
  templateUrl: './registry-dialog.component.html',
  styleUrls: ['./registry-dialog.component.scss']
})
export class RegistryDialogComponent implements OnInit {
  @Input() user?: User;
  disableAddNewUser: boolean = true;
  newUserForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.maxLength(20), Validators.required]),
    password: new FormControl('', [Validators.maxLength(20), Validators.required]),
    email: new FormControl('', [Validators.email, Validators.required]),
    name: new FormControl('', [Validators.maxLength(20), Validators.required]),
    surname: new FormControl('', [Validators.maxLength(20)]),
    telephone: new FormControl(''),
    role: new FormControl('', [Validators.required])
  });

  constructor(private userService: UserService,
              protected activeModal: NgbActiveModal) {
    this.updateAddUserButtonStatus();
  }

  ngOnInit(): void {
    this.patchFormWithUserData();
  }

  private patchFormWithUserData() {
    if (this.user) {
      this.newUserForm.patchValue(this.user);
    }
  }

  private updateAddUserButtonStatus() {
    this.newUserForm.statusChanges.subscribe(status => {
      this.disableAddNewUser = status !== 'VALID';
    });
  }

  saveUser(): void {
    if (this.newUserForm.valid) {
      const userData: User = this.newUserForm.value;
      if (this.user) {
        this.updateExistingUser(userData);
      } else {
        this.addUser(userData);
      }
    }
  }

  private addUser(userData: User) {
    this.userService.addUser(userData).subscribe({
      next: () => {
        this.activeModal.close(true);
      },
      error: (err) => {
        console.error('Error adding new user:', err);
        this.activeModal.dismiss('error');
      }
    });
  }

  private updateExistingUser(userData: User) {
    if (this.user) {
      this.userService.updateUser({...userData, id: this.user.id}).subscribe({
        next: () => {
          this.activeModal.close(true);
        },
        error: (err) => {
          console.error('Error updating user:', err);
          this.activeModal.dismiss('error');
        }
      });
    } else {
      console.error('No user to update');
      this.activeModal.dismiss('error');
    }
  }

}
