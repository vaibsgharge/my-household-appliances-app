import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HouseholdAplicationSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [HouseholdAplicationSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class HouseholdAplicationHomeModule {}
