import { Link } from "react-router-dom";
import { Trash2, Plus, Minus } from "lucide-react";
import { useAppDispatch, useAppSelector } from "../../app/store/configureStore";
import agent from "../../app/api/agent";
import { BasketSummary } from "./BasketSummary";

export function BasketPage() {
    const { basket } = useAppSelector(state => state.basket);
    const dispatch = useAppDispatch();
    const { Basket: BasketActions } = agent;

    const removeItem = (productId: number) => BasketActions.removeItem(productId, dispatch);
    const decrementItem = (productId: number) => BasketActions.decrementItemQuantity(productId, 1, dispatch);
    const incrementItem = (productId: number) => BasketActions.incrementItemQuantity(productId, 1, dispatch);

    const formatPrice = (price: number) => 
        new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(price);

    if (!basket || basket.items.length === 0) {
        return <div className="text-center py-20 text-xl text-neutral-500">Your basket is empty. Please add some items!</div>;
    }

    return (
        <div className="max-w-5xl mx-auto py-8">
            <div className="bg-white dark:bg-neutral-800 shadow-sm rounded-lg overflow-hidden border border-neutral-200 dark:border-neutral-700">
                <table className="w-full text-left">
                    <thead className="bg-neutral-50 dark:bg-neutral-900 border-b border-neutral-200 dark:border-neutral-700">
                        <tr>
                            <th className="p-4 font-semibold text-neutral-700 dark:text-neutral-300">Product</th>
                            <th className="p-4 font-semibold text-neutral-700 dark:text-neutral-300">Price</th>
                            <th className="p-4 font-semibold text-neutral-700 dark:text-neutral-300">Quantity</th>
                            <th className="p-4 font-semibold text-neutral-700 dark:text-neutral-300">Subtotal</th>
                            <th className="p-4 font-semibold text-neutral-700 dark:text-neutral-300">Action</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-neutral-200 dark:divide-neutral-700">
                        {basket.items.map((item) => (
                            <tr key={item.id}>
                                <td className="p-4 flex items-center gap-4">
                                    <img src={item.pictureUrl} alt={item.name} className="w-16 h-16 object-cover rounded" />
                                    <span className="font-medium text-neutral-900 dark:text-white">{item.name}</span>
                                </td>
                                <td className="p-4 text-neutral-600 dark:text-neutral-300">{formatPrice(item.price)}</td>
                                <td className="p-4">
                                    <div className="flex items-center gap-2">
                                        <button onClick={() => decrementItem(item.id)} className="p-1 rounded bg-neutral-100 hover:bg-neutral-200 dark:bg-neutral-700">
                                            <Minus className="w-4 h-4" />
                                        </button>
                                        <span className="w-8 text-center font-bold">{item.quantity}</span>
                                        <button onClick={() => incrementItem(item.id)} className="p-1 rounded bg-neutral-100 hover:bg-neutral-200 dark:bg-neutral-700">
                                            <Plus className="w-4 h-4" />
                                        </button>
                                    </div>
                                </td>
                                <td className="p-4 font-semibold">{formatPrice(item.price * item.quantity)}</td>
                                <td className="p-4">
                                    <button onClick={() => removeItem(item.id)} className="text-red-500 hover:text-red-700">
                                        <Trash2 className="w-5 h-5" />
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <div className="mt-8 max-w-sm ml-auto">
                <BasketSummary />
                <Link
                    to="/checkout"
                    className="mt-4 block w-full text-center bg-primary-600 hover:bg-primary-700 text-white font-bold py-3 rounded-lg transition-colors"
                >
                    Checkout
                </Link>
            </div>
        </div>
    );
}