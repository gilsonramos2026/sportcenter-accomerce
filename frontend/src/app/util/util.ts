import { Basket } from "../models/basket";

/**
 * Recupera e valida o objeto Basket do localStorage.
 * Retorna null se o item não existir, estiver corrompido ou for inválido.
 */
export function getBasketFromLocalStorage(): Basket | null {
    const storedBasket = localStorage.getItem('basket');
    
    if (!storedBasket) return null;

    try {
        const parsedBasket: Basket = JSON.parse(storedBasket);
        
        // Validação básica para garantir que o objeto possui a estrutura esperada
        if (parsedBasket && Array.isArray(parsedBasket.items)) {
            return parsedBasket;
        }
        return null;
    } catch (error) {
        console.error('Error parsing basket from local storage: ', error);
        return null;
    }
}