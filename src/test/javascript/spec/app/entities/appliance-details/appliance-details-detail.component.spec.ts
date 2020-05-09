import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HouseholdAplicationTestModule } from '../../../test.module';
import { ApplianceDetailsDetailComponent } from 'app/entities/appliance-details/appliance-details-detail.component';
import { ApplianceDetails } from 'app/shared/model/appliance-details.model';

describe('Component Tests', () => {
  describe('ApplianceDetails Management Detail Component', () => {
    let comp: ApplianceDetailsDetailComponent;
    let fixture: ComponentFixture<ApplianceDetailsDetailComponent>;
    const route = ({ data: of({ applianceDetails: new ApplianceDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HouseholdAplicationTestModule],
        declarations: [ApplianceDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplianceDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplianceDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applianceDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applianceDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
