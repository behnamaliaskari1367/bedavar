import {AfterViewInit, Component, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {CustomRequestModel, UserModel} from "../../../../model/model";
import {SelectionModel} from "@angular/cdk/collections";
import {UserDataSource} from "./user-data-source";
import {MatPaginator, MatSort} from "@angular/material";
import {merge, Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {UserFa} from "../../../../model/lang.fa";
import {UserService} from "../../../service/user.service";

/**
 * @author Abbas Alishirvani
 */
@Component({
  selector: 'bs-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit, AfterViewInit, OnDestroy {

  lang = new UserFa();
  datasource: UserDataSource;
  totalRecord$: Observable<number>;
  pageSizeOptions = [5, 10, 25, 50];
  selection = new SelectionModel<UserModel>(false, []);

  @Input() requestModel: UserModel;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @Output() selectionChanged = this.selection.changed;
  @Input() _pageSize;
  get pageSize(): number {
    let pageSize = localStorage.getItem("UserListComponent_pageSize");
    if (pageSize) {
      return Number(pageSize);
    } else if (this._pageSize) {
      return this._pageSize;
    } else {
      return 5;
    }
  }

  constructor(userService: UserService) {
    this.datasource = new UserDataSource(userService);
    this.totalRecord$ = this.datasource.totalRecordSubject.asObservable();
  }

  checkboxLabel(row?: UserModel): string {
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }

  getDisplayedColumns(): string[] {
    return ['select', 'username', 'firstName', 'lastName', 'branch', 'enabled'];
  }

  ngOnInit(): void {
    this.loadPage();
  }

  ngAfterViewInit(): void {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => {
          if (this.paginator.pageSize) {
            localStorage.setItem("UserListComponent_pageSize", this.paginator.pageSize.toString());
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
    let model = new CustomRequestModel<UserModel>(
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
