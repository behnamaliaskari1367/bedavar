import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {AuthorityModel} from "../../model/model";
import {BackServiceUrls} from "../../model/urls";
import {BackServiceResponseModel} from "@fartak/backservice-common";

/**
 * @author Abbas Alishirvani
 */
@Injectable({
  providedIn: 'root'
})
export class AuthorityService {

  constructor(private http: HttpClient) {
  }

  userActive(): Observable<BackServiceResponseModel<AuthorityModel[]>> {
    return this.http.get<BackServiceResponseModel<AuthorityModel[]>>(BackServiceUrls.AUTHORITY_TYPE_USER_ACTIVE);
  }

  assignAuthorities(id: string, model: AuthorityModel[]): Observable<BackServiceResponseModel<boolean>> {
    let url = BackServiceUrls.AUTHORITY_ASSIGN_AUTHORITIES;
    url = url.replace("${id}", id);
    return this.http.put<BackServiceResponseModel<boolean>>(url, model);
  }
}
