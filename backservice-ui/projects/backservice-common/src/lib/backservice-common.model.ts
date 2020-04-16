export interface BackServiceResponseModel<T> {
  response: T;
  hasError: number;
  type: string;
  exception: string;
}

export interface NavItem {
  displayName: string;
  disabled?: boolean;
  iconName: string;
  route?: string;
  roles?: string[];
  children?: NavItem[];
}

export class LoggedDetails {
  remoteAddress: string;
  tokenValue: string;
  tokenType: string;
}

export class LoggedModel {
  details: LoggedDetails;
  authenticated: boolean;
  principal: Principal;
}

export class Principal {
  username: string;
  authorities: AuthorityModel[];
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  enabled: boolean;
}

export class AuthorityModel {
  authority: string;
}
