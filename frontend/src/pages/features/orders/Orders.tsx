import { useEffect, useState } from "react";
import agent from "../../app/api/agent";
import { Order } from "../../app/models/order";
import { Loader2 } from "lucide-react";

export function Orders() {
    const [orders, setOrders] = useState<Order[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        agent.Orders.list()
            .then(setOrders)
            .catch(console.error)
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <div className="flex justify-center p-20"><Loader2 className="animate-spin w-10 h-10" /></div>;

    function formatDate(dateArray: any) {
        if (!Array.isArray(dateArray) || dateArray.length < 3) return "N/A";
        return `${String(dateArray[2]).padStart(2, '0')}/${String(dateArray[1]).padStart(2, '0')}/${dateArray[0]}`;
    }

    return (
        <div className="p-6 max-w-5xl mx-auto">
            <h1 className="text-2xl font-bold mb-6">My Orders</h1>
            
            <div className="bg-white dark:bg-neutral-800 rounded-xl shadow border border-neutral-100 dark:border-neutral-700 overflow-hidden">
                <div className="grid grid-cols-4 bg-neutral-50 dark:bg-neutral-900 p-4 font-bold text-neutral-600 dark:text-neutral-300 border-b">
                    <div>Order ID</div>
                    <div className="text-right">Total</div>
                    <div className="text-right">Date</div>
                    <div className="text-right">Status</div>
                </div>
                
                {orders.map((order) => (
                    <div key={order.id} className="grid grid-cols-4 p-4 border-b last:border-0 hover:bg-neutral-50 dark:hover:bg-neutral-900 transition">
                        <div className="font-medium text-primary-600">#{order.id}</div>
                        <div className="text-right font-semibold">
                            {new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(order.total)}
                        </div>
                        <div className="text-right text-neutral-500">{formatDate(order.orderDate)}</div>
                        <div className="text-right">
                            <span className={`px-2 py-1 rounded-full text-xs font-bold ${
                                order.orderStatus === 'Pending' ? 'bg-yellow-100 text-yellow-800' : 'bg-green-100 text-green-800'
                            }`}>
                                {order.orderStatus}
                            </span>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}