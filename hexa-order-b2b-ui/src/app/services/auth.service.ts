import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly USER_KEY = 'auth_user';
  user = signal<any | null>(this.getStoredUser());

  constructor(
    private http: HttpClient, 
    private router: Router
  ) {}

  login(username: string, password: string) {    
    return this.http.get<any[]>('/assets/users.json').pipe(
      map(users => {
        const found = users.find(
          u => u.username === username && u.password === password
        );        

        if (!found) {
          throw new Error('Invalid credentials');
        }

        localStorage.setItem(this.USER_KEY, JSON.stringify(found));
        this.user.set(found);
        return found;
      })
    );
  }

  logout() {
    this.user.set(null);
    localStorage.removeItem(this.USER_KEY);
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return this.user() !== null;
  }

  private getStoredUser() {
    const raw = localStorage.getItem(this.USER_KEY);
    return raw ? JSON.parse(raw) : null;
  }

}
