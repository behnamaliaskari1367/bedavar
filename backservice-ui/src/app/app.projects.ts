import {NavItems} from "@fartak/samtak-ui-lib";
import {NavItem} from "@fartak/backservice-common";
import {environment} from "../environments/environment";

export class AppProjects {

  private _samatIsInstalled = false;
  private _samtakIsInstalled = true;
  private _samatNavItems: NavItem[];
  private _samtakNavItems: NavItem[];

  constructor() {
    if (this._samtakIsInstalled) {
      this._samtakNavItems = [];
      for (const item of new NavItems().items) {
        this._samtakNavItems.push(item);
      }
    }

    // if (this._samatIsInstalled) {
    //   this._samtakNavItems = [];
    //   for (const item of new NavItems().items) {
    //     this._samtakNavItems.push(item);
    //   }
    // }
  }

  get samtakIsInstalled(): boolean {
    return this._samtakIsInstalled;
  }

  get samtakNavItems(): NavItem[] {
    return this._samtakNavItems;
  }

  get samatIsInstalled(): boolean {
    return this._samatIsInstalled;
  }

  get samatNavItems(): NavItem[] {
    return this._samatNavItems;
  }
}

export function isInProdMode(): boolean {
  return environment.production;
}
