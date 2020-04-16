import {NgModule} from '@angular/core';
import {CustomMaterialModule} from "../custom-material.module";
import {ScheduleParamFormComponent} from "./form/schedule-param/schedule-param.form.component";
import {ReactiveFormsModule} from "@angular/forms";
import {ScheduleManagementPageComponent} from "./page/schedule-management/schedule-management-page.component";
import {ScheduleParamListComponent} from "./form/schedule-param-list/schedule-param-list.component";
import {SchedulerService} from "./service/scheduler.service";
import {SchedulePageComponent} from "./page/schedule-page/schedule-page.component";
import {ScheduleParamDialogComponent} from "./form/schedule-param-dialog/schedule-param.dialog.component";
import {ConfirmDialogComponent} from "./confirm-dialog/confirm-dialog.component";

@NgModule({
  declarations: [
    ScheduleParamFormComponent,
    ScheduleParamListComponent,
    SchedulePageComponent,
    ScheduleManagementPageComponent,
    ScheduleParamDialogComponent,
    ConfirmDialogComponent
  ],
  exports: [
    ScheduleManagementPageComponent,
    SchedulePageComponent
  ],
  imports: [
    CustomMaterialModule,
    ReactiveFormsModule,
  ],
  providers: [
    SchedulerService
  ],
  entryComponents:[
    ScheduleParamDialogComponent,
    ConfirmDialogComponent
  ]
})
export class ScheduleServiceModule {
}
