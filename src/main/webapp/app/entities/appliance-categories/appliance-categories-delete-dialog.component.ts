import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplianceCategories } from 'app/shared/model/appliance-categories.model';
import { ApplianceCategoriesService } from './appliance-categories.service';

@Component({
  templateUrl: './appliance-categories-delete-dialog.component.html'
})
export class ApplianceCategoriesDeleteDialogComponent {
  applianceCategories?: IApplianceCategories;

  constructor(
    protected applianceCategoriesService: ApplianceCategoriesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applianceCategoriesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applianceCategoriesListModification');
      this.activeModal.close();
    });
  }
}
