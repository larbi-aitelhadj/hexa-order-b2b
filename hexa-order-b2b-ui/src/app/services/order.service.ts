import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;

  getAll() {
    return this.http.get<any[]>(this.baseUrl);
  }

  create(payload: any) {
    console.log(payload);
    
    return this.http.post<any>(this.baseUrl, payload);
  }

  getById(id: string) {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  confirm(id: string) {
    return this.http.post(`${this.baseUrl}/${id}/confirm`, {});
  }
  
}
