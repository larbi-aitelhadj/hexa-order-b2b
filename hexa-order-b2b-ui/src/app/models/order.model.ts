export interface OrderItem {
  productId: string;
  quantity: number;
  unitPrice: number;
}

export interface Order {
  id: string;
  clientId: string;
  status: 'CREATED' | 'CONFIRMED' | 'PAID' | 'SHIPPED' | 'CANCELLED';
  total: number;
  items: OrderItem[];
}
