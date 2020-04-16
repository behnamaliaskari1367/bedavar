import {Component} from '@angular/core';
import {BackServiceAuthService} from "@fartak/backservice-common";
import {ThemeService} from "./dashboard/service/theme.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(authService: BackServiceAuthService,
              themeService: ThemeService) {
  }
}
