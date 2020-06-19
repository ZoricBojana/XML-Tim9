import { Component, OnInit } from '@angular/core';
import { PersonService } from 'src/app/services/person.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-editor-nav',
  templateUrl: './editor-nav.component.html',
  styleUrls: ['./editor-nav.component.css']
})
export class EditorNavComponent implements OnInit {

  constructor(private personService: PersonService, private router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.personService.logout();
    this.router.navigate(['login']);
  }
}
