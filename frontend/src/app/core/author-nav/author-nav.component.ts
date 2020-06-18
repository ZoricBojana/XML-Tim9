import { Component, OnInit } from '@angular/core';
import { PersonService } from 'src/app/services/person.service';

@Component({
  selector: 'app-author-nav',
  templateUrl: './author-nav.component.html',
  styleUrls: ['./author-nav.component.css']
})
export class AuthorNavComponent implements OnInit {

  constructor(private personService: PersonService) { }

  ngOnInit() {
  }

  logout() {
    this.personService.logout();
  }

}
