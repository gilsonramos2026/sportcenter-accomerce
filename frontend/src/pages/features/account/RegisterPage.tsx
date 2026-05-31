import { useState } from 'react';
import { Link } from 'react-router-dom';
import { UserPlus, Lock, Mail, User } from 'lucide-react';

export function RegisterPage() {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: ''
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log(formData);
    };

    return (
        <div className="min-h-[80vh] flex items-center justify-center p-4">
            <div className="w-full max-w-md bg-white dark:bg-neutral-800 p-8 rounded-2xl shadow-xl border border-neutral-100 dark:border-neutral-700">
                <div className="flex flex-col items-center mb-8">
                    <div className="bg-primary-100 dark:bg-primary-900/30 p-3 rounded-full mb-4">
                        <UserPlus className="w-8 h-8 text-primary-600" />
                    </div>
                    <h1 className="text-2xl font-bold text-neutral-900 dark:text-white">Register</h1>
                </div>

                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label className="block text-sm font-medium text-neutral-700 dark:text-neutral-300 mb-1">Username</label>
                        <div className="relative">
                            <User className="absolute left-3 top-3 w-5 h-5 text-neutral-400" />
                            <input
                                name="username"
                                type="text"
                                required
                                className="w-full pl-10 pr-4 py-2 border border-neutral-300 dark:border-neutral-600 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none transition-all dark:bg-neutral-900 dark:text-white"
                                value={formData.username}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-neutral-700 dark:text-neutral-300 mb-1">Email</label>
                        <div className="relative">
                            <Mail className="absolute left-3 top-3 w-5 h-5 text-neutral-400" />
                            <input
                                name="email"
                                type="email"
                                required
                                className="w-full pl-10 pr-4 py-2 border border-neutral-300 dark:border-neutral-600 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none transition-all dark:bg-neutral-900 dark:text-white"
                                value={formData.email}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-neutral-700 dark:text-neutral-300 mb-1">Password</label>
                        <div className="relative">
                            <Lock className="absolute left-3 top-3 w-5 h-5 text-neutral-400" />
                            <input
                                name="password"
                                type="password"
                                required
                                className="w-full pl-10 pr-4 py-2 border border-neutral-300 dark:border-neutral-600 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none transition-all dark:bg-neutral-900 dark:text-white"
                                value={formData.password}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-primary-600 hover:bg-primary-700 text-white font-bold py-3 rounded-lg transition-colors mt-6"
                    >
                        Register
                    </button>
                </form>

                <div className="mt-6 text-center text-sm">
                    <span className="text-neutral-600 dark:text-neutral-400">Already have an account? </span>
                    <Link to="/login" className="text-primary-600 font-semibold hover:underline">
                        Sign in
                    </Link>
                </div>
            </div>
        </div>
    );
}