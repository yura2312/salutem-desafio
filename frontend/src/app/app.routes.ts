import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'bebidas',
    loadComponent: () => import('./bebida/bebida').then((modulo) => modulo.Bebida),
  },
  {
    path: 'ingredientes',
    loadComponent: () => import('./ingrediente/ingrediente').then((modulo) => modulo.Ingrediente),
  },
  {
    path: 'hamburgueres',
    loadComponent: () => import('./hamburguer/hamburguer').then((modulo) => modulo.Hamburguer),
  },
  {
    path: 'pedidos',
    loadComponent: () => import('./pedido/pedido').then((modulo) => modulo.Pedido),
  },
  { path: '', pathMatch: 'full', redirectTo: 'bebidas' },
  { path: '**', redirectTo: 'bebidas' },
];
