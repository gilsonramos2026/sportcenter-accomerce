import { useForm, FieldValues } from "react-hook-form";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { LogIn, Lock, User, Loader2 } from 'lucide-react';
import { toast } from "react-toastify";
import { useAppDispatch } from "../../app/store/configureStore";
import { signInUser } from "./accountSlice";

export function SignInPage() {
    const navigate = useNavigate();
    const location = useLocation();
    const dispatch = useAppDispatch();
    
    const { register, handleSubmit, formState: { isSubmitting, errors, isValid } } = useForm({
        mode: 'onTouched'
    });

    async function submitForm(data: FieldValues) {
        try {
            // dispatching the sign in action
            const result = await dispatch(signInUser(data));
            
            // Verifica sucesso através da action do Redux
            if (result.meta.requestStatus === 'fulfilled') {
                navigate(location.state?.from || '/store');
            } else {
                toast.error('Sign in Failed. Please check your credentials.');
            }
        } catch (error) {
            console.error('Error signing in:', error);
            toast.error('Sign in Failed. Please try again');
        }
    }

    return (
        <div className="min-h-[80vh] flex items-center justify-center p-4">
            <div className="w-full max-w-md bg-white dark:bg-neutral-800 p-8 rounded-2xl shadow-xl border border-neutral-100 dark:border-neutral-700">
                <div className="flex flex-col items-center mb-8">
                    <div className="bg-primary-100 dark:bg-primary-900/30 p-3 rounded-full mb-4">
                        <LogIn className="w-8 h-8 text-primary-600" />
                    </div>
                    <h1 className="text-2xl font-bold text-neutral-900 dark:text-white">Sign in</h1>
                </div>

                <form onSubmit={handleSubmit(submitForm)} className="space-y-4">
                    <div>
                        <label className="block text-sm font-medium text-neutral-700 dark:text-neutral-300 mb-1">Username</label>
                        <div className="relative">
                            <User className="absolute left-3 top-3 w-5 h-5 text-neutral-400" />
                            <input
                                {...register('username', { required: 'Username is required' })}
                                className={`w-full pl-10 pr-4 py-2 border rounded-lg outline-none transition-all dark:bg-neutral-900 dark:text-white ${errors.username ? 'border-red-500' : 'border-neutral-300 dark:border-neutral-600 focus:ring-2 focus:ring-primary-500'}`}
                            />
                        </div>
                        {errors.username && <p className="text-red-500 text-xs mt-1">{errors.username.message as string}</p>}
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-neutral-700 dark:text-neutral-300 mb-1">Password</label>
                        <div className="relative">
                            <Lock className="absolute left-3 top-3 w-5 h-5 text-neutral-400" />
                            <input
                                type="password"
                                {...register('password', { required: 'Password is required' })}
                                className={`w-full pl-10 pr-4 py-2 border rounded-lg outline-none transition-all dark:bg-neutral-900 dark:text-white ${errors.password ? 'border-red-500' : 'border-neutral-300 dark:border-neutral-600 focus:ring-2 focus:ring-primary-500'}`}
                            />
                        </div>
                        {errors.password && <p className="text-red-500 text-xs mt-1">{errors.password.message as string}</p>}
                    </div>

                    <button
                        type="submit"
                        disabled={!isValid || isSubmitting}
                        className="w-full bg-primary-600 hover:bg-primary-700 disabled:opacity-50 text-white font-bold py-3 rounded-lg transition-colors mt-6 flex items-center justify-center gap-2"
                    >
                        {isSubmitting ? <Loader2 className="w-5 h-5 animate-spin" /> : 'Sign In'}
                    </button>

                    <div className="flex justify-between text-sm mt-4">
                        <Link to="#" className="text-neutral-600 dark:text-neutral-400 hover:underline">Forgot password?</Link>
                        <Link to="/register" className="text-primary-600 font-semibold hover:underline">Don't have an account? Sign Up</Link>
                    </div>
                </form>
            </div>
        </div>
    );
}