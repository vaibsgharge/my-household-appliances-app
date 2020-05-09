import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HouseholdAplicationSharedModule } from 'app/shared/shared.module';
import { ApplianceCategoriesComponent } from './appliance-categories.component';
import { ApplianceCategoriesDetailComponent } from './appliance-categories-detail.component';
import { ApplianceCategoriesUpdateComponent } from './appliance-categories-update.component';
import { ApplianceCategoriesDeleteDialogComponent } from './appliance-categories-delete-dialog.component';
import { applianceCategoriesRoute } from './appliance-categories.route';

@NgModule({
  imports: [HouseholdAplicationSharedModule, RouterModule.forChild(applianceCategoriesRoute)],
  declarations: [
    ApplianceCategoriesComponent,
    ApplianceCategoriesDetailComponent,
    ApplianceCategoriesUpdateComponent,
    ApplianceCategoriesDeleteDialogComponent
  ],
  entryComponents: [ApplianceCategoriesDeleteDialogComponent]
})
export class HouseholdAplicationApplianceCategoriesModule {}
