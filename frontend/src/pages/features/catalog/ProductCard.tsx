import { useState } from "react";
import { Product } from "../../app/models/product";
import { Link } from "react-router-dom";
import { ShoppingCart, Loader2 } from "lucide-react";
import agent from "../../app/api/agent";
import { useAppDispatch } from "../../app/store/configureStore";
import { setBasket } from "../basket/basketSlice";

interface Props { product: Product; }

export function ProductCard({ product }: Props) {
    const [loading, setLoading] = useState(false);
    const dispatch = useAppDispatch();

    function addItem() {
        setLoading(true);
        agent.Basket.addItem(product, dispatch)
            .then(response => dispatch(setBasket(response.basket)))
            .finally(() => setLoading(false));
    }

    return (
        <div className="bg-white dark:bg-neutral-800 rounded-xl shadow-md overflow-hidden border border-neutral-100 hover:shadow-xl transition-shadow">
            <img src={product.pictureUrl} alt={product.name} className="h-48 w-full object-contain p-4" />
            <div className="p-4">
                <h3 className="font-bold text-lg text-neutral-900 dark:text-white truncate">{product.name}</h3>
                <p className="text-primary-600 font-bold text-xl my-2">
                    {new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(product.price)}
                </p>
                <div className="flex gap-2 mt-4">
                    <button
                        onClick={addItem}
                        disabled={loading}
                        className="flex-1 bg-primary-600 hover:bg-primary-700 text-white py-2 rounded-lg flex items-center justify-center gap-2 disabled:opacity-50"
                    >
                        {loading ? <Loader2 className="animate-spin w-4 h-4" /> : <ShoppingCart className="w-4 h-4" />}
                        Add
                    </button>
                    <Link to={`/store/${product.id}`} className="px-4 py-2 border border-neutral-200 rounded-lg hover:bg-neutral-50 dark:hover:bg-neutral-700">
                        View
                    </Link>
                </div>
            </div>
        </div>
    );
}