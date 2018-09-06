import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { HttpUtils } from './http-utils';
import { catchError, retry, tap } from 'rxjs/operators';

@Injectable()
export class AuthService {

  isLoggedIn: boolean = false;
  // store the URL so we can redirect after logging in
  redirectUrl: string;


  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<any> {
    let url = '/api/login';
    let data = new FormData();
    data.append('username', username);
    data.append('password', password);

    return this.http.post<any>(url, data).pipe(
      tap(response => {
        console.log("response", response);
        if (response && response.token) {
          this.isLoggedIn = response['result']['isLoggedIn'];
          // login successful, store username and jwt token in local storage to keep user logged in between page refreshes
          // tslint:disable-next-line:max-line-length
          localStorage.setItem('currentUser', JSON.stringify({username: username, token: response.token}));
        }
        return this.isLoggedIn;
      }),
      catchError(HttpUtils.handleError)
    );
  }

  getCurrentUser(): any {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : '';
  }

  getToken(): string {
    const currentUser = this.getCurrentUser();
    return currentUser ? currentUser.token : '';
  }

  getUsername(): string {
    const currentUser = this.getCurrentUser();
    return currentUser ? currentUser.username : '';
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  isLogIn(): boolean {
    const token: String = this.getToken();
    return token && token.length > 0;
  }

  // hasRole(role: string): boolean {
  //   const currentUser = this.getCurrentUser();
  //   if (!currentUser) {
  //   return false;
  //   }
  //   const authorities: string[] = currentUser.authorities;
  //   return authorities.indexOf('ROLE_' + role) != -1;
  // }
}
