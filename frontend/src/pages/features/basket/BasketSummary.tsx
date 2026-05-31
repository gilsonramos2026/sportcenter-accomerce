import { useAppSelector } from "../../app/store/configureStore";

export function BasketSummary() {
    const { basket } = useAppSelector(state => state.basket);
    const subTotal = basket?.items.reduce((sum, item) => sum + (item.quantity * item.price), 0) ?? 0;
    const shipping = 200;
    const total = subTotal + shipping;

    const formatPrice = (price: number): string => {
        return new Intl.NumberFormat('en-IN', {
            style: 'currency',
            currency: 'INR',
            minimumFractionDigits: 2
        }).format(price);
    };

    return (
        <div className="bg-white dark:bg-neutral-800 p-6 rounded-xl border border-neutral-200 dark:border-neutral-700 shadow-sm">
            <h2 className="text-lg font-bold text-neutral-900 dark:text-white mb-4">
                Basket Summary
            </h2>
            
            <div className="space-y-3">
                <div className="flex justify-between text-neutral-600 dark:text-neutral-300">
                    <span>Subtotal</span>
                    <span>{formatPrice(subTotal)}</span>
                </div>
                
                <div className="flex justify-between text-neutral-600 dark:text-neutral-300">
                    <span>Shipping</span>
                    <span>{formatPrice(shipping)}</span>
                </div>
                
                <div className="pt-3 border-t border-neutral-200 dark:border-neutral-700 flex justify-between font-bold text-lg text-neutral-900 dark:text-white">
                    <span>Total</span>
                    <span>{formatPrice(total)}</span>
                </div>
            </div>
        </div>
    );
}