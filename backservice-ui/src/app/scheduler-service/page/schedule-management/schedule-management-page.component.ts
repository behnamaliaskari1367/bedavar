import {Component, ViewChild} from '@angular/core';
import {ScheduleParamFa} from "../../../model/lang.fa";
import {MatDialog} from "@angular/material";
import {Router} from "@angular/router";
import {PageType, ScheduleParamModel} from "../../../model/model";
import {BackServiceMessageService} from "@fartak/backservice-common";
import {ScheduleParamListComponent} from "../../form/schedule-param-list/schedule-param-list.component";
import {SchedulerService} from "../../service/scheduler.service";
import {ScheduleParamDialogComponent} from "../../form/schedule-param-dialog/schedule-param.dialog.component";
import {ConfirmDialogComponent, ConfirmDialogModel} from "../../confirm-dialog/confirm-dialog.component";

@Component({
  selector: 'bs-page-schedule-management',
  templateUrl: 'schedule-management-page.component.html',
  styleUrls: ['schedule-management-page.component.css']
})
export class ScheduleManagementPageComponent {

  @ViewChild("table") listComponent: ScheduleParamListComponent;
  lang: ScheduleParamFa;
  pageType = PageType;
  formValue: ScheduleParamModel;

  constructor(private dialog: MatDialog,
              private messageService: BackServiceMessageService,
              private service: SchedulerService,
              private router: Router) {
    this.lang = new ScheduleParamFa();
  }

  onEditButtonSelected($event: MouseEvent) {
    this.router.navigate(['scheduler', 'edit', this.listComponent.selection.selected[0].id]);
  }

  onViewButtonSelected($event: MouseEvent) {
    this.dialog.open(ScheduleParamDialogComponent, {
      width: '590px',
      data: this.listComponent.selection.selected[0].id
    });
  }

  onAddButtonSelected($event: MouseEvent) {
    $event.preventDefault();
    this.router.navigate(['scheduler', 'new', 'new']);
  }

  onSearchButtonSubmitted($event: ScheduleParamModel) {
    this.listComponent.requestModel = $event;
    this.listComponent.loadPage();
  }

  onDeleteButtonSelected($event: MouseEvent) {
    const dialogData = new ConfirmDialogModel(this.lang.confirmTitle, this.lang.confirmMessage);

    let matDialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '590px',
      data: dialogData
    });

    matDialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult) {
        this.service
          .delete(this.listComponent.selection.selected[0].id)
          .subscribe(value => {
            this.listComponent.loadPage();
          })
      }
    });
  }
}
