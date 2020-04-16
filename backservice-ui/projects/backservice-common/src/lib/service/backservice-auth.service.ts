import {Inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {Router} from "@angular/router";
import {BackServiceURL} from "../backservice-url";
import {catchError, finalize} from "rxjs/operators";
import {BackServiceResponseModel, LoggedModel} from "../backservice-common.model";

@Injectable({providedIn: 'root'})
export class BackServiceAuthService {

  redirectUrl: string;
  public static LOGIN_URL = "/auth";

  private isLoginSubject = new BehaviorSubject<boolean>(false);
  private validateInProgress = false;
  private USER_NAME_SESSION_ATTRIBUTE_NAME = 'backServiceAuthenticatedUser';
  private TOKEN_SESSION_ATTRIBUTE_NAME = 'backServiceAuthenticatedToken';
  private LOGGED_MODEL = 'backServiceUserLoggedModel';
  private BACK_SERVICE_AUTHORITIES = 'backServiceAuthorities';
  private BACK_SERVICE_LOGIN_URL = BackServiceURL.GATEWAY_URL + 'backservice-app/user/get_token';
  private BACK_SERVICE_CHECK_TOKEN = BackServiceURL.GATEWAY_URL + 'backservice-app/user/check_token';
  private BACK_SERVICE_LOGOUT_URL = BackServiceURL.GATEWAY_URL + 'auth-service/oauth2/principal/revoke';
  private BACK_SERVICE_USER_DETAIL_URL = BackServiceURL.GATEWAY_URL + 'auth-service/oauth2/principal/detail';
  private BACK_SERVICE_PRINCIPAL_URL = BackServiceURL.GATEWAY_URL + 'auth-service/oauth2/principal';

  constructor(private http: HttpClient,
              private router: Router,
              @Inject('env') private env) {
    this.isLoginSubject.next(this.isUserLoggedIn());
  }

  isLoggedIn(): Observable<boolean> {
    return this.isLoginSubject.asObservable();
  }

  // login_sso(data: any) {
  //   this.http
  //     .get(BackServiceURL.GATEWAY_URL + 'auth-service/principal', {
  //       headers: new HttpHeaders({
  //         'Authorization': `Basic ${window.btoa(data.username + ":" + data.password)}`,
  //         'Access-Control-Allow-Methods': 'GET, POST',
  //         'Access-Control-Allow-Origin': '*'
  //       })
  //     }).subscribe(() => {
  //     this.http
  //       .get(BackServiceURL.GATEWAY_URL + 'auth-service/principal/token')
  //       .subscribe(t => {
  //         this.registerSuccessfulLogin();
  //       });
  //   });
  // }

  login(data: any) {
    let reqModel: ReqModel = new ReqModel();
    reqModel.password = data.password;
    reqModel.username = data.username;

    this.http.post<BackServiceResponseModel<LoggedModel>>(this.BACK_SERVICE_LOGIN_URL, reqModel)
      .subscribe(r => {
        if (r && r.hasError == 0 && r.response) {
          this.registerAuthority(r.response);
          this.registerSuccessfulLogin();
        }
      });
  }

  public logout() {
    this.http
      .post(this.BACK_SERVICE_LOGOUT_URL, null)
      .pipe(
        finalize(() => {
          this.registerSuccessfulLogout();
        })
      ).subscribe();
  }

  /**
   * for development mode inject user and authorities
   * @param loggedModel for fake login
   */
  public devLogin(loggedModel: LoggedModel) {
    if (this.env.production) {
      throw new Error("access denied!");
    }

    this.registerAuthority(loggedModel);
    this.registerSuccessfulLogin();
  }

  /**
   * for development mode logout
   */
  public devLogout() {
    if (this.env.production) {
      throw new Error("access denied!");
    }

    this.registerSuccessfulLogout();
  }

  private registerSuccessfulLogin() {
    this.isLoginSubject.next(true);
    if (!this.redirectUrl) {
      this.redirectUrl = '';
    }
    this.isLoginSubject.next(true);
    this.router.navigate([this.redirectUrl]);
  }

  public validateToken(): void {
    if (this.validateInProgress) {
      return;
    }

    this.validateInProgress = true;
    let reqModel: ReqModel = new ReqModel();
    reqModel.token = sessionStorage.getItem(this.TOKEN_SESSION_ATTRIBUTE_NAME);

    this.http.post<BackServiceResponseModel<CheckTokenModel>>(this.BACK_SERVICE_CHECK_TOKEN, reqModel)
      .pipe(
        catchError(error => {
          this.registerSuccessfulLogout();
          return throwError(error);
        }),
        finalize(() => {
          this.validateInProgress = false;
        })
      )
      .subscribe(r => {
        if (r && r.hasError == 1) {
          this.registerSuccessfulLogout();
        }
      });
  }

  private registerSuccessfulLogout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem(this.TOKEN_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem(this.BACK_SERVICE_AUTHORITIES);
    sessionStorage.removeItem(this.LOGGED_MODEL);
    this.isLoginSubject.next(false);
    this.router.navigate([BackServiceAuthService.LOGIN_URL]);
  }

  hasRole(role: String): boolean {
    let item = sessionStorage.getItem(this.BACK_SERVICE_AUTHORITIES);
    if (item) {
      return item.indexOf(`##${role}##`) > -1;
    }
  }

  hasAnyRoles(roles: String[]): boolean {
    for (const role of roles) {
      if (this.hasRole(role)) {
        return true;
      }
    }
    return false;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    return user !== null;
  }


  isMustAddToken(url: string): boolean {
    if (url) {
      url = url.trim();
    }

    return !(url === this.BACK_SERVICE_LOGIN_URL || url === this.BACK_SERVICE_CHECK_TOKEN);
  }

  getLoggedInUserName(): string {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) return '';
    return user
  }

  getLoggedInToken(): string {
    let token = sessionStorage.getItem(this.TOKEN_SESSION_ATTRIBUTE_NAME);
    if (token === null) return '';
    return token
  }

  getPrincipal() {
    return this.http
      .get(this.BACK_SERVICE_PRINCIPAL_URL);
  }

  getUserDetail(): Observable<Object> {
    return this.http
      .get(this.BACK_SERVICE_USER_DETAIL_URL);
  }

  private registerAuthority(model: LoggedModel) {
    sessionStorage.setItem(this.LOGGED_MODEL, JSON.stringify(model));

    let authorities: string = "";
    if (model && model.principal && model.principal.authorities) {
      for (let i = 0; i < model.principal.authorities.length; i++) {
        const authority = model.principal.authorities[i];
        if (authority && authority.authority) {
          authorities += `##${authority.authority}##`;
        }
      }
      sessionStorage.setItem(this.BACK_SERVICE_AUTHORITIES, authorities);
      sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, model.principal.username);
    }

    if (model && model.details) {
      sessionStorage.setItem(this.TOKEN_SESSION_ATTRIBUTE_NAME, model.details.tokenValue);
    }
  }
}

class ReqModel {
  username: string;
  password: string;
  token: string;
}

class CheckTokenModel {
  username: string;
  token: string;
  active: boolean;
  exp: number;
}
