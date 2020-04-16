import {Component} from '@angular/core';
import {BackServiceAuthService, NavItem} from "@fartak/backservice-common";
import {HomeFa} from "../../model/lang.fa";
import {AppProjects} from "../../app.projects";
import {Router} from "@angular/router";

@Component({
  selector: 'bs-dash-dash',
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.css']
})
export class DashboardComponent {

  lang = new HomeFa();
  cards: Card[] = [];
  appProjects: AppProjects;

  constructor(private authService: BackServiceAuthService,
              private router: Router) {
    this.appProjects = new AppProjects();
    this.setCards();
  }

  private setCards() {
    let colorIndex = 0;

    if (!this.authService.isUserLoggedIn()) {
      this.cards.push({
        color: DashboardComponent.getColor(colorIndex++),
        displayName: this.lang.login,
        iconName: 'account_box',
        route: 'auth'
      });
    }

    const haUserManagementRole = this.authService.hasRole('ROLE_BACK_SERVICE_USER_MANAGEMENT');
    if (haUserManagementRole) {
      this.cards.push({
        color: DashboardComponent.getColor(colorIndex++),
        displayName: this.lang.userManagement,
        iconName: 'account_circle',
        route: 'user-management'
      });
    }
  }

  cardSelected($event: MouseEvent, card: Card) {
    this.router.navigate([card.route]);
  }

  private static getColor(index: number) {
    const colors = ['#673AB7', '#66BB6A', '#FFA726', '#FF74D5', '#FF9274', '#6AA1FF'];
    return colors[index % colors.length];
  }
}

interface Card extends NavItem {
  parent?: string;
  color: string;
}
