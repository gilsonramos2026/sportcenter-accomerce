import { useNavigate } from "react-router-dom";
import { AlertCircle } from "lucide-react";

export function NotFound() {
    const navigate = useNavigate();

    return (
        <div className="flex flex-col items-center justify-center min-h-[60vh] p-8 text-center">
            <img 
                src="/images/page-not-found.png" 
                alt="404 Not Found" 
                className="w-full max-w-sm mb-8"
            />
            <h1 className="text-4xl font-extrabold text-neutral-900 dark:text-neutral-100 mb-2">
                Oops! Página não encontrada.
            </h1>
            <p className="text-neutral-500 dark:text-neutral-400 mb-8 max-w-md">
                Não conseguimos encontrar a página que você está procurando.
            </p>
            <button 
                onClick={() => navigate('/')}
                className="bg-neutral-900 dark:bg-neutral-100 text-white dark:text-neutral-950 px-8 py-3 rounded-xl font-semibold hover:opacity-90 transition-opacity"
            >
                Voltar para o Início
            </button>
        </div>
    );
}