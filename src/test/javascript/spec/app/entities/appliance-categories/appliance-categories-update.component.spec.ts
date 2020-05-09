import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HouseholdAplicationTestModule } from '../../../test.module';
import { ApplianceCategoriesUpdateComponent } from 'app/entities/appliance-categories/appliance-categories-update.component';
import { ApplianceCategoriesService } from 'app/entities/appliance-categories/appliance-categories.service';
import { ApplianceCategories } from 'app/shared/model/appliance-categories.model';

describe('Component Tests', () => {
  describe('ApplianceCategories Management Update Component', () => {
    let comp: ApplianceCategoriesUpdateComponent;
    let fixture: ComponentFixture<ApplianceCategoriesUpdateComponent>;
    let service: ApplianceCategoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HouseholdAplicationTestModule],
        declarations: [ApplianceCategoriesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplianceCategoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplianceCategoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplianceCategoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplianceCategories(123);
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
        const entity = new ApplianceCategories();
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
