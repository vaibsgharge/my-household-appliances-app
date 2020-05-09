import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPurchaseHistory, PurchaseHistory } from 'app/shared/model/purchase-history.model';
import { PurchaseHistoryService } from './purchase-history.service';
import { PurchaseHistoryComponent } from './purchase-history.component';
import { PurchaseHistoryDetailComponent } from './purchase-history-detail.component';
import { PurchaseHistoryUpdateComponent } from './purchase-history-update.component';

@Injectable({ providedIn: 'root' })
export class PurchaseHistoryResolve implements Resolve<IPurchaseHistory> {
  constructor(private service: PurchaseHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPurchaseHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((purchaseHistory: HttpResponse<PurchaseHistory>) => {
          if (purchaseHistory.body) {
            return of(purchaseHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PurchaseHistory());
  }
}

export const purchaseHistoryRoute: Routes = [
  {
    path: '',
    component: PurchaseHistoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PurchaseHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PurchaseHistoryDetailComponent,
    resolve: {
      purchaseHistory: PurchaseHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PurchaseHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PurchaseHistoryUpdateComponent,
    resolve: {
      purchaseHistory: PurchaseHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PurchaseHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PurchaseHistoryUpdateComponent,
    resolve: {
      purchaseHistory: PurchaseHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PurchaseHistories'
    },
    canActivate: [UserRouteAccessService]
  }
];
