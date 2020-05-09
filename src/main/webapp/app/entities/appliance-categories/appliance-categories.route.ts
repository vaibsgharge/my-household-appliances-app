import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApplianceCategories, ApplianceCategories } from 'app/shared/model/appliance-categories.model';
import { ApplianceCategoriesService } from './appliance-categories.service';
import { ApplianceCategoriesComponent } from './appliance-categories.component';
import { ApplianceCategoriesDetailComponent } from './appliance-categories-detail.component';
import { ApplianceCategoriesUpdateComponent } from './appliance-categories-update.component';

@Injectable({ providedIn: 'root' })
export class ApplianceCategoriesResolve implements Resolve<IApplianceCategories> {
  constructor(private service: ApplianceCategoriesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplianceCategories> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applianceCategories: HttpResponse<ApplianceCategories>) => {
          if (applianceCategories.body) {
            return of(applianceCategories.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplianceCategories());
  }
}

export const applianceCategoriesRoute: Routes = [
  {
    path: '',
    component: ApplianceCategoriesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplianceCategoriesDetailComponent,
    resolve: {
      applianceCategories: ApplianceCategoriesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplianceCategoriesUpdateComponent,
    resolve: {
      applianceCategories: ApplianceCategoriesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplianceCategoriesUpdateComponent,
    resolve: {
      applianceCategories: ApplianceCategoriesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplianceCategories'
    },
    canActivate: [UserRouteAccessService]
  }
];
