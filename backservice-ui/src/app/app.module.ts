import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {AuthModule} from "./auth/auth.module";
import {CommonModule} from "@angular/common";
import {HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {DashboardModule} from "./dashboard/dashboard.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouteReuseStrategy} from "@angular/router";
import {BackServiceCacheRouteReuseStrategy} from "./auth/service/backservice-cache-route-reuse-strategy";
import {BackServiceHttpInterceptorService} from "./auth/service/backservice-http-interceptor.service";
import {ScheduleServiceModule} from "./scheduler-service/schedule-service.module";
import {MatPaginatorIntl} from "@angular/material";
import {MaterialPaginatorIntlProvider} from "./auth/service/material-paginator-intl.provider";
import {BackServiceCommonModule} from "@fartak/backservice-common";
import {environment} from "../environments/environment";
import {
  BreakPointRegistry, FlexFillStyleBuilder, FlexLayoutModule, FlexOrderStyleBuilder, FlexStyleBuilder,
  LayoutAlignStyleBuilder, LayoutGapStyleBuilder,
  LayoutStyleBuilder, MediaMarshaller,
  PrintHook,
  ShowHideStyleBuilder, StylesheetMap,
  StyleUtils,
  ɵMatchMedia,
} from "@angular/flex-layout";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BackServiceCommonModule.forRoot(environment),
    BrowserModule,
    CommonModule,
    AuthModule,
    HttpClientModule,
    HttpClientXsrfModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    DashboardModule,
    ScheduleServiceModule,
    // FlexLayoutModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: BackServiceHttpInterceptorService,
      multi: true
    },
    {
      provide: RouteReuseStrategy,
      useClass: BackServiceCacheRouteReuseStrategy
    },
    {provide: MatPaginatorIntl, useClass: MaterialPaginatorIntlProvider},
    StyleUtils,StylesheetMap,ɵMatchMedia,FlexFillStyleBuilder,LayoutAlignStyleBuilder,LayoutGapStyleBuilder,MediaMarshaller,BreakPointRegistry,PrintHook,LayoutStyleBuilder,FlexStyleBuilder,ShowHideStyleBuilder,FlexOrderStyleBuilder
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
