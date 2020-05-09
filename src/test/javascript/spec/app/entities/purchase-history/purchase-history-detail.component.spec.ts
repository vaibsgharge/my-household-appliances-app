import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HouseholdAplicationTestModule } from '../../../test.module';
import { PurchaseHistoryDetailComponent } from 'app/entities/purchase-history/purchase-history-detail.component';
import { PurchaseHistory } from 'app/shared/model/purchase-history.model';

describe('Component Tests', () => {
  describe('PurchaseHistory Management Detail Component', () => {
    let comp: PurchaseHistoryDetailComponent;
    let fixture: ComponentFixture<PurchaseHistoryDetailComponent>;
    const route = ({ data: of({ purchaseHistory: new PurchaseHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HouseholdAplicationTestModule],
        declarations: [PurchaseHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PurchaseHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PurchaseHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load purchaseHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.purchaseHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
