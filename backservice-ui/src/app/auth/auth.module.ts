import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {LoginComponent} from "./login/login.component";
import {CustomMaterialModule} from "../custom-material.module";
import {UserFormComponent} from './form/user/user/user-form.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {UserListComponent} from './form/user/user-list/user-list.component';
import {UserViewComponent} from "./form/user/user-view/user-view.component";
import {UserViewDialogComponent} from "./form/user/user-view/user-view.dialog.component";
import {ChangePasswordDialogComponent} from "./change-password/change-password.dialog.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {UserManagementPageComponent} from './page/user-management/user-management-page.component';
import {UserPageComponent} from './page/user/user-page.component';
import {UserDialogComponent} from "./form/user/user/user-form.dialog.component";
import {AssignAuthorityPageComponent} from './page/assign-authority/assign-authority-page.component';
import {MatListModule} from "@angular/material";

@NgModule({
  declarations: [
    LoginComponent,
    UserFormComponent,
    UserListComponent,
    UserViewComponent,
    UserViewDialogComponent,
    ChangePasswordComponent,
    ChangePasswordDialogComponent,
    UserManagementPageComponent,
    UserPageComponent,
    UserDialogComponent,
    AssignAuthorityPageComponent
  ],
  exports: [
    LoginComponent
  ],
  imports: [
    MatListModule,
    CustomMaterialModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    FlexLayoutModule
  ],
  entryComponents: [
    UserViewDialogComponent,
    UserDialogComponent,
    ChangePasswordDialogComponent
  ],
  providers: []
})
export class AuthModule {
}
