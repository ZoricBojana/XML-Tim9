import { Component, OnInit } from '@angular/core';
import { PersonService } from 'src/app/services/person.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-author-nav',
  templateUrl: './author-nav.component.html',
  styleUrls: ['./author-nav.component.css']
})
export class AuthorNavComponent implements OnInit {

  constructor(private personService: PersonService, private router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.personService.logout();
    this.router.navigate(['login']);
  }

}
