import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { Provider } from 'react-redux';

// Estilos globais (que já carregam o Tailwind v4, DaisyUI v5 e a fonte Roboto)
import './index.css';
import 'daisyui/dist/full.css';

// Configurações de rotas e estado global do Redux
import { router } from './app/router/Routes.tsx';
import { store } from './app/store/configureStore.ts';

// Inicialização limpa da raiz utilizando a API do React 19
createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      <RouterProvider router={router} />
    </Provider>
  </StrictMode>
);