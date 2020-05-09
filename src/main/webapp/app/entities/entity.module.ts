import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'appliance-categories',
        loadChildren: () =>
          import('./appliance-categories/appliance-categories.module').then(m => m.HouseholdAplicationApplianceCategoriesModule)
      },
      {
        path: 'appliance-details',
        loadChildren: () => import('./appliance-details/appliance-details.module').then(m => m.HouseholdAplicationApplianceDetailsModule)
      },
      {
        path: 'purchase-history',
        loadChildren: () => import('./purchase-history/purchase-history.module').then(m => m.HouseholdAplicationPurchaseHistoryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HouseholdAplicationEntityModule {}
