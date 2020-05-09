import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPurchaseHistory } from 'app/shared/model/purchase-history.model';

type EntityResponseType = HttpResponse<IPurchaseHistory>;
type EntityArrayResponseType = HttpResponse<IPurchaseHistory[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/purchase-histories';

  constructor(protected http: HttpClient) {}

  create(purchaseHistory: IPurchaseHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseHistory);
    return this.http
      .post<IPurchaseHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(purchaseHistory: IPurchaseHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseHistory);
    return this.http
      .put<IPurchaseHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPurchaseHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPurchaseHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(purchaseHistory: IPurchaseHistory): IPurchaseHistory {
    const copy: IPurchaseHistory = Object.assign({}, purchaseHistory, {
      purchaseDate:
        purchaseHistory.purchaseDate && purchaseHistory.purchaseDate.isValid()
          ? purchaseHistory.purchaseDate.format(DATE_FORMAT)
          : undefined,
      createdAt:
        purchaseHistory.createdAt && purchaseHistory.createdAt.isValid() ? purchaseHistory.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt:
        purchaseHistory.updatedAt && purchaseHistory.updatedAt.isValid() ? purchaseHistory.updatedAt.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.purchaseDate = res.body.purchaseDate ? moment(res.body.purchaseDate) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((purchaseHistory: IPurchaseHistory) => {
        purchaseHistory.purchaseDate = purchaseHistory.purchaseDate ? moment(purchaseHistory.purchaseDate) : undefined;
        purchaseHistory.createdAt = purchaseHistory.createdAt ? moment(purchaseHistory.createdAt) : undefined;
        purchaseHistory.updatedAt = purchaseHistory.updatedAt ? moment(purchaseHistory.updatedAt) : undefined;
      });
    }
    return res;
  }
}
