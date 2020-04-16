import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./auth/login/login.component";
import {UserManagementPageComponent} from "./auth/page/user-management/user-management-page.component";
import {UserPageComponent} from "./auth/page/user/user-page.component";
import {AssignAuthorityPageComponent} from "./auth/page/assign-authority/assign-authority-page.component";
import {BackServiceAuthGuard} from "./auth/service/back-service-auth-guard.service";
import {ScheduleManagementPageComponent} from "./scheduler-service/page/schedule-management/schedule-management-page.component";
import {DashboardComponent} from "./dashboard/dashboard/dashboard.component";
import {SchedulePageComponent} from "./scheduler-service/page/schedule-page/schedule-page.component";
import {isInProdMode} from "./app.projects";

console.log(isInProdMode());

const samtakProdMode = {
  path: 'samtak',
  canActivate: [BackServiceAuthGuard]
  // loadChildren: () => import('@fartak/samtak-ui-lib').then(m => m.SamtakUiLibModule)
};

const samtakDevMode = {
  path: 'samtak',
  canActivate: [BackServiceAuthGuard]
  // loadChildren: () => {
  //   try {
  //     return import('@fartak/samtak-ui-lib').then(m => m.SamtakUiLibModule)
  //   } catch (e) {
  //     console.log("error on import @fartak/samtak-ui-lib - error is:");
  //     console.error(e);
  //   }
  // },
};

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'dash'},
  {path: 'dash', component: DashboardComponent},
  {path: 'auth', component: LoginComponent, canActivate: [BackServiceAuthGuard]},
  {path: 'user/:type/:id', component: UserPageComponent, canActivate: [BackServiceAuthGuard]},
  {path: 'user-management', component: UserManagementPageComponent, canActivate: [BackServiceAuthGuard]},
  {path: 'assign-authority/:type/:id', component: AssignAuthorityPageComponent, canActivate: [BackServiceAuthGuard]},
  {path: 'schedule-management', component: ScheduleManagementPageComponent, canActivate: [BackServiceAuthGuard]},
  {path: 'scheduler/:type/:id', component: SchedulePageComponent, canActivate: [BackServiceAuthGuard]},
  // isInProdMode() ? samtakProdMode : samtakDevMode,
  {path: '**', redirectTo: 'dash'},
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {enableTracing: false})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
