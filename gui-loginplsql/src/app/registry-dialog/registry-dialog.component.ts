import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../assets/user";
import {UserService} from "../registry/user.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-registry-dialog',
  templateUrl: './registry-dialog.component.html',
  styleUrls: ['./registry-dialog.component.scss']
})
export class RegistryDialogComponent  implements OnInit{
  @Input() user?: User;
  disableAddNewUser: boolean = true;
  newUserForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.maxLength(20), Validators.required]),
    password: new FormControl('', [Validators.maxLength(20), Validators.required]),
    email: new FormControl('', [Validators.email, Validators.required]),
    name: new FormControl('', [Validators.maxLength(20), Validators.required]),
    surname: new FormControl('', [Validators.maxLength(20)]),
    telephone: new FormControl(''),
    role: new FormControl('')
  });

  constructor(private userService: UserService,
              protected activeModal: NgbActiveModal) {
    this.newUserForm.statusChanges.subscribe(status => {
      this.disableAddNewUser = status !== 'VALID';
    });
  }
  ngOnInit(): void {
    if (this.user) {
      this.newUserForm.patchValue(this.user);
    }
  }

  saveUser(): void {
    if (this.newUserForm.valid) {
      const userData: User = this.newUserForm.value;
      if (this.user) {
        // Update existing user
        this.userService.updateUser({ ...userData, id: this.user.id }).subscribe({
          next: () => {
            this.activeModal.close(true);
          },
          error: (err) => {
            console.error('Error updating user:', err);
            this.activeModal.dismiss('error');
          }
        });
      } else {
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
    }
  }
}