import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserFa} from "../../../../model/lang.fa";
import {PageType, UserModel} from "../../../../model/model";
import {UserFormComponent} from "./user-form.component";

@Component({
  selector: 'bs-change-password-dialog',
  template: '<mat-card mat-dialog-content style="margin: 3px;">'+
    '<mat-card-title>{{title}}</mat-card-title>' +
    '<mat-card-content>' +
    '<bs-user-form [type]="data.type" [patchValue]="value" (submitted)="onSubmitted($event)"></bs-user-form>' +
    '</mat-card-content>' +
    '</mat-card>'
})
export class UserDialogComponent implements OnInit {

  langFa: UserFa;
  title: string;
  value: UserModel;

  constructor(public dialogRef: MatDialogRef<UserFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: UserDialogModel) {
    this.langFa = new UserFa();
    this.title = this.langFa.pageTitle + ' ' + data.model.username;
  }

  onSubmitted(model: UserModel) {
    this.dialogRef.close(model);
  }

  ngOnInit(): void {
    if (this.data && this.data.type && this.data.type === PageType.EDIT) {
      this.value = this.data.model;
    }
  }
}

class UserDialogModel {
  type: PageType;
  model: UserModel;
}
