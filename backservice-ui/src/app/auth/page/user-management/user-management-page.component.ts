import {Component, OnInit, ViewChild} from '@angular/core';
import {UserFa} from "../../../model/lang.fa";
import {UserListComponent} from "../../form/user/user-list/user-list.component";
import {MatDialog} from "@angular/material";
import {UserViewDialogComponent} from "../../form/user/user-view/user-view.dialog.component";
import {Router} from "@angular/router";
import {ChangePasswordDialogComponent} from "../../change-password/change-password.dialog.component";
import {UserService} from "../../service/user.service";
import {PageType, UserModel} from "../../../model/model";
import {BackServiceMessageService} from "@fartak/backservice-common";

@Component({
  selector: 'bs-page-user-management',
  templateUrl: 'user-management-page.component.html',
  styleUrls: ['user-management-page.component.css']
})
export class UserManagementPageComponent implements OnInit {

  @ViewChild("table") userListComponent: UserListComponent;
  lang: UserFa;
  pageType = PageType;
  userFormValue: UserModel;

  constructor(private dialog: MatDialog,
              private messageService: BackServiceMessageService,
              private router: Router,
              private userService: UserService) {
    this.lang = new UserFa();
  }

  ngOnInit() {
  }

  onEditButtonSelected($event: MouseEvent) {
    this.router.navigate(['user', 'edit', this.userListComponent.selection.selected[0].id]);
  }

  onViewButtonSelected($event: MouseEvent) {
    this.dialog.open(UserViewDialogComponent, {
      width: '590px',
      data: this.userListComponent.selection.selected[0]
    });
  }

  onChangePasswordButtonSelected($event: MouseEvent) {
    $event.preventDefault();

    let matDialogRef = this.dialog.open(ChangePasswordDialogComponent, {
      width: '590px',
      data: {
        type: 'reset',
        username: this.userListComponent.selection.selected[0].username,
        id: this.userListComponent.selection.selected[0].id
      }
    });

    matDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userService
          .resetPassword(result)
          .subscribe(value => {
            this.messageService.add('', 'OK');
          });
      }
    });
  }

  onAddButtonSelected($event: MouseEvent) {
    $event.preventDefault();
    this.router.navigate(['user', 'new', "user"]);
  }

  onAssignAuthorityButtonSelected($event: MouseEvent) {
    $event.preventDefault();
    this.router.navigate(['assign-authority', 'edit', this.userListComponent.selection.selected[0].id]);
  }

  onUserSearchButtonSubmitted($event: UserModel) {
    this.userListComponent.requestModel = $event;
    this.userListComponent.loadPage();
  }
}
