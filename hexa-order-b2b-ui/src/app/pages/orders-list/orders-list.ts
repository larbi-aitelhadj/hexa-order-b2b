import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { OrderCreate } from '../order-create/order-create';
import { OrderService } from '../../services/order.service';
import { OrderDetail } from '../order-detail/order-detail';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

@Component({
  selector: 'app-orders-list',
  imports: [
    CommonModule,
    DialogModule,
    ButtonModule,
    ConfirmDialogModule,
    OrderCreate,
    OrderDetail
  ],
  templateUrl: './orders-list.html',
  styleUrl: './orders-list.css'
})
export class OrdersList {

  private service = inject(OrderService);
  private confirmationService= inject(ConfirmationService);
  orders = signal<any[]>([]);
  showCreate = false;

  showView = false;
  selectedOrderId: string | null = null;

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.service.getAll().subscribe(data => {
      this.orders.set(data);
    });
  }

  openCreate() {
    this.showCreate = true;
  }

  closeCreate() {
    this.showCreate = false;
  }

  onCreated() {
    this.showCreate = false;
    this.loadOrders();
  }

  openView(orderId: string) {
    this.selectedOrderId = orderId;
    this.showView = true;
  }

  closeView() {
    this.showView = false;
    this.selectedOrderId = null;
  }

  confirmOrder(orderId: string) {
    this.confirmationService.confirm({
      header: 'Confirmer la commande',
      message: 'Cette action est définitive. Voulez-vous vraiment confirmer cette commande ?',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Confirmer',
      rejectLabel: 'Annuler',

      accept: () => {
        this.updateOrderStatus(orderId);
      }
    });
  }

  updateOrderStatus(orderId: string) {
    this.service.confirm(orderId).subscribe(() => {
      this.loadOrders();
    });    
  }
  
}