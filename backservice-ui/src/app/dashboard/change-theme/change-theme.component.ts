import {Component} from '@angular/core';
import {HomeFa} from "../../model/lang.fa";
import {ThemeService} from "../service/theme.service";

@Component({
  selector: 'change-theme',
  templateUrl: 'change-theme.component.html',
  styleUrls: ['change-theme.component.css'],
})
export class ChangeThemeComponent {

  lang = new HomeFa();
  themes: string[];

  constructor(private service: ThemeService) {
    this.themes = ThemeService.themeList();
  }

  changeThemeClicked(themeName: string) {
    this.service.changeTheme(themeName);
  }
}
