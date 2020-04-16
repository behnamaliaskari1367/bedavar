import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MyErrorStateMatcher} from "../../model/error-state-matcher";
import {ChangePasswordModel, PageType} from "../../model/model";
import {ChangePasswordFa} from "../../model/lang.fa";

@Component({
  selector: 'bs-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  @Input() username: string;
  @Input() type: PageType;
  @Output() submitted = new EventEmitter<ChangePasswordModel>();

  formGroup: FormGroup;
  matcher = new MyErrorStateMatcher();
  langFa = new ChangePasswordFa();

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
        type: [this.type],
        username: [this.username, Validators.required],
        newPassword: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
        oldPassword: [''],
      }, {
        validators: [ChangePasswordComponent.checkPasswords, ChangePasswordComponent.checkOldPassword]
      }
    );
  }

  private static checkOldPassword(group: FormGroup) {
    let type = group.get("type").value;
    if (type === PageType.RESET) return null;

    let oldPassword: string = group.get('oldPassword').value;
    return oldPassword.length > 1 ? null : {oldPassword: true};
  };

  private static checkPasswords(group: FormGroup) {
    let pass = group.get('newPassword').value;
    let confirmPass = group.get('confirmPassword').value;
    return pass === confirmPass ? null : {notSame: true};
  };

  onFormSubmit(model: ChangePasswordModel) {
    this.submitted.emit(model);
  }

  closeForm($event: MouseEvent) {
    this.submitted.emit(null);
  }
}
