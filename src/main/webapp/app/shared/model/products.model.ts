import { OrderItemType } from './invoice.model';
import { generateOrderName, Invoice } from 'app/shared/model/invoice.model';

export interface CartItem {
    selection: string;
}

export interface Product {
    title: string;
    type: OrderItemType;
    price: number;
    options: string[];
    cart: CartItem[];
}

const PRODUCT_1: Product = {
    title: 'Product 1',
    type: OrderItemType.PRODUCT_1,
    price: 5.0,
    options: ['"Option 1"', '"Option 2"'],
    cart: []
};
const PRODUCT_2: Product = {
    title: 'Product 2',
    type: OrderItemType.PRODUCT_2,
    price: 5.0,
    options: ['"Option 1"', '"Option 2"'],
    cart: []
};
const PRODUCT_3: Product = {
    title: 'Product 3',
    type: OrderItemType.PRODUCT_3,
    price: 5.0,
    options: ['"Option 1"', '"Option 2"'],
    cart: []
};
const PRODUCT_4: Product = {
    title: 'Product 4',
    type: OrderItemType.PRODUCT_4,
    price: 5.0,
    options: ['"Option 1"', '"Option 2"'],
    cart: []
};
const PRODUCTS: Product[] = [PRODUCT_1, PRODUCT_2, PRODUCT_3, PRODUCT_4];

export function clone(obj) {
    return JSON.parse(JSON.stringify(obj));
}

export function getProducts() {
    return clone(PRODUCTS);
}

export function getProductByType(type: OrderItemType): Product {
    return getProducts().filter(p => p.type === type)[0] || {};
}

export function getTitleByType(type: OrderItemType): string {
    return getProductByType(type).title;
}

export function getPriceByType(type: OrderItemType): string {
    return (<any>getProductByType(type)).price;
}

export function getSelfServiceOrders(): Invoice[] {
    return getSelfServiceItems().map(item => ({
        orderName: generateOrderName(),
        orderItems: [item],
        autoGenerated: true
    }));
}

export function getSelfServiceItems() {
    return [
        {
            itemType: OrderItemType.PRODUCT_1,
            count: 1,
            options: ['"Flavor 1"']
        },
        {
            itemType: OrderItemType.PRODUCT_2,
            count: 1,
            options: ['"Flavor 2"']
        },
        {
            itemType: OrderItemType.PRODUCT_3,
            count: 1,
            options: ['"Flavor 3"']
        },
        {
            itemType: OrderItemType.PRODUCT_4,
            count: 1,
            options: ['"Flavor 4"']
        }
    ];
}
