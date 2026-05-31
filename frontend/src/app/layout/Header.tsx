import { useEffect } from "react";
import { Link, NavLink } from "react-router-dom";
import { ShoppingCart, Sun, Moon } from "lucide-react";
import { useAppSelector } from "../store/configureStore";
import { SignedInMenu } from "./SignedInMenu";

const navLinks = [
    { title: 'Home', path: '/' },
    { title: 'Store', path: '/store' },
    { title: 'Contact', path: '/contact' }
];

const accountLinks = [
    { title: 'Login', path: '/login' },
    { title: 'Register', path: '/register' }
];

interface Props {
    darkMode: boolean;
    handleThemeChange: () => void;
}

export function Header({ darkMode, handleThemeChange }: Props) {
    const { basket } = useAppSelector(state => state.basket);
    const { user } = useAppSelector(state => state.account);

    const itemCount = basket?.items?.reduce((sum, item) => sum + item.quantity, 0) || 0;

    return (
        <header className="fixed top-0 left-0 right-0 z-50 bg-white dark:bg-neutral-900 border-b border-neutral-200 dark:border-neutral-800 shadow-sm">
            <nav className="max-w-7xl mx-auto px-4 h-16 flex items-center justify-between">
                
                {/* Logo & Theme Switch */}
                <div className="flex items-center gap-4">
                    <h1 className="text-xl font-bold text-neutral-900 dark:text-white">
                        Sports Center
                    </h1>
                    <button 
                        onClick={handleThemeChange}
                        className="p-2 rounded-full hover:bg-neutral-100 dark:hover:bg-neutral-800 transition-colors"
                    >
                        {darkMode ? <Sun className="w-5 h-5 text-amber-400" /> : <Moon className="w-5 h-5 text-neutral-600" />}
                    </button>
                </div>

                {/* Nav Links */}
                <ul className="flex items-center gap-6">
                    {navLinks.map(({ title, path }) => (
                        <li key={path}>
                            <NavLink 
                                to={path} 
                                className={({ isActive }) => 
                                    `text-sm font-medium transition-colors ${isActive ? 'text-primary-600' : 'text-neutral-600 dark:text-neutral-400 hover:text-neutral-900 dark:hover:text-white'}`
                                }
                            >
                                {title}
                            </NavLink>
                        </li>
                    ))}
                </ul>

                {/* Basket & Account */}
                <div className="flex items-center gap-4">
                    <Link to='/basket' className="relative p-2 hover:bg-neutral-100 dark:hover:bg-neutral-800 rounded-full">
                        <ShoppingCart className="w-6 h-6 text-neutral-700 dark:text-neutral-300" />
                        {itemCount > 0 && (
                            <span className="absolute top-0 right-0 bg-red-500 text-white text-[10px] font-bold w-5 h-5 flex items-center justify-center rounded-full">
                                {itemCount}
                            </span>
                        )}
                    </Link>

                    {user ? (
                        <SignedInMenu />
                    ) : (
                        <ul className="flex items-center gap-4">
                            {accountLinks.map(({ title, path }) => (
                                <li key={path}>
                                    <NavLink 
                                        to={path} 
                                        className="text-sm font-medium text-neutral-600 dark:text-neutral-400 hover:text-neutral-900 dark:hover:text-white transition-colors"
                                    >
                                        {title}
                                    </NavLink>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </nav>
        </header>
    );
}