import { useNavigate } from "react-router-dom";
import { ServerCrash } from "lucide-react";

export function ServerError() {
    const navigate = useNavigate();

    return (
        <div className="flex flex-col items-center justify-center min-h-[60vh] p-8 text-center">
            <img 
                src="/images/server-error.png" 
                alt="500 Server Error" 
                className="w-full max-w-sm mb-8"
            />
            <h1 className="text-4xl font-extrabold text-neutral-900 dark:text-neutral-100 mb-2">
                Ops! Algo deu errado.
            </h1>
            <p className="text-neutral-500 dark:text-neutral-400 mb-8 max-w-md">
                O servidor encontrou um erro interno e não conseguiu concluir sua solicitação.
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