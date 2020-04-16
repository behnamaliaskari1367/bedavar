export class CustomRequestModel<T> {
  pageSize: number;
  currentPage: number;
  sortIndex: string;
  sortDirection: string;
  value: T;


  constructor(pageSize: number, currentPage: number, sortIndex: string, sortDirection: string) {
    this.pageSize = pageSize;
    this.currentPage = currentPage;
    this.sortIndex = sortIndex;
    this.sortDirection = sortDirection;
  }
}

export class CustomResponseModel<T> {
  currentPage: number;
  totalPage: number;
  totalRecords: number;
  response: T[];
}

class BaseModel<T> {
  id: T;
  version ?: number;
}

export class UserModel extends BaseModel<number> {
  firstName: string;
  lastName: string;
  branch: string;
  username: string;
  password: string;
  enabled: boolean;
  userAuthorities: UserAuthorityModel[];
}

export class UserAuthorityModel extends BaseModel<number> {
  user: UserModel;
  authority: AuthorityModel;
}

export class AuthorityModel extends BaseModel<number> {
  name: string;
  description: string;
  enabled: boolean;
  type: string;
}

export class ChangePasswordModel extends BaseModel<number> {
  type: PageType;
  username: string;
  newPassword: string;
  oldPassword: string;
}

export class ScheduleParamModel extends BaseModel<number> {
  serviceName: string;
  cron: string;
  url: string;
  actionName: string;
  enabled: boolean;
}

export enum PageType {
  NEW = 'new',
  EDIT = 'edit',
  VIEW = 'view',
  RESET = 'reset',
  SEARCH = 'search',
}
