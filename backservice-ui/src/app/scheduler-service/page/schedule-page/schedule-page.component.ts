import {Component, OnInit} from '@angular/core';
import {PageType, ScheduleParamModel} from "../../../model/model";
import {ScheduleParamFa} from "../../../model/lang.fa";
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {BackServiceMessageService} from "@fartak/backservice-common";
import {SchedulerService} from "../../service/scheduler.service";

@Component({
  selector: 'bs-schedule-param',
  templateUrl: 'schedule-page.component.html',
  styleUrls: ['schedule-page.component.css']
})
export class SchedulePageComponent implements OnInit {

  type: PageType;
  langFa: ScheduleParamFa;
  private id: string;
  value: ScheduleParamModel;

  constructor(private route: ActivatedRoute,
              private backServiceMessageService: BackServiceMessageService,
              private service: SchedulerService,
              private location: Location) {
    this.langFa = new ScheduleParamFa();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.type = params['type'];
    });
  }

  onFormSubmitted($event: ScheduleParamModel) {
    if (!$event || !this.type) {
      this.location.back();
      return;
    }

    if (this.type === PageType.NEW) {
      this.service
        .add($event)
        .subscribe(() => {
          this.backServiceMessageService.add('Schedule Added', 'OK');
          this.location.back();
        });
    } else if (this.type === PageType.EDIT) {
      this.service
        .edit($event)
        .subscribe(() => {
          this.backServiceMessageService.add('Schedule Edited', 'OK');
          this.location.back();
        });
    }
  }

  ngOnInit(): void {
    if (this.type === PageType.EDIT || this.type === PageType.VIEW) {
      this.service
        .get(this.id)
        .subscribe(value => {
          this.value = value.response;
        });
    }
  }
}
