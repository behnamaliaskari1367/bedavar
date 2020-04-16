import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {CustomMaterialModule} from "../custom-material.module";
import {HomeComponent} from "./home-component/home.component";
import {AppRoutingModule} from "../app-routing.module";
import {MatExpansionModule, MatListModule} from "@angular/material";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MenuListItemComponent} from "./menu-item-list/menu-list-item.component";
import {AuthModule} from "../auth/auth.module";
import {NavService} from "./nav.service";
import {AppInfoComponent} from "./app-info/app-info.component";
import {AppInfoDialogComponent} from "./app-info/app-info-dialog.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ChangeThemeComponent} from "./change-theme/change-theme.component";
import {ChangeThemeDialogComponent} from "./change-theme/change-theme-dialog.component";
import {OverlayComponent} from "./overlay/overlay.component";

@NgModule({
  declarations: [
    HomeComponent,
    MenuListItemComponent,
    AppInfoDialogComponent,
    AppInfoComponent,
    DashboardComponent,
    ChangeThemeComponent,
    ChangeThemeDialogComponent,
    OverlayComponent
  ],
  exports: [
    HomeComponent,
    DashboardComponent,
    OverlayComponent
  ],
  imports: [
    AuthModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    AppRoutingModule,
    MatListModule,
    FlexLayoutModule,
    MatExpansionModule
  ],
  entryComponents: [
    AppInfoDialogComponent,
    ChangeThemeDialogComponent,
    OverlayComponent
  ],
  providers: [
    NavService
  ]
})
export class DashboardModule {
}
