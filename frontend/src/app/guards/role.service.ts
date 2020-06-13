import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { PersonService } from '../services/person.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    public auth: PersonService,
    public router: Router
  ) { }

   canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string = route.data.expectedRoles;
    const token = localStorage.getItem('user');


    return true;
  }
}
