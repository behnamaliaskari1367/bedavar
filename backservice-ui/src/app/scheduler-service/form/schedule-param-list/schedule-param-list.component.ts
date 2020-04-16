import {AfterViewInit, Component, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {SelectionModel} from "@angular/cdk/collections";
import {MatPaginator, MatSort} from "@angular/material";
import {merge, Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {CustomRequestModel, ScheduleParamModel} from "../../../model/model";
import {ScheduleParamFa} from "../../../model/lang.fa";
import {SchedulerService} from "../../service/scheduler.service";
import {ScheduleParamDataSource} from "./schedule-param-data-source";

/**
 * @author Abbas Alishirvani
 */
@Component({
  selector: 'bs-schedule-list',
  templateUrl: 'schedule-param-list.component.html',
  styleUrls: ['schedule-param-list.component.css']
})
export class ScheduleParamListComponent implements OnInit, AfterViewInit, OnDestroy {

  lang = new ScheduleParamFa();
  datasource: ScheduleParamDataSource;
  totalRecord$: Observable<number>;
  pageSizeOptions = [5, 10, 25];
  selection = new SelectionModel<ScheduleParamModel>(false, []);

  @Input() requestModel: ScheduleParamModel;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @Output() selectionChanged = this.selection.changed;

  @Input() _pageSize;
  get pageSize(): number {
    let pageSize = localStorage.getItem("ScheduleParamListComponent_pageSize");
    if (pageSize) {
      return Number(pageSize);
    } else if (this._pageSize) {
      return this._pageSize;
    } else {
      return 5;
    }
  }

  constructor(service: SchedulerService) {
    this.datasource = new ScheduleParamDataSource(service);
    this.totalRecord$ = this.datasource.totalRecordSubject.asObservable();
  }

  checkboxLabel(row?: ScheduleParamModel): string {
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }

  getDisplayedColumns(): string[] {
    return ['select', 'serviceName', 'actionName', 'enabled'];
  }

  ngOnInit(): void {
    this.loadPage();
  }

  ngAfterViewInit(): void {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => {
          if (this.paginator.pageSize) {
            localStorage.setItem("ScheduleParamListComponent_pageSize", this.paginator.pageSize.toString());
          }
          this.loadPage();
        })
      ).subscribe();
  }

  private getSortDirection(): string {
    let result = 'asc';
    if (this.sort && this.sort.direction) {
      result = this.sort.direction;
    }
    return result.toUpperCase();
  }

  private getSortIndex(): string {
    let result = 'id';
    if (this.sort && this.sort.active) {
      result = this.sort.active;
    }
    return result;
  }

  public loadPage() {
    let model = new CustomRequestModel<ScheduleParamModel>(
      this.pageSize,
      this.getCurrentPage(),
      this.getSortIndex(),
      this.getSortDirection());
    model.value = this.getRequestValue();
    this.datasource.search(this.paginator, model);
  }

  private getCurrentPage(): number {
    let result = 0;
    if (this.paginator && this.paginator.pageIndex) {
      result = this.paginator.pageIndex;
    }
    return result;
  }

  private getRequestValue() {
    if (this.requestModel) {
      return this.requestModel;
    }
    return null;
  }

  ngOnDestroy(): void {

  }
}
