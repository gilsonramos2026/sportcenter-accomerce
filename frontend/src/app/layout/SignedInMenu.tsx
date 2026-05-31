import { useState } from 'react';
import { Link } from 'react-router-dom';
import { LogOut, Package, User, ChevronDown } from 'lucide-react';
import { useAppDispatch, useAppSelector } from '../store/configureStore';
import { logOut } from '../../features/account/accountSlice';

export function SignedInMenu() {
    const dispatch = useAppDispatch();
    const { user } = useAppSelector(state => state.account);
    const [isOpen, setIsOpen] = useState(false);

    return (
        <div className="relative">
            {/* Trigger Button */}
            <button
                onClick={() => setIsOpen(!isOpen)}
                className="flex items-center gap-1 text-sm font-medium text-neutral-700 dark:text-neutral-300 hover:text-neutral-900 dark:hover:text-white transition-colors"
            >
                Hi, {user?.username}
                <ChevronDown className="w-4 h-4" />
            </button>

            {/* Dropdown Menu */}
            {isOpen && (
                <>
                    {/* Overlay para fechar ao clicar fora */}
                    <div className="fixed inset-0 z-40" onClick={() => setIsOpen(false)} />
                    
                    <div className="absolute right-0 top-full mt-2 w-48 bg-white dark:bg-neutral-800 rounded-xl shadow-lg border border-neutral-100 dark:border-neutral-700 z-50 overflow-hidden">
                        <button onClick={() => setIsOpen(false)} className="w-full text-left px-4 py-2 text-sm text-neutral-700 dark:text-neutral-300 hover:bg-neutral-50 dark:hover:bg-neutral-700 flex items-center gap-2">
                            <User className="w-4 h-4" /> Profile
                        </button>
                        <Link 
                            to="/orders" 
                            onClick={() => setIsOpen(false)}
                            className="block px-4 py-2 text-sm text-neutral-700 dark:text-neutral-300 hover:bg-neutral-50 dark:hover:bg-neutral-700 flex items-center gap-2"
                        >
                            <Package className="w-4 h-4" /> My Orders
                        </Link>
                        <button 
                            onClick={() => { dispatch(logOut()); setIsOpen(false); }}
                            className="w-full text-left px-4 py-2 text-sm text-red-600 dark:text-red-400 hover:bg-red-50 dark:hover:bg-red-950/30 flex items-center gap-2"
                        >
                            <LogOut className="w-4 h-4" /> Logout
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}