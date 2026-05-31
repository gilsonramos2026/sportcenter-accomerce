import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Loader2, ShoppingCart, RefreshCw } from "lucide-react";
import { Product } from "../../app/models/product";
import agent from "../../app/api/agent";
import { useAppDispatch, useAppSelector } from "../../app/store/configureStore";

export function ProductDetails() {
    const { basket } = useAppSelector(state => state.basket);
    const dispatch = useAppDispatch();
    const { id } = useParams<{ id: string }>();
    const [product, setProduct] = useState<Product | null>(null);
    const [loading, setLoading] = useState(true);
    const [quantity, setQuantity] = useState(0);
    const [submitting, setSubmitting] = useState(false);

    const item = basket?.items.find(i => i.id === product?.id);

    useEffect(() => {
        if (item) setQuantity(item.quantity);
        if (id) {
            agent.Store.details(parseInt(id))
                .then(setProduct)
                .catch(console.error)
                .finally(() => setLoading(false));
        }
    }, [id, item]);

    const updateQuantity = async () => {
        if (!product) return;
        setSubmitting(true);
        try {
            if (item) {
                const diff = quantity - item.quantity;
                if (diff > 0) await agent.Basket.incrementItemQuantity(item.id, diff, dispatch);
                else if (diff < 0) await agent.Basket.decrementItemQuantity(item.id, Math.abs(diff), dispatch);
            } else {
                await agent.Basket.addItem({ ...product, quantity }, dispatch);
            }
        } finally {
            setSubmitting(false);
        }
    };

    if (loading) return <div className="text-center py-20"><Loader2 className="animate-spin w-10 h-10 mx-auto" /></div>;
    if (!product) return <div className="text-center py-20 text-red-500">Product not found</div>;

    return (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-12 p-6">
            <img src={product.pictureUrl} alt={product.name} className="w-full rounded-2xl shadow-lg" />
            
            <div className="space-y-6">
                <h1 className="text-4xl font-extrabold text-neutral-900 dark:text-white">{product.name}</h1>
                <p className="text-2xl text-primary-600 font-bold">
                    {new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(product.price)}
                </p>
                <p className="text-neutral-600 dark:text-neutral-300 leading-relaxed">{product.description}</p>

                <div className="flex gap-4 items-end">
                    <div className="w-32">
                        <label className="block text-sm font-medium mb-1">Quantity</label>
                        <input 
                            type="number" 
                            className="w-full p-2 border rounded-lg dark:bg-neutral-800"
                            value={quantity}
                            onChange={(e) => setQuantity(parseInt(e.target.value))}
                        />
                    </div>
                    <button 
                        onClick={updateQuantity}
                        disabled={submitting}
                        className="flex-1 bg-primary-600 text-white py-2 rounded-lg font-bold hover:bg-primary-700 flex items-center justify-center gap-2"
                    >
                        {submitting ? <Loader2 className="animate-spin w-5 h-5" /> : (item ? <RefreshCw className="w-5 h-5" /> : <ShoppingCart className="w-5 h-5" />)}
                        {item ? 'Update Quantity' : 'Add to Cart'}
                    </button>
                </div>
            </div>
        </div>
    );
}