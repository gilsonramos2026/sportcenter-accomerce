import { Link } from "react-router-dom";

export function HomePage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-[60vh] text-center p-6">
      <h1 className="text-5xl md:text-6xl font-extrabold text-neutral-900 dark:text-white mb-6">
        Welcome to <span className="text-primary-600">Our Store</span>
      </h1>
      <p className="text-xl text-neutral-600 dark:text-neutral-300 max-w-2xl mb-10">
        Discover high-quality products at the best prices. Explore our latest collection 
        and find exactly what you need with ease.
      </p>
      
      <div className="flex gap-4">
        <Link 
          to="/store" 
          className="px-8 py-3 bg-primary-600 text-white font-bold rounded-xl hover:bg-primary-700 transition shadow-lg"
        >
          Shop Now
        </Link>
        <Link 
          to="/contact" 
          className="px-8 py-3 bg-white dark:bg-neutral-800 border border-neutral-200 dark:border-neutral-700 font-bold rounded-xl hover:bg-neutral-50 dark:hover:bg-neutral-700 transition"
        >
          Contact Us
        </Link>
      </div>
    </div>
  );
}