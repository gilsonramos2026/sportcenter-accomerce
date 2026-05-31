import { useAppSelector } from "../../app/store/configureStore";
import { BasketSummary } from "../basket/BasketSummary";

export function Review() {
    const { basket } = useAppSelector(state => state.basket);

    const formatPrice = (price: number) => 
        new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(price);

    return (
        <div className="space-y-6">
            <h2 className="text-xl font-bold">Order summary</h2>
            <div className="border rounded-lg overflow-hidden">
                {basket?.items.map((item) => (
                    <div key={item.id} className="flex items-center p-4 border-b last:border-0 hover:bg-neutral-50 dark:hover:bg-neutral-900">
                        <img src={item.pictureUrl} alt={item.name} className="w-12 h-12 object-cover rounded mr-4" />
                        <div className="flex-1">
                            <h4 className="font-medium">{item.name}</h4>
                            <p className="text-sm text-neutral-500">Qty: {item.quantity}</p>
                        </div>
                        <span className="font-semibold">{formatPrice(item.price * item.quantity)}</span>
                    </div>
                ))}
            </div>
            <BasketSummary />
        </div>
    );
}