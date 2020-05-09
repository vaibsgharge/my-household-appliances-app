import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplianceCategories } from 'app/shared/model/appliance-categories.model';

@Component({
  selector: 'jhi-appliance-categories-detail',
  templateUrl: './appliance-categories-detail.component.html'
})
export class ApplianceCategoriesDetailComponent implements OnInit {
  applianceCategories: IApplianceCategories | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applianceCategories }) => (this.applianceCategories = applianceCategories));
  }

  previousState(): void {
    window.history.back();
  }
}
