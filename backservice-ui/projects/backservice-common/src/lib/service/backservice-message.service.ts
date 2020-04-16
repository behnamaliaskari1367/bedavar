import {Injectable, OnDestroy} from '@angular/core';
import {MatSnackBar, MatSnackBarConfig, MatSnackBarRef, SimpleSnackBar} from "@angular/material";
import {Subject, Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BackServiceMessageService implements OnDestroy {

  private snackBarRef: MatSnackBarRef<SimpleSnackBar>;
  private messageQueue: Subject<SnackBarMessage> = new Subject<SnackBarMessage>();
  private msgQueue = [];
  private isInstanceVisible = false;
  private subscription: Subscription;

  constructor(public snackBar: MatSnackBar) {
    this.subscription = this.messageQueue.subscribe(message => {
      this.snackBarRef = this.snackBar.open(message.message, message.action, message.config);
    });
  }

  showNext() {
    if (this.msgQueue.length === 0) {
      return;
    }

    let message = this.msgQueue.shift();
    this.isInstanceVisible = true;
    this.snackBarRef = this.snackBar.open(message.message, message.action, {duration: 3000});
    this.snackBarRef.afterDismissed().subscribe(() => {
      this.isInstanceVisible = false;
      this.showNext();
    });
  }

  ngOnDestroy(): void {
    this.messageQueue.unsubscribe();
    this.subscription.unsubscribe();
  }

  /**
   * Add a message
   * @param message The message to show in the snackbar.
   * @param action The label for the snackbar action.
   * @param config Additional configuration options for the snackbar.
   */
  public add(message: string, action?: string, config?: MatSnackBarConfig): void {

    if (!config) {
      config = new MatSnackBarConfig();
      config.duration = 10000;
    }

    let sbMessage = new SnackBarMessage();
    sbMessage.message = message;
    sbMessage.action = action;
    sbMessage.config = config;

    this.messageQueue.next(sbMessage);
    this.msgQueue.push(sbMessage);
    if (!this.isInstanceVisible) {
      this.showNext();
    }
  }
}

export class SnackBarMessage {
  message: string;
  action: string = null;
  config: MatSnackBarConfig = null;
}
