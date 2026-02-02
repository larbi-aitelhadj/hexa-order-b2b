import { Component, EventEmitter, inject, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../services/order.service';

@Component({
  standalone: true,
  selector: 'app-order-create',
  imports: [CommonModule],
  templateUrl: './order-create.html'
})
export class OrderCreate {
  private service = inject(OrderService);
  @Output() created = new EventEmitter<void>();

  clientId = signal('');
  productId = signal('');
  quantity = signal(1);
  unitPrice = signal(0);
  loading = signal(false);
  error = signal<string | null>(null);

  isValid() {
    return !!this.clientId() && !!this.productId() && this.quantity() > 0;
  }

  create() {
    this.loading.set(true);
    this.error.set(null);
    const payload = {
      clientId: this.clientId(),
      items: [
        {
          productId: this.productId(),
          quantity: this.quantity(),
          unitPrice: this.unitPrice()
        }
      ]
    };

    this.service.create(payload).subscribe({
      next: order => {
        this.loading.set(false);
        this.loading.set(false);
        this.created.emit();
      },
      error: err => {
        this.loading.set(false);
        this.error.set(err.error?.message ?? 'Order creation failed');
      }
    });
  }
}
