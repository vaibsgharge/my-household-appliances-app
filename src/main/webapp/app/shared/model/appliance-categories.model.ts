import { Moment } from 'moment';
import { IApplianceDetails } from 'app/shared/model/appliance-details.model';

export interface IApplianceCategories {
  id?: number;
  categoryName?: string;
  categoryDesc?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  appliances?: IApplianceDetails[];
}

export class ApplianceCategories implements IApplianceCategories {
  constructor(
    public id?: number,
    public categoryName?: string,
    public categoryDesc?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public appliances?: IApplianceDetails[]
  ) {}
}
