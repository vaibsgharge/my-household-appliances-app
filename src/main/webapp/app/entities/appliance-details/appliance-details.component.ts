import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplianceDetails } from 'app/shared/model/appliance-details.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ApplianceDetailsService } from './appliance-details.service';
import { ApplianceDetailsDeleteDialogComponent } from './appliance-details-delete-dialog.component';

@Component({
  selector: 'jhi-appliance-details',
  templateUrl: './appliance-details.component.html'
})
export class ApplianceDetailsComponent implements OnInit, OnDestroy {
  applianceDetails: IApplianceDetails[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected applianceDetailsService: ApplianceDetailsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.applianceDetails = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.applianceDetailsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IApplianceDetails[]>) => this.paginateApplianceDetails(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.applianceDetails = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInApplianceDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IApplianceDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInApplianceDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('applianceDetailsListModification', () => this.reset());
  }

  delete(applianceDetails: IApplianceDetails): void {
    const modalRef = this.modalService.open(ApplianceDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.applianceDetails = applianceDetails;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateApplianceDetails(data: IApplianceDetails[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.applianceDetails.push(data[i]);
      }
    }
  }
}
