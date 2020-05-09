import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplianceDetails } from 'app/shared/model/appliance-details.model';
import { ApplianceDetailsService } from './appliance-details.service';

@Component({
  templateUrl: './appliance-details-delete-dialog.component.html'
})
export class ApplianceDetailsDeleteDialogComponent {
  applianceDetails?: IApplianceDetails;

  constructor(
    protected applianceDetailsService: ApplianceDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applianceDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applianceDetailsListModification');
      this.activeModal.close();
    });
  }
}
