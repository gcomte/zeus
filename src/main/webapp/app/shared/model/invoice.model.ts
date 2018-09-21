import { Moment } from 'moment';

export enum OrderItemType {
    PRODUCT_1 = 'PRODUCT_1',
    PRODUCT_2 = 'PRODUCT_2',
    PRODUCT_3 = 'PRODUCT_3',
    PRODUCT_4 = 'PRODUCT_4'
}

export interface OrderItem {
    id?: number;
    itemType?: OrderItemType;
    count?: number;
    options?: string[];
    total?: number;
}

export interface Invoice {
    id?: number;
    referenceId?: string;
    referenceIdShort?: string;
    memo?: string;
    hashHex?: string;
    preimageHex?: string;
    amount?: number;
    amountChf?: number;
    exchangeRate?: number;
    orderName?: string;
    settled?: boolean;
    creationDate?: Moment;
    settleDate?: Moment;
    paymentRequest?: string;
    orderItems?: OrderItem[];
    total?: number;
    autoGenerated?: boolean;
}

export const COLORS = ['Blue', 'Red', 'Green', 'Yellow', 'White', 'Black', 'Purple', 'Pink', 'Brown', 'Grey', 'Orange'];

export const OBJECTS = ['Door', 'Flag', 'Cat', 'Dog', 'Card', 'Shirt', 'Sofa', 'Bottle', 'Bus', 'Hat', 'Vase', 'Paper'];

export function random(arr) {
    return arr[Math.floor(Math.random() * arr.length)];
}

export function generateOrderName() {
    return random(COLORS) + ' ' + random(OBJECTS);
}
