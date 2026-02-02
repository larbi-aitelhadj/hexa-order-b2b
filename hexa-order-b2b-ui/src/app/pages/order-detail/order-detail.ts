import { Component, EventEmitter, inject, Input, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-detail',
  imports: [CommonModule],
  templateUrl: './order-detail.html',
  styleUrl: './order-detail.css'
})
export class OrderDetail {

  @Input() orderId!: string | null;
  @Output() close = new EventEmitter<void>();
  
  private service = inject(OrderService);

  order = signal<any | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit() {
    if (this.orderId) {
      this.loadOrder(this.orderId);
    }
  }

  loadOrder(id: string) {
    this.loading.set(true);
    this.service.getById(id).subscribe({
      next: (data) => {
        this.order.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Unable to load order');
        this.loading.set(false);
      }
    });
  }
}
