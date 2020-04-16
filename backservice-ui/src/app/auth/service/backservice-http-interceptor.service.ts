import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, finalize, tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {BackServiceAuthService, BackServiceMessageService} from "@fartak/backservice-common";
import {Overlay, OverlayConfig, OverlayRef} from "@angular/cdk/overlay";
import {ComponentPortal} from "@angular/cdk/portal";
import {OverlayComponent} from "../../dashboard/overlay/overlay.component";

@Injectable()
export class BackServiceHttpInterceptorService implements HttpInterceptor {

  private _overlayRef: OverlayRef;
  private _overlayComponent: any;

  constructor(private router: Router,
              private authService: BackServiceAuthService,
              private messageService: BackServiceMessageService,
              private overlay: Overlay
  ) {
    this._overlayRef = this.overlay.create(this.getOverlayConfig());
    this._overlayComponent = new ComponentPortal(OverlayComponent);
  }

  intercept2(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let inProgress = true;
    setTimeout(() => {
      if (inProgress && !this._overlayRef.hasAttached()) {
        this._overlayRef.attach(this._overlayComponent);
      }
    }, 300);

    request = request.clone({
      withCredentials: true,
    });

    if (this.authService.isUserLoggedIn()) {
      request = request.clone({headers: request.headers.set('X-XSRF-TOKEN', this.authService.getLoggedInToken())});
    }

    return next.handle(request)
      .pipe(
        tap(value => {
          let body = value['body'];
          if (body && body['hasError'] == 1) {
            throw ({message: body['type'] + ' - ' + body['exception']});
          }
        }),
        catchError((error: HttpErrorResponse) => {
          this.messageService.add(error.message, 'خطا');

          if (error.status === 401) {
            this.router.navigate([BackServiceAuthService.LOGIN_URL]);
          }
          if (error.status === 400) {
            this.messageService.add(error.error, 'خطا');
          }
          return throwError(error);
        }),
        finalize(() => {
          this._overlayRef.detach();
          inProgress = false;
        }));
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let inProgress = true;
    setTimeout(() => {
      if (inProgress && !this._overlayRef.hasAttached()) {
        this._overlayRef.attach(this._overlayComponent);
      }
    }, 200);

    request = request.clone({
      withCredentials: true,
    });

    if (this.authService.isMustAddToken(request.url)) {
      request = request.clone({
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${this.authService.getLoggedInToken()}`
        })
      });
    }

    return next.handle(request)
      .pipe(
        tap(value => {
          let body = value['body'];
          if (body && body['hasError'] == 1) {
            throw ({message: body['type'] + ' - ' + body['exception']});
          }
        }),
        catchError((error: HttpErrorResponse) => {

          let action = "خطا";
          let message = error.message;

          if (error.error && error.error.message) {
            message = error.error.message;
          } else if (error.error && error.error['error']) {
            message = error.error['error'];
          } else if (error.error && error.error['exception']) {
            message = error.error['exception'];
          }

          if (error.status === 401) {
            action = "خطای دسترسی";
            this.authService.validateToken();
          }

          this.messageService.add(message, action);

          return throwError(error);
        }),
        finalize(() => {
          inProgress = false;
          setTimeout(() => {
            this._overlayRef.detach();
          }, 200);
        }));
  }

  private getOverlayConfig(): OverlayConfig {
    return new OverlayConfig({
      hasBackdrop: true,
      scrollStrategy: this.overlay.scrollStrategies.reposition()
    });
  }

}
