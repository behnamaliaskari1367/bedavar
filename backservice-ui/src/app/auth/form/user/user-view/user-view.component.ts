import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserModel} from "../../../../model/model";
import {UserFa} from "../../../../model/lang.fa";

@Component({
  selector: 'bs-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {
  @Input() userData: UserModel = new UserModel();
  @Output() formClosed = new EventEmitter<boolean>();
  userForm: FormGroup;
  langFa = new UserFa();

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.userForm = this.formBuilder.group({
      username: [this.userData.username],
      firstName: [this.userData.firstName],
      lastName: [this.userData.lastName],
      branch: [this.userData.branch],
      enabled: [{value: this.userData.enabled, disabled: true}]
    });
  }

  closeForm($event: MouseEvent) {
    $event.preventDefault();
    this.formClosed.emit(true);
  }
}
