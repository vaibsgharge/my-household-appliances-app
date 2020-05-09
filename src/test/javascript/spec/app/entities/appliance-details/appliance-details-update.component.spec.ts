import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HouseholdAplicationTestModule } from '../../../test.module';
import { ApplianceDetailsUpdateComponent } from 'app/entities/appliance-details/appliance-details-update.component';
import { ApplianceDetailsService } from 'app/entities/appliance-details/appliance-details.service';
import { ApplianceDetails } from 'app/shared/model/appliance-details.model';

describe('Component Tests', () => {
  describe('ApplianceDetails Management Update Component', () => {
    let comp: ApplianceDetailsUpdateComponent;
    let fixture: ComponentFixture<ApplianceDetailsUpdateComponent>;
    let service: ApplianceDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HouseholdAplicationTestModule],
        declarations: [ApplianceDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplianceDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplianceDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplianceDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplianceDetails(123);
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
        const entity = new ApplianceDetails();
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
