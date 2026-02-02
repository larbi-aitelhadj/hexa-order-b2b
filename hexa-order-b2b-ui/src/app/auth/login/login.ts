import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
  <div class="container mt-5" style="max-width: 400px;">
    <h3 class="mb-4 text-center">Login</h3>

    <div class="mb-3">
      <input class="form-control"
        placeholder="Username"
        [(ngModel)]="username">
    </div>

    <div class="mb-3">
      <input type="password"
        class="form-control"
        placeholder="Password"
        [(ngModel)]="password">
    </div>

    <div *ngIf="error()" class="alert alert-danger">
      {{ error() }}
    </div>

    <button class="btn btn-dark w-100" (click)="login()">
      Login
    </button>
  </div>
  `
})
export class Login {

  username = '';
  password = '';
  error = signal<string | null>(null);

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  login() {
    this.error.set(null);

    this.auth.login(this.username, this.password).subscribe({
      next: () => this.router.navigate(['/orders']),
      error: () => this.error.set('Invalid username or password')
    });
  }
}
