import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable()
export class AuthGuard  implements CanActivate {
    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log("canActivate.stats data", this.authService.isLoggedIn);
    if (this.authService.isLoggedIn) {
      // logged in so return true
      return true;
    }
     this.authService.stats().subscribe(
       data => {
         console.log("authService.stats data", data);
        this.authService.isLoggedIn = data ? data : true ;
        console.log("authService.stats this.authService.isLoggedIn", this.authService.isLoggedIn);
        return true;
       },
       err => {
        this.router.navigate(['/login']);
            // not logged in so redirect to login page with the return url and return false
        return false;
       }
     );

  }
}
