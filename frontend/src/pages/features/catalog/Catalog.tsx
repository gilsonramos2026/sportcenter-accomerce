import { useState, useEffect } from "react";
import { Product } from "../../app/models/product";
import { ProductList } from "./ProductList";
import agent from "../../app/api/agent";
import { Spinner } from "../../app/layout/Spinner";
import { Brand } from "../../app/models/brand";
import { Type } from "../../app/models/type";
import { Search } from "lucide-react";

export function Catalog() {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const [brands, setBrands] = useState<Brand[]>([]);
    const [types, setTypes] = useState<Type[]>([]);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        Promise.all([
            agent.Store.list(),
            agent.Store.brands(),
            agent.Store.types()
        ]).then(([p, b, t]) => {
            setProducts(p.content);
            setBrands(b);
            setTypes(t);
        }).finally(() => setLoading(false));
    }, []);

    if (loading) return <Spinner />;

    return (
        <div className="flex gap-8 p-6">
            {/* Sidebar Filtros */}
            <aside className="w-64 space-y-6">
                <div className="relative">
                    <Search className="absolute left-3 top-3 w-5 h-5 text-neutral-400" />
                    <input
                        placeholder="Search products..."
                        className="w-full pl-10 pr-4 py-2 border rounded-lg dark:bg-neutral-800"
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
                {/* Aqui você mapearia seus RadioGroups usando Tailwind */}
            </aside>

            {/* Grid de Produtos */}
            <main className="flex-1">
                <ProductList products={products} />
            </main>
        </div>
    );
}