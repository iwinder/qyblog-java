import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,  throwError } from 'rxjs';
import { HttpUtils } from './http-utils';
import { catchError, tap,  map } from 'rxjs/operators';
import { Response } from '@angular/http';

@Injectable()
export class AuthService {

  isLoggedIn: boolean = false;
  // store the URL so we can redirect after logging in
  redirectUrl: string;
  userToken: {username: string, token: string};

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<any> {
    let url = '/api/login';
    let data = new FormData();
    data.append('username', username);
    data.append('password', password);

    return this.http.post<any>(url, data).pipe(
      tap(response => {
        // console.log("response", response);
        if (response && response.token != null) {
          this.isLoggedIn = response['result']['isLoggedIn'];
          // login successful, store username and jwt token in local storage to keep user logged in between page refreshes
          // tslint:disable-next-line:max-line-length
          // localStorage.setItem('currentUser', JSON.stringify({username: username, token: response.token}));
          // this.userToken = JSON.parse(localStorage.getItem('currentUser'));
          this.userToken = {username: username, token: response.token};
          return this.isLoggedIn;
        } else {
          this.isLoggedIn = false;
           throwError((JSON.stringify(response)));
          // return  of(false);
        }
      }),
      catchError(HttpUtils.handleError)
    );
  }

  // getCurrentUser(): any {
  //   const userStr = localStorage.getItem('currentUser');
  //   return userStr ? JSON.parse(userStr) : '';
  // }

  // getToken(): string {
  //   const currentUser = this.getCurrentUser();
  //   return currentUser ? currentUser.token : '';
  // }

  // getUsername(): string {
  //   const currentUser = this.getCurrentUser();
  //   return currentUser ? currentUser.username : '';
  // }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  stats(): Observable<boolean> {
    let url = '/api/status';
    return this.http.get<boolean>(url).pipe(
        map(resp => {
          this.isLoggedIn = true;
          // console.log("stats data", resp);
          if (!this.userToken) {
              this.loginfo().subscribe();
          }
          return true;
      }),
        catchError(resp => {
          this.isLoggedIn = false;
         return HttpUtils.handleError(resp);
      })
    );
  }

  statsOfLogIng(): Observable<boolean> {
    let url = '/api/status';
    return this.http.get<boolean>(url);
      // return this.http.options(url)
    //   return this.http.get<boolean>(url).pipe(
    //     map(
    //       resp => {
    //         this.isLoggedIn = true;
    //         // console.log("stats data", resp);
    //         if (!this.userToken) {
    //             this.loginfo().subscribe();
    //         }
    //         return true;
    //       },
    //       err => {
    //         this.isLoggedIn = false;
    //       }
    //   )
    // );
  }


  loginfo(): Observable<any> {
    let url = '/api/loginfo';
    return this.http.get(url).pipe(
      tap( (resp: Response) => {
        // console.log("loginfo data", resp);
            if (resp && resp['token'] ) {
              this.isLoggedIn = resp['result']['isLoggedIn'];
              // login successful, store username and jwt token in local storage to keep user logged in between page refreshes
              // tslint:disable-next-line:max-line-length
              localStorage.setItem('currentUser', JSON.stringify({username: resp['result']['username'], token: resp['token']}));
            }
            // let ut =resp;
            // console.log("ut", ut);
            // this.userToken =  this.userToken = JSON.parse(localStorage.getItem('currentUser'));
            this.userToken = {username: resp['result']['username'], token: resp['token']};
            return this.userToken;
        }),
        catchError(HttpUtils.handleError)
      );
}

  getCurrentUser(): Observable<any>  {
    return  this.http.get('/api/currentUser');
  }
}
