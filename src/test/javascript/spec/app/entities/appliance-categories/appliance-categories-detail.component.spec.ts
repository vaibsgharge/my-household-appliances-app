import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HouseholdAplicationTestModule } from '../../../test.module';
import { ApplianceCategoriesDetailComponent } from 'app/entities/appliance-categories/appliance-categories-detail.component';
import { ApplianceCategories } from 'app/shared/model/appliance-categories.model';

describe('Component Tests', () => {
  describe('ApplianceCategories Management Detail Component', () => {
    let comp: ApplianceCategoriesDetailComponent;
    let fixture: ComponentFixture<ApplianceCategoriesDetailComponent>;
    const route = ({ data: of({ applianceCategories: new ApplianceCategories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HouseholdAplicationTestModule],
        declarations: [ApplianceCategoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplianceCategoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplianceCategoriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applianceCategories on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applianceCategories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
