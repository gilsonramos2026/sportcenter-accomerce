import { createAsyncThunk, createSlice, isAnyOf } from "@reduxjs/toolkit";
import { User } from "../../app/models/user";
import { FieldValues } from "react-hook-form";
import agent from "../../app/api/agent";
import { router } from "../../app/router/Routes";
import { toast } from "react-toastify";

interface AccountState {
    user: User | null;
    error: string | null;
}

const initialState: AccountState = {
    user: null,
    error: null
};

export const signInUser = createAsyncThunk<User, FieldValues, { rejectValue: string }>(
    'auth/login',
    async (data, thunkAPI) => {
        try {
            const user = await agent.Account.login(data);
            localStorage.setItem('user', JSON.stringify(user));
            return user;
        } catch (error: any) {
            return thunkAPI.rejectWithValue(error.data || 'Login failed');
        }
    }
);

export const fetchCurrentUser = createAsyncThunk<User | null>(
    'auth/fetchCurrentUser',
    async (_, thunkAPI) => {
        try {
            const userString = localStorage.getItem('user');
            if (userString) {
                return JSON.parse(userString) as User;
            }
            return null;
        } catch (error) {
            return thunkAPI.rejectWithValue('Could not fetch user');
        }
    }
);

export const accountSlice = createSlice({
    name: 'account',
    initialState,
    reducers: {
        logOut: (state) => {
            state.user = null;
            state.error = null;
            localStorage.removeItem('user');
            router.navigate('/');
        },
        clearError: (state) => {
            state.error = null;
        }
    },
    extraReducers: (builder) => {
        builder.addMatcher(isAnyOf(signInUser.fulfilled, fetchCurrentUser.fulfilled), (state, action) => {
            state.user = action.payload;
            state.error = null;
            if (action.type === signInUser.fulfilled.type) {
                toast.success('Login realizado com sucesso!');
            }
        });
        builder.addMatcher(isAnyOf(signInUser.rejected, fetchCurrentUser.rejected), (state, action) => {
            state.error = action.payload as string;
            toast.error('Erro ao autenticar. Verifique suas credenciais.');
        });
    }
});

export const { logOut, clearError } = accountSlice.actions;