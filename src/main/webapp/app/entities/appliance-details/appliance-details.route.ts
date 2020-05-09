import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApplianceDetails, ApplianceDetails } from 'app/shared/model/appliance-details.model';
import { ApplianceDetailsService } from './appliance-details.service';
import { ApplianceDetailsComponent } from './appliance-details.component';
import { ApplianceDetailsDetailComponent } from './appliance-details-detail.component';
import { ApplianceDetailsUpdateComponent } from './appliance-details-update.component';

@Injectable({ providedIn: 'root' })
export class ApplianceDetailsResolve implements Resolve<IApplianceDetails> {
  constructor(private service: ApplianceDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplianceDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applianceDetails: HttpResponse<ApplianceDetails>) => {
          if (applianceDetails.body) {
            return of(applianceDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplianceDetails());
  }
}

export const applianceDetailsRoute: Routes = [
  {
    path: '',
    component: ApplianceDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplianceDetailsDetailComponent,
    resolve: {
      applianceDetails: ApplianceDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplianceDetailsUpdateComponent,
    resolve: {
      applianceDetails: ApplianceDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplianceDetailsUpdateComponent,
    resolve: {
      applianceDetails: ApplianceDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceDetails'
    },
    canActivate: [UserRouteAccessService]
  }
];
