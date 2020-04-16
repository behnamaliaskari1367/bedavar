import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserModel} from "../../../../model/model";
import {UserFa} from "../../../../model/lang.fa";

@Component({
  selector: 'bs-user-view-dialog',
  template: '<mat-card mat-dialog-content style="margin: 3px;">' +
    '<mat-card-title>{{langFa.pageTitle}}</mat-card-title>' +
    '<mat-card-content>' +
    '<bs-user-view [userData]="userData" (formClosed)="onFormClosed($event)"></bs-user-view>' +
    '</mat-card-content>' +
    '</mat-card>'
})
export class UserViewDialogComponent {

  langFa: UserFa;

  constructor(public dialogRef: MatDialogRef<UserViewDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public userData: UserModel) {
    this.langFa = new UserFa();
  }

  onFormClosed($event: boolean) {
    this.dialogRef.close();
  }
}
