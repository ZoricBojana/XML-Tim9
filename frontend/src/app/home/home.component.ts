import { Component, OnInit, HostListener, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { ServiceSaService } from '../services/service-sa.service';
import { Filter } from '../dto/filter';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  articles: any;

  private searchValue: string;

  private filter = {};


  constructor(private articleService: ServiceSaService, private router: Router) {
    this.searchValue = '';

  }

  ngOnInit() {
    this.articleService.searchByText(' ')
    .subscribe (res => {
      console.log(res);
      this.articles = res;
    });
  }

  onSubmitSearch() {
    let val = ' ';
    if (this.searchValue) {
      val = this.searchValue;
    }
    this.articleService.searchByText(val)
    .subscribe (res => {
      this.articles = res;
    });
  }

  onFilterSearch() {

    this.articleService.filter(this.filter as Filter)
    .subscribe(
      res => {
        this.articles = res;
      }
    );
  }

}
