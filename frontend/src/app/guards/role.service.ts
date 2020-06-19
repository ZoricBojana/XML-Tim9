import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { PersonService } from '../services/person.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard /*implements CanActivate*/ {

  constructor(
    public router: Router
  ) { }
/*
   canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

    const isLoggedIn = !!localStorage.getItem('token');

    if (isLoggedIn) {
      const user = JSON.parse(localStorage.getItem('user'));

      if (user) {
        if (route.data.roles && !route.data.roles.some(r => user.roles.includes(r))) {
          this.router.navigate(['/']);
          return false;
        }

        return true;
      }
    }
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }*/
}
