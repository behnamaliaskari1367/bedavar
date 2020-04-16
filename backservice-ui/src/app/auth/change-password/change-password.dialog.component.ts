import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ChangePasswordModel} from "../../model/model";
import {ChangePasswordFa} from "../../model/lang.fa";

@Component({
  selector: 'bs-change-password-dialog',
  template: '<mat-card mat-dialog-content style="margin: 3px;">' +
    '<mat-card-title>{{title}}</mat-card-title>' +
    '<mat-card-content>' +
    '<bs-change-password [type]="data.type"  [username]="data.username" (submitted)="onPasswordSubmitted($event)"></bs-change-password>' +
    '</mat-card-content>' +
    '</mat-card>'
})
export class ChangePasswordDialogComponent {

  langFa: ChangePasswordFa;
  title: string;

  constructor(public dialogRef: MatDialogRef<ChangePasswordDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ChangePasswordModel) {
    this.langFa = new ChangePasswordFa();
    this.title = this.langFa.pageTitle + ' ' + data.username;
  }

  onPasswordSubmitted(model: ChangePasswordModel) {
    if (model && this.data) {
      model.id = this.data.id;
    }
    this.dialogRef.close(model);
  }
}
