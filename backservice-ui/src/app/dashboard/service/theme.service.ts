/**
 * @author Abbas Alishirvani
 */
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private BACK_SERVICE_USER_THEME = 'backServiceUserTheme';

  constructor() {
    let theme = localStorage.getItem(this.BACK_SERVICE_USER_THEME);
    if (theme) {
      this.changeTheme(theme);
    }
  }

  public changeTheme(themeName: string) {
    localStorage.setItem(this.BACK_SERVICE_USER_THEME, themeName);
    document.getElementById('themeAsset')['href'] = `assets/css/${themeName}.css`;
  }

  public static themeList(): string[] {
    return ['deeppurple-amber', 'indigo-pink', 'pink-bluegrey', 'purple-green'];
  }
}
