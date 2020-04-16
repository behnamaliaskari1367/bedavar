import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {CustomRequestModel, CustomResponseModel, ScheduleParamModel} from "../../model/model";
import {BackServiceUrls} from "../../model/urls";
import {BackServiceResponseModel} from "@fartak/backservice-common";

/**
 * @author Abbas Alishirvani
 */
@Injectable()
export class SchedulerService {

  constructor(private http: HttpClient) {
  }

  get(id: string): Observable<BackServiceResponseModel<ScheduleParamModel>> {
    let url = BackServiceUrls.SCHEDULER_PARAM_ID;
    url = url.replace("${id}", id.toString());
    return this.http.get<BackServiceResponseModel<ScheduleParamModel>>(url);
  }

  delete(id: number): Observable<BackServiceResponseModel<ScheduleParamModel>> {
    let url = BackServiceUrls.SCHEDULER_PARAM_ID;
    url = url.replace("${id}", id.toString());
    return this.http.delete<BackServiceResponseModel<ScheduleParamModel>>(url);
  }

  add(model: ScheduleParamModel): Observable<BackServiceResponseModel<ScheduleParamModel>> {
    return this.http.post<BackServiceResponseModel<ScheduleParamModel>>(BackServiceUrls.SCHEDULER_PARAM_BASE, model);
  }

  edit(model: ScheduleParamModel): Observable<BackServiceResponseModel<ScheduleParamModel>> {
    return this.http.put<BackServiceResponseModel<ScheduleParamModel>>(BackServiceUrls.SCHEDULER_PARAM_BASE, model);
  }

  search(model: CustomRequestModel<ScheduleParamModel>): Observable<CustomResponseModel<ScheduleParamModel>> {
    return this.http.post<CustomResponseModel<ScheduleParamModel>>(BackServiceUrls.SCHEDULER_PARAM_SEARCH, model);
  }
}
