import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {MatPaginator} from '@angular/material';
import {SchedulerService} from "../../service/scheduler.service";
import {CustomRequestModel, ScheduleParamModel} from "../../../model/model";

/**
 * @author Abbas Alishirvani
 */
export class ScheduleParamDataSource implements DataSource<ScheduleParamModel> {

  public totalRecordSubject = new BehaviorSubject<number>(0);
  private subject = new BehaviorSubject<ScheduleParamModel[]>([]);
  private currentPage: number;
  private totalPage: number;

  constructor(private service: SchedulerService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<ScheduleParamModel[]> {
    return this.subject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.subject.complete();
  }

  search(paginator: MatPaginator, request: CustomRequestModel<ScheduleParamModel>) {

    this.service.search(request)
      .pipe(map(r => {
        if (r) {
          this.currentPage = r.currentPage;
          this.totalPage = r.totalPage;
          this.totalRecordSubject.next(r.totalRecords);
          return r.response;
        } else {
          return [];
        }
      }))
      .subscribe(r => this.subject.next(r));
  }
}
