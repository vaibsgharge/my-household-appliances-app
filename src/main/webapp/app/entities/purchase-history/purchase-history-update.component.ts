import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPurchaseHistory, PurchaseHistory } from 'app/shared/model/purchase-history.model';
import { PurchaseHistoryService } from './purchase-history.service';

@Component({
  selector: 'jhi-purchase-history-update',
  templateUrl: './purchase-history-update.component.html'
})
export class PurchaseHistoryUpdateComponent implements OnInit {
  isSaving = false;
  purchaseDateDp: any;
  createdAtDp: any;
  updatedAtDp: any;

  editForm = this.fb.group({
    id: [],
    serialNumber: [],
    purchaseDate: [],
    createdAt: [],
    updatedAt: []
  });

  constructor(
    protected purchaseHistoryService: PurchaseHistoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseHistory }) => {
      this.updateForm(purchaseHistory);
    });
  }

  updateForm(purchaseHistory: IPurchaseHistory): void {
    this.editForm.patchValue({
      id: purchaseHistory.id,
      serialNumber: purchaseHistory.serialNumber,
      purchaseDate: purchaseHistory.purchaseDate,
      createdAt: purchaseHistory.createdAt,
      updatedAt: purchaseHistory.updatedAt
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchaseHistory = this.createFromForm();
    if (purchaseHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseHistoryService.update(purchaseHistory));
    } else {
      this.subscribeToSaveResponse(this.purchaseHistoryService.create(purchaseHistory));
    }
  }

  private createFromForm(): IPurchaseHistory {
    return {
      ...new PurchaseHistory(),
      id: this.editForm.get(['id'])!.value,
      serialNumber: this.editForm.get(['serialNumber'])!.value,
      purchaseDate: this.editForm.get(['purchaseDate'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseHistory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
