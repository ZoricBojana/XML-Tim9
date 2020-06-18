import { Component, OnInit } from '@angular/core';
import { PersonService } from 'src/app/services/person.service';

@Component({
  selector: 'app-reviewer-nav',
  templateUrl: './reviewer-nav.component.html',
  styleUrls: ['./reviewer-nav.component.css']
})
export class ReviewerNavComponent implements OnInit {

  constructor(private personService: PersonService) { }

  ngOnInit() {
  }

  logout() {
    this.personService.logout();
  }

}
