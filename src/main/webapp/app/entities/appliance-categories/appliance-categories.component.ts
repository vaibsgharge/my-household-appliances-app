import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplianceCategories } from 'app/shared/model/appliance-categories.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ApplianceCategoriesService } from './appliance-categories.service';
import { ApplianceCategoriesDeleteDialogComponent } from './appliance-categories-delete-dialog.component';

@Component({
  selector: 'jhi-appliance-categories',
  templateUrl: './appliance-categories.component.html'
})
export class ApplianceCategoriesComponent implements OnInit, OnDestroy {
  applianceCategories: IApplianceCategories[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected applianceCategoriesService: ApplianceCategoriesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.applianceCategories = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.applianceCategoriesService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IApplianceCategories[]>) => this.paginateApplianceCategories(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.applianceCategories = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInApplianceCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IApplianceCategories): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInApplianceCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('applianceCategoriesListModification', () => this.reset());
  }

  delete(applianceCategories: IApplianceCategories): void {
    const modalRef = this.modalService.open(ApplianceCategoriesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.applianceCategories = applianceCategories;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateApplianceCategories(data: IApplianceCategories[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.applianceCategories.push(data[i]);
      }
    }
  }
}
