import { Routes } from '@angular/router';
import { OrderCreate } from './pages/order-create/order-create';
import { OrderDetail } from './pages/order-detail/order-detail';
import { OrdersList } from './pages/orders-list/orders-list';
import { Login } from './auth/login/login';
import { authGuard } from './auth/auth.guard';

export const routes: Routes = [
    { path: '', component: OrdersList, canActivate: [authGuard] },
    { path: 'orders', component: OrdersList, canActivate: [authGuard] },
    { path: 'orders/new', component: OrderCreate, canActivate: [authGuard] },
    { path: 'orders/:id', component: OrderDetail, canActivate: [authGuard] },
    { path: 'login', component: Login }
];
