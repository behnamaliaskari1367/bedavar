import {BackServiceURL} from "@fartak/backservice-common";

export class BackServiceUrls {
  static BASE_URL = BackServiceURL.GATEWAY_URL + 'auth-service/api/v1/';
  static LOGIN_URL = BackServiceUrls.BASE_URL + "principal/";
  static USER_BASE_URL = BackServiceUrls.BASE_URL + "user/";
  static USER_FIND = BackServiceUrls.USER_BASE_URL + "find";
  static USER_SEARCH = BackServiceUrls.USER_BASE_URL + "search";
  static USER_EDIT = BackServiceUrls.USER_BASE_URL + "edit";
  static USER_ADD = BackServiceUrls.USER_BASE_URL + "add";
  static USER_GET = BackServiceUrls.USER_BASE_URL + "${id}";
  static USER_CHANGE_PASSWORD = BackServiceUrls.USER_BASE_URL + "change/password";
  static USER_RESET_PASSWORD = BackServiceUrls.USER_BASE_URL + "reset/password";
  static USER_ASSIGNED_AUTHORITIES = BackServiceUrls.USER_BASE_URL + "${id}/authorities";
  static AUTHORITY_BASE_URL = BackServiceUrls.BASE_URL + "authority/";
  static AUTHORITY_TYPE_USER_ACTIVE = BackServiceUrls.AUTHORITY_BASE_URL + "type/user/active";
  static USER_AUTHORITY_BASE_URL = BackServiceUrls.BASE_URL + "user-authority/";
  static AUTHORITY_ASSIGN_AUTHORITIES = BackServiceUrls.USER_AUTHORITY_BASE_URL + "user/${id}/assign/authorities";
  static SCHEDULER_BASE = BackServiceURL.GATEWAY_URL + "scheduler-service/scheduler/api/v1/";
  static SCHEDULER_PARAM_BASE = BackServiceUrls.SCHEDULER_BASE + "param";
  static SCHEDULER_PARAM_ID = BackServiceUrls.SCHEDULER_PARAM_BASE + "/${id}";
  static SCHEDULER_PARAM_SEARCH = BackServiceUrls.SCHEDULER_PARAM_BASE + "/search";
}
