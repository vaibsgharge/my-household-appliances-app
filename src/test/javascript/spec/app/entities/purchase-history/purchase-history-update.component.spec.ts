import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HouseholdAplicationTestModule } from '../../../test.module';
import { PurchaseHistoryUpdateComponent } from 'app/entities/purchase-history/purchase-history-update.component';
import { PurchaseHistoryService } from 'app/entities/purchase-history/purchase-history.service';
import { PurchaseHistory } from 'app/shared/model/purchase-history.model';

describe('Component Tests', () => {
  describe('PurchaseHistory Management Update Component', () => {
    let comp: PurchaseHistoryUpdateComponent;
    let fixture: ComponentFixture<PurchaseHistoryUpdateComponent>;
    let service: PurchaseHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HouseholdAplicationTestModule],
        declarations: [PurchaseHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PurchaseHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PurchaseHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurchaseHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PurchaseHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PurchaseHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
