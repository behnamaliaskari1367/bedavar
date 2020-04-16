import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {PageType, ScheduleParamModel} from "../../../model/model";
import {ScheduleParamFa} from "../../../model/lang.fa";
import {SchedulerService} from "../../service/scheduler.service";

@Component({
  selector: 'bs-user-view-dialog',
  template: '<mat-card mat-dialog-content style="margin: 3px;">' +
    '<mat-card-title>{{langFa.title}}</mat-card-title>' +
    '<mat-card-content>' +
    '<bs-schedule-param-form [type]="type" [patchValue]="value" (submitted)="onFormSubmitted($event)"></bs-schedule-param-form>' +
    '</mat-card-content>' +
    '</mat-card>'
})
export class ScheduleParamDialogComponent {

  langFa: ScheduleParamFa;
  type: PageType;
  value: ScheduleParamModel;
  private id: string;

  constructor(private service: SchedulerService,
              public dialogRef: MatDialogRef<ScheduleParamDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public model: string) {
    this.langFa = new ScheduleParamFa();
    this.id = model;
    this.type = PageType.VIEW;
    this.service
      .get(this.id)
      .subscribe(value => {
        this.value = value.response;
      });
  }

  onFormSubmitted($event: ScheduleParamModel) {
    this.dialogRef.close($event);
  }

  onFormClosed($event: boolean) {
    this.dialogRef.close();
  }
}
