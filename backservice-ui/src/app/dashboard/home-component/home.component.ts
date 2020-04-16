import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {map} from 'rxjs/operators';
import {MatDialog} from '@angular/material';
import {UserViewDialogComponent} from '../../auth/form/user/user-view/user-view.dialog.component';
import {ChangePasswordDialogComponent} from '../../auth/change-password/change-password.dialog.component';
import {UserService} from '../../auth/service/user.service';
import {HomeFa} from '../../model/lang.fa';
import {NavService} from '../nav.service';
import {PageType} from '../../model/model';
import {AppProjects} from '../../app.projects';
import {Observable} from 'rxjs';
import {BackServiceAuthService, BackServiceMessageService, NavItem} from '@fartak/backservice-common';
import {HttpClient} from '@angular/common/http';
import {AppInfoDialogComponent} from "../app-info/app-info-dialog.component";
import {Location} from "@angular/common";
import {ChangeThemeDialogComponent} from "../change-theme/change-theme-dialog.component";

@Component({
  selector: 'bs-dash-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {

  userName = '';
  lang = new HomeFa();
  isLoggedIn: Observable<boolean>;
  appProjects: AppProjects;
  navItems: NavItem[] = [];

  @ViewChild('appDrawer') appDrawer: ElementRef;

  constructor(private authService: BackServiceAuthService,
              private userService: UserService,
              private location: Location,
              private navService: NavService,
              private http: HttpClient,
              private messageService: BackServiceMessageService,
              private dialog: MatDialog) {
    this.appProjects = new AppProjects();
    this.isLoggedIn = this.authService.isLoggedIn();
    this.authService.isLoggedIn().subscribe(value => {
      this.setNavItems();
    });
  }

  private setNavItems() {

    this.navItems = [];

    const haUserManagementRole = this.authService.hasRole('ROLE_BACK_SERVICE_USER_MANAGEMENT');
    if (haUserManagementRole) {
      this.navItems.push({
        displayName: this.lang.userManagement,
        iconName: 'account_circle',
        route: 'user-management'
      });
    }

    // const hasSchedulerService = this.authService.hasRole('ROLE_BACK_SERVICE_SCHEDULER_SERVICE');
    // if (hasSchedulerService) {
    //   this.navItems.push({
    //     displayName: this.lang.schedulerService,
    //     iconName: 'av_timer',
    //     route: 'schedule-management'
    //   });
    // }

    // if (this.appProjects.samtakIsInstalled) {
    //   let samtakNavItems: NavItem[] = [];
    //   for (let i = 0; i < this.appProjects.samtakNavItems.length; i++) {
    //     const samtakNavItem = this.appProjects.samtakNavItems[i];
    //     let hasAnyRoles = true;
    //     if (samtakNavItem.roles) {
    //       hasAnyRoles = this.authService.hasAnyRoles(samtakNavItem.roles);
    //     }
    //
    //     if (hasAnyRoles) {
    //       samtakNavItems.push(samtakNavItem);
    //     }
    //   }

      // if (samtakNavItems && samtakNavItems.length > 0) {
      //   this.navItems.push({
      //     displayName: this.lang.samtak,
      //     iconName: 'title',
      //     route: '/samtak',
      //     children: samtakNavItems
      //   });
      // }
    }

    // if (this.appProjects.samatIsInstalled) {
    //   this.navItems.push({
    //     displayName: this.lang.samat,
    //     iconName: 'title',
    //     route: '/samat',
    //     children: this.appProjects.samatNavItems
    //   })
    // }
  // }

  ngOnInit(): void {
    this.userName = this.authService.getLoggedInUserName();
  }

  logout($event) {
    $event.preventDefault();
    this.authService.logout();
  }

  changePassword($event) {
    $event.preventDefault();

    let matDialogRef = this.dialog.open(ChangePasswordDialogComponent, {
      width: '590px',
      data: {
        type: PageType.EDIT,
        username: this.authService.getLoggedInUserName()
      }
    });

    matDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userService
          .changePassword(result)
          .subscribe(value => {
            this.messageService.add('', 'OK');
          });
      }
    });
  }

  userInfo($event) {
    $event.preventDefault();
    this.authService.getUserDetail()
      .pipe(
        map(r => {
          if (r) {
            let matDialogRef = this.dialog.open(UserViewDialogComponent, {
              width: '590px',
              data: r
            });
          }
        })
      ).subscribe();
  }

  ngAfterViewInit() {
    this.navService.appDrawer = this.appDrawer;
  }

  appInfo($event: MouseEvent) {
    $event.preventDefault();

    let matDialogRef = this.dialog.open(AppInfoDialogComponent, {
      width: '400px',
      data: {}
    });
  }

  back($event: MouseEvent) {
    $event.preventDefault();
    this.location.back();
  }

  forward($event: MouseEvent) {
    $event.preventDefault();
    this.location.forward();
  }

  changeTheme($event: MouseEvent) {
    $event.preventDefault();
    let matDialogRef = this.dialog.open(ChangeThemeDialogComponent, {
      width: '350px'
    });
  }
}
