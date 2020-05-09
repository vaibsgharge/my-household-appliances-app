import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HouseholdAplicationSharedModule } from 'app/shared/shared.module';
import { ApplianceDetailsComponent } from './appliance-details.component';
import { ApplianceDetailsDetailComponent } from './appliance-details-detail.component';
import { ApplianceDetailsUpdateComponent } from './appliance-details-update.component';
import { ApplianceDetailsDeleteDialogComponent } from './appliance-details-delete-dialog.component';
import { applianceDetailsRoute } from './appliance-details.route';

@NgModule({
  imports: [HouseholdAplicationSharedModule, RouterModule.forChild(applianceDetailsRoute)],
  declarations: [
    ApplianceDetailsComponent,
    ApplianceDetailsDetailComponent,
    ApplianceDetailsUpdateComponent,
    ApplianceDetailsDeleteDialogComponent
  ],
  entryComponents: [ApplianceDetailsDeleteDialogComponent]
})
export class HouseholdAplicationApplianceDetailsModule {}
