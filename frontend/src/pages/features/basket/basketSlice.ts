import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Basket } from "../../app/models/basket";

interface BasketState {
    basket: Basket | null;
    status: 'idle' | 'pending';
}

const initialState: BasketState = {
    basket: null,
    status: 'idle'
};

export const basketSlice = createSlice({
    name: 'basket',
    initialState,
    reducers: {
        setBasket: (state, action: PayloadAction<Basket>) => {
            state.basket = action.payload;
        },
        removeItem: (state, action: PayloadAction<number>) => {
            if (!state.basket) return;
            state.basket.items = state.basket.items.filter(item => item.id !== action.payload);
        },
        setStatus: (state, action: PayloadAction<'idle' | 'pending'>) => {
            state.status = action.payload;
        }
    }
});

export const { setBasket, removeItem, setStatus } = basketSlice.actions;