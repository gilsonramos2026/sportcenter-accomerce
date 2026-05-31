export interface BasketItem {
    id: number;
    name: string;
    description: string;
    price: number;
    pictureUrl: string;
    productBrand: string;
    productType: string;
    quantity: number;
}

export interface Basket {
    id: string;
    items: BasketItem[];
    // Adicionamos clientSecret e paymentIntentId caso o seu fluxo de checkout 
    // precise interagir com o Stripe, o que é comum em projetos React
    clientSecret?: string;
    paymentIntentId?: string;
}

export interface BasketTotals {
    shipping: number;
    subTotal: number;
    total: number;
}