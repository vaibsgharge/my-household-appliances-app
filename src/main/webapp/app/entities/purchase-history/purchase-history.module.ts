import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HouseholdAplicationSharedModule } from 'app/shared/shared.module';
import { PurchaseHistoryComponent } from './purchase-history.component';
import { PurchaseHistoryDetailComponent } from './purchase-history-detail.component';
import { PurchaseHistoryUpdateComponent } from './purchase-history-update.component';
import { PurchaseHistoryDeleteDialogComponent } from './purchase-history-delete-dialog.component';
import { purchaseHistoryRoute } from './purchase-history.route';

@NgModule({
  imports: [HouseholdAplicationSharedModule, RouterModule.forChild(purchaseHistoryRoute)],
  declarations: [
    PurchaseHistoryComponent,
    PurchaseHistoryDetailComponent,
    PurchaseHistoryUpdateComponent,
    PurchaseHistoryDeleteDialogComponent
  ],
  entryComponents: [PurchaseHistoryDeleteDialogComponent]
})
export class HouseholdAplicationPurchaseHistoryModule {}
