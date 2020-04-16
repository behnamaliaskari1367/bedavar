import {MatPaginatorIntl} from '@angular/material';
import { Injectable } from "@angular/core";

@Injectable()
export class MaterialPaginatorIntlProvider extends MatPaginatorIntl {

  itemsPerPageLabel = 'تعداد در صفحه';
  nextPageLabel = 'صفحه بعدی';
  previousPageLabel = 'صفحه قبلی';
  firstPageLabel = 'صفحه اول';
  lastPageLabel = 'صفحه آخر';

  getRangeLabel = function (page, pageSize, length) {
    if (length === 0 || pageSize === 0) {
      return '0 از ' + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + ' از ' + length;
  };
}
