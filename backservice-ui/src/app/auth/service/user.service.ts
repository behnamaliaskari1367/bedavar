import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {ChangePasswordModel, CustomRequestModel, CustomResponseModel, UserModel} from "../../model/model";
import {BackServiceUrls} from "../../model/urls";
import {BackServiceResponseModel} from "@fartak/backservice-common";

/**
 * @author Abbas Alishirvani
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  get(id: string): Observable<BackServiceResponseModel<UserModel>> {
    let url = BackServiceUrls.USER_GET;
    url = url.replace("${id}", id);
    return this.http.get<BackServiceResponseModel<UserModel>>(url);
  }

  add(model: UserModel): Observable<BackServiceResponseModel<UserModel>> {
    return this.http.post<BackServiceResponseModel<UserModel>>(BackServiceUrls.USER_ADD, model);
  }

  edit(model: UserModel): Observable<BackServiceResponseModel<UserModel>> {
    return this.http.put<BackServiceResponseModel<UserModel>>(BackServiceUrls.USER_EDIT, model);
  }

  find(model: CustomRequestModel<UserModel>): Observable<CustomResponseModel<UserModel>> {
    return this.http.post<CustomResponseModel<UserModel>>(BackServiceUrls.USER_FIND, model);
  }

  search(model: CustomRequestModel<UserModel>): Observable<CustomResponseModel<UserModel>> {
    return this.http.post<CustomResponseModel<UserModel>>(BackServiceUrls.USER_SEARCH, model);
  }

  resetPassword(model: ChangePasswordModel): Observable<CustomResponseModel<UserModel>> {
    return this.http.post<CustomResponseModel<UserModel>>(BackServiceUrls.USER_RESET_PASSWORD, model);
  }

  changePassword(model: ChangePasswordModel): Observable<CustomResponseModel<UserModel>> {
    return this.http.post<CustomResponseModel<UserModel>>(BackServiceUrls.USER_CHANGE_PASSWORD, model);
  }

  assignedAuthorities(id: string): Observable<BackServiceResponseModel<UserModel>> {
    let url = BackServiceUrls.USER_ASSIGNED_AUTHORITIES;
    url = url.replace("${id}", id);
    return this.http.get<BackServiceResponseModel<UserModel>>(url);
  }
}
