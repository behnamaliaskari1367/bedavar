import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {MatPaginator} from '@angular/material';
import {UserService} from "../../../service/user.service";
import {CustomRequestModel, UserModel} from "../../../../model/model";

/**
 * @author Abbas Alishirvani
 */
export class UserDataSource implements DataSource<UserModel> {

  public totalRecordSubject = new BehaviorSubject<number>(0);
  private subject = new BehaviorSubject<UserModel[]>([]);
  private currentPage: number;
  private totalPage: number;

  constructor(private service: UserService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<UserModel[]> {
    return this.subject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.subject.complete();
  }

  search(paginator: MatPaginator, request: CustomRequestModel<UserModel>) {

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
