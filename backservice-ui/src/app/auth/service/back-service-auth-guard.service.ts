import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {BackServiceAuthService} from "@fartak/backservice-common";

@Injectable({
  providedIn: 'root'
})
export class BackServiceAuthGuard implements CanActivate {

  constructor(private authService: BackServiceAuthService, private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const url: string = state.url;
    return this.checkLogin(url);
  }

  checkLogin(url: string): boolean {
    if (url === BackServiceAuthService.LOGIN_URL) {
      return !this.authService.isUserLoggedIn();
    }

    if (this.authService.isUserLoggedIn()) {
      return true;
    }

    // Store the attempted URL for redirecting
    this.authService.redirectUrl = url;

    // Navigate to the login page with extras
    this.router.navigate([BackServiceAuthService.LOGIN_URL]);
    return false;
  }
}
