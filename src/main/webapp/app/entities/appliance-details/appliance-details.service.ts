import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplianceDetails } from 'app/shared/model/appliance-details.model';

type EntityResponseType = HttpResponse<IApplianceDetails>;
type EntityArrayResponseType = HttpResponse<IApplianceDetails[]>;

@Injectable({ providedIn: 'root' })
export class ApplianceDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/appliance-details';

  constructor(protected http: HttpClient) {}

  create(applianceDetails: IApplianceDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applianceDetails);
    return this.http
      .post<IApplianceDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applianceDetails: IApplianceDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applianceDetails);
    return this.http
      .put<IApplianceDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplianceDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplianceDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applianceDetails: IApplianceDetails): IApplianceDetails {
    const copy: IApplianceDetails = Object.assign({}, applianceDetails, {
      createdAt:
        applianceDetails.createdAt && applianceDetails.createdAt.isValid() ? applianceDetails.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt:
        applianceDetails.updatedAt && applianceDetails.updatedAt.isValid() ? applianceDetails.updatedAt.format(DATE_FORMAT) : undefined
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
      res.body.forEach((applianceDetails: IApplianceDetails) => {
        applianceDetails.createdAt = applianceDetails.createdAt ? moment(applianceDetails.createdAt) : undefined;
        applianceDetails.updatedAt = applianceDetails.updatedAt ? moment(applianceDetails.updatedAt) : undefined;
      });
    }
    return res;
  }
}
