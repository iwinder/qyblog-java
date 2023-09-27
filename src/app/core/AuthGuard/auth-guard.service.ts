import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { tap, catchError, mergeMap } from 'rxjs/operators';
import { of, Observable } from 'rxjs';

@Injectable()
export class AuthGuard  implements CanActivate {
    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    let url: string = state.url;
    if (this.authService.isLoggedIn) {
      // logged in so return true
      return true;
    }
    this.authService.redirectUrl = url;
    return this.authService.stats().pipe(
      mergeMap(data => {
        console.log("canActivate data", data);
        if ( (data != null && data !== true ) || (data === false) ) {
          this.authService.isLoggedIn = false;
          // this.router.navigate(['/login']);
          return of(false);
        }
        this.authService.isLoggedIn = true;
        return of(true);
      }),
      catchError( err => {
        // alert(err);
        this.router.navigate(['/login']);
        // not logged in so redirect to login page with the return url and return false
        return of(false);
     })
     );

  }
}
