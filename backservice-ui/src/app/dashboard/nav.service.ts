import {Injectable, OnDestroy} from '@angular/core';
import {Event, NavigationEnd, Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class NavService implements OnDestroy {
  public appDrawer: any;
  public currentUrl = new BehaviorSubject<string>(undefined);

  constructor(private router: Router) {
    this.router.events
      .subscribe((event: Event) => {
        if (event instanceof NavigationEnd) {
          this.currentUrl.next(event.urlAfterRedirects);
        }
      });
  }

  public closeNav() {
    this.appDrawer.close();
  }

  public openNav() {
    this.appDrawer.open();
  }

  ngOnDestroy(): void {
    this.currentUrl.unsubscribe();
  }
}
