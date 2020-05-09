import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IApplianceDetails, ApplianceDetails } from 'app/shared/model/appliance-details.model';
import { ApplianceDetailsService } from './appliance-details.service';
import { IPurchaseHistory } from 'app/shared/model/purchase-history.model';
import { PurchaseHistoryService } from 'app/entities/purchase-history/purchase-history.service';
import { IApplianceCategories } from 'app/shared/model/appliance-categories.model';
import { ApplianceCategoriesService } from 'app/entities/appliance-categories/appliance-categories.service';

type SelectableEntity = IPurchaseHistory | IApplianceCategories;

@Component({
  selector: 'jhi-appliance-details-update',
  templateUrl: './appliance-details-update.component.html'
})
export class ApplianceDetailsUpdateComponent implements OnInit {
  isSaving = false;
  purchases: IPurchaseHistory[] = [];
  appliancecategories: IApplianceCategories[] = [];
  createdAtDp: any;
  updatedAtDp: any;

  editForm = this.fb.group({
    id: [],
    serialNumber: [null, [Validators.required]],
    applianceName: [null, [Validators.required]],
    applianceDesc: [null, [Validators.required]],
    categoryId: [],
    status: [],
    applianceBrand: [],
    applianceModel: [],
    purchaseId: [],
    createdAt: [],
    updatedAt: [],
    purchasesId: [],
    categoriesId: []
  });

  constructor(
    protected applianceDetailsService: ApplianceDetailsService,
    protected purchaseHistoryService: PurchaseHistoryService,
    protected applianceCategoriesService: ApplianceCategoriesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applianceDetails }) => {
      this.updateForm(applianceDetails);

      this.purchaseHistoryService
        .query({ filter: 'appliancedetails-is-null' })
        .pipe(
          map((res: HttpResponse<IPurchaseHistory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPurchaseHistory[]) => {
          if (!applianceDetails.purchasesId) {
            this.purchases = resBody;
          } else {
            this.purchaseHistoryService
              .find(applianceDetails.purchasesId)
              .pipe(
                map((subRes: HttpResponse<IPurchaseHistory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPurchaseHistory[]) => (this.purchases = concatRes));
          }
        });

      this.applianceCategoriesService
        .query()
        .subscribe((res: HttpResponse<IApplianceCategories[]>) => (this.appliancecategories = res.body || []));
    });
  }

  updateForm(applianceDetails: IApplianceDetails): void {
    this.editForm.patchValue({
      id: applianceDetails.id,
      serialNumber: applianceDetails.serialNumber,
      applianceName: applianceDetails.applianceName,
      applianceDesc: applianceDetails.applianceDesc,
      categoryId: applianceDetails.categoryId,
      status: applianceDetails.status,
      applianceBrand: applianceDetails.applianceBrand,
      applianceModel: applianceDetails.applianceModel,
      purchaseId: applianceDetails.purchaseId,
      createdAt: applianceDetails.createdAt,
      updatedAt: applianceDetails.updatedAt,
      purchasesId: applianceDetails.purchasesId,
      categoriesId: applianceDetails.categoriesId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applianceDetails = this.createFromForm();
    if (applianceDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.applianceDetailsService.update(applianceDetails));
    } else {
      this.subscribeToSaveResponse(this.applianceDetailsService.create(applianceDetails));
    }
  }

  private createFromForm(): IApplianceDetails {
    return {
      ...new ApplianceDetails(),
      id: this.editForm.get(['id'])!.value,
      serialNumber: this.editForm.get(['serialNumber'])!.value,
      applianceName: this.editForm.get(['applianceName'])!.value,
      applianceDesc: this.editForm.get(['applianceDesc'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
      status: this.editForm.get(['status'])!.value,
      applianceBrand: this.editForm.get(['applianceBrand'])!.value,
      applianceModel: this.editForm.get(['applianceModel'])!.value,
      purchaseId: this.editForm.get(['purchaseId'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      purchasesId: this.editForm.get(['purchasesId'])!.value,
      categoriesId: this.editForm.get(['categoriesId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplianceDetails>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
