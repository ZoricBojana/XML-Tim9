import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  public role: string;

  title = 'sc-Publications | Homepage';
  navbarOpen = false;

  public constructor(private titleService: Title, private router: Router ) {
    this.setTitle(this.title);
  }

  checkRole() {
    const user = localStorage.getItem('user');
    if (!user) {
    // this.router.navigate(['login']);
     this.role = 'USER';
     return;
   }

    const roles = JSON.parse(JSON.parse(user)).roles;
    console.log(roles);

    if (roles === null) {
      this.role = 'USER';
      return;
    }

    if (roles.indexOf('EDITOR_ROLE') > -1) {
      this.role = 'EDITOR';
    } else if (roles.indexOf('REVIEWER_ROLE') > -1) {
      this.role = 'REVIEWER';
    } else if (roles.indexOf('USER_ROLE') > -1) {
      this.role = 'AUTHOR';
    }
    console.log('Role ' + this.role);
  }

  public setTitle( newTitle: string) {
    this.titleService.setTitle( newTitle );
  }

  public toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }
}

