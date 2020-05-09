import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IApplianceDetails {
  id?: number;
  serialNumber?: string;
  applianceName?: string;
  applianceDesc?: string;
  categoryId?: number;
  status?: Status;
  applianceBrand?: string;
  applianceModel?: string;
  purchaseId?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  purchasesId?: number;
  categoriesId?: number;
}

export class ApplianceDetails implements IApplianceDetails {
  constructor(
    public id?: number,
    public serialNumber?: string,
    public applianceName?: string,
    public applianceDesc?: string,
    public categoryId?: number,
    public status?: Status,
    public applianceBrand?: string,
    public applianceModel?: string,
    public purchaseId?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public purchasesId?: number,
    public categoriesId?: number
  ) {}
}
