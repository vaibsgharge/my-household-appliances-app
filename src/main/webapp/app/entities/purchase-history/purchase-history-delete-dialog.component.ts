import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseHistory } from 'app/shared/model/purchase-history.model';
import { PurchaseHistoryService } from './purchase-history.service';

@Component({
  templateUrl: './purchase-history-delete-dialog.component.html'
})
export class PurchaseHistoryDeleteDialogComponent {
  purchaseHistory?: IPurchaseHistory;

  constructor(
    protected purchaseHistoryService: PurchaseHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.purchaseHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('purchaseHistoryListModification');
      this.activeModal.close();
    });
  }
}
