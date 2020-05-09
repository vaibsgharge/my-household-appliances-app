import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplianceCategories } from 'app/shared/model/appliance-categories.model';

type EntityResponseType = HttpResponse<IApplianceCategories>;
type EntityArrayResponseType = HttpResponse<IApplianceCategories[]>;

@Injectable({ providedIn: 'root' })
export class ApplianceCategoriesService {
  public resourceUrl = SERVER_API_URL + 'api/appliance-categories';

  constructor(protected http: HttpClient) {}

  create(applianceCategories: IApplianceCategories): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applianceCategories);
    return this.http
      .post<IApplianceCategories>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applianceCategories: IApplianceCategories): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applianceCategories);
    return this.http
      .put<IApplianceCategories>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplianceCategories>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplianceCategories[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applianceCategories: IApplianceCategories): IApplianceCategories {
    const copy: IApplianceCategories = Object.assign({}, applianceCategories, {
      createdAt:
        applianceCategories.createdAt && applianceCategories.createdAt.isValid() ? applianceCategories.createdAt.toJSON() : undefined,
      updatedAt:
        applianceCategories.updatedAt && applianceCategories.updatedAt.isValid() ? applianceCategories.updatedAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((applianceCategories: IApplianceCategories) => {
        applianceCategories.createdAt = applianceCategories.createdAt ? moment(applianceCategories.createdAt) : undefined;
        applianceCategories.updatedAt = applianceCategories.updatedAt ? moment(applianceCategories.updatedAt) : undefined;
      });
    }
    return res;
  }
}
