import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IApplianceCategories, ApplianceCategories } from 'app/shared/model/appliance-categories.model';
import { ApplianceCategoriesService } from './appliance-categories.service';

@Component({
  selector: 'jhi-appliance-categories-update',
  templateUrl: './appliance-categories-update.component.html'
})
export class ApplianceCategoriesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    categoryName: [null, [Validators.required]],
    categoryDesc: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: []
  });

  constructor(
    protected applianceCategoriesService: ApplianceCategoriesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applianceCategories }) => {
      if (!applianceCategories.id) {
        const today = moment().startOf('day');
        applianceCategories.createdAt = today;
        applianceCategories.updatedAt = today;
      }

      this.updateForm(applianceCategories);
    });
  }

  updateForm(applianceCategories: IApplianceCategories): void {
    this.editForm.patchValue({
      id: applianceCategories.id,
      categoryName: applianceCategories.categoryName,
      categoryDesc: applianceCategories.categoryDesc,
      createdAt: applianceCategories.createdAt ? applianceCategories.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: applianceCategories.updatedAt ? applianceCategories.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applianceCategories = this.createFromForm();
    if (applianceCategories.id !== undefined) {
      this.subscribeToSaveResponse(this.applianceCategoriesService.update(applianceCategories));
    } else {
      this.subscribeToSaveResponse(this.applianceCategoriesService.create(applianceCategories));
    }
  }

  private createFromForm(): IApplianceCategories {
    return {
      ...new ApplianceCategories(),
      id: this.editForm.get(['id'])!.value,
      categoryName: this.editForm.get(['categoryName'])!.value,
      categoryDesc: this.editForm.get(['categoryDesc'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplianceCategories>>): void {
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
