import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplianceDetails } from 'app/shared/model/appliance-details.model';

@Component({
  selector: 'jhi-appliance-details-detail',
  templateUrl: './appliance-details-detail.component.html'
})
export class ApplianceDetailsDetailComponent implements OnInit {
  applianceDetails: IApplianceDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applianceDetails }) => (this.applianceDetails = applianceDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
