import type { ShippingAddress } from "./ShippingAddress";

export interface Order {
    id: number;
    basketId: string;
    shippingAddress: ShippingAddress;
    orderDate: Date;
    orderStatus: string;
    subTotal: number;
    deliveryFee: number;
    total: number;
}