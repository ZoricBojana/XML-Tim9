import { Component, OnInit } from '@angular/core';
import { PersonService } from 'src/app/services/person.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reviewer-nav',
  templateUrl: './reviewer-nav.component.html',
  styleUrls: ['./reviewer-nav.component.css']
})
export class ReviewerNavComponent implements OnInit {

  constructor(private personService: PersonService, private router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.personService.logout();
    this.router.navigate(['login']);
  }

}
