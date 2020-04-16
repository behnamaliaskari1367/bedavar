import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserFa} from "../../../../model/lang.fa";
import {PageType, UserModel} from "../../../../model/model";
import {MyErrorStateMatcher} from "../../../../model/error-state-matcher";

/**
 * @author Abbas Alishirvani
 */
@Component({
  selector: 'bs-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {


  private _value: UserModel;


  @Output() submitted: EventEmitter<UserModel>;

  formGroup: FormGroup;
  matcher = new MyErrorStateMatcher();
  langFa = new UserFa();
  @Input() showSubmitButtons: boolean = true;
  @Input() showCancelButtons: boolean = true;
  @Input() showSearchButtons: boolean = false;
  @Input() showResetButtons: boolean = false;
  @Input() type: PageType;

  @Input() set patchValue(value: UserModel) {
    this._value = value;
    if (value && this.formGroup) {
      this.formGroup.patchValue(value);
    }
  }

  constructor(private fb: FormBuilder) {
    this.submitted = new EventEmitter();
  }

  private initForm() {
    this.formGroup = this.fb.group({
      version: [null],
      id: [null],
      username: [null, (this.type !== PageType.SEARCH) ? Validators.required : null],
      password: [null, (this.type === PageType.NEW) ? Validators.required : null],
      retryPassword: [null, (this.type === PageType.NEW) ? Validators.required : null],
      firstName: [null],
      lastName: [null],
      branch: [null],
      type: [this.type],
      enabled: new FormControl({value: null, disabled: this.type === PageType.VIEW})
    }, {
      validators: [UserFormComponent.checkPasswords]
    });

    if (this._value && this.formGroup) {
      this.formGroup.patchValue(this._value);
    }
  }

  private static checkPasswords(group: FormGroup) {
    let type = group.get('type').value;
    if (type && type === PageType.NEW) {
      let pass = group.get('password').value;
      let confirmPass = group.get('retryPassword').value;
      return pass === confirmPass ? null : {notSame: true};
    } else {
      return null;
    }
  };

  ngOnInit(): void {
    this.initForm();
  }

  onFormSubmit(value: any) {
    this.submitted.emit(value);
  }

  cancelForm($event: MouseEvent) {
    $event.preventDefault();
    this.submitted.emit(null);
  }

  searchClicked($event: MouseEvent) {
    $event.preventDefault();
    this.submitted.emit(this.formGroup.value);
  }

  resetClicked($event: MouseEvent) {
    $event.preventDefault();
    this.formGroup.reset();
  }
}
