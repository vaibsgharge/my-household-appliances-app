import { Moment } from 'moment';

export interface IPurchaseHistory {
  id?: number;
  serialNumber?: string;
  purchaseDate?: Moment;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export class PurchaseHistory implements IPurchaseHistory {
  constructor(
    public id?: number,
    public serialNumber?: string,
    public purchaseDate?: Moment,
    public createdAt?: Moment,
    public updatedAt?: Moment
  ) {}
}
