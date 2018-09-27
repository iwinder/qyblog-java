import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { tap, catchError, map, mergeMap } from 'rxjs/operators';
import { of, Observable } from 'rxjs';

@Injectable()
export class AuthGuard  implements CanActivate {
    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> |boolean {
    console.log("canActivate.stats data", this.authService.isLoggedIn);
    if (this.authService.isLoggedIn) {
      // logged in so return true
      return true;
    }
    return this.authService.stats().pipe(
      tap(data => {
        console.log("authService.stats data", data);
        if (data instanceof Object) {
          alert(data.msg);
          this.router.navigate(['/login']);
          return of(false);
        }
        this.authService.isLoggedIn = data ? data : true ;
        console.log("authService.stats this.authService.isLoggedIn", this.authService.isLoggedIn);
        return of(true);
      }),
      catchError( err => {
        alert(err);
        this.router.navigate(['/login']);
        // not logged in so redirect to login page with the return url and return false
        return of(false);
     })
     );

  }
}
