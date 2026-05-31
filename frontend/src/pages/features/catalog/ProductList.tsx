import { Product } from "../../app/models/product";
import { ProductCard } from "./ProductCard";

interface Props { products: Product[]; }

export function ProductList({ products }: Props) {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      {products.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
}