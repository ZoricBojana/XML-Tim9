import { Component, OnInit } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';

@Component({
  selector: 'app-user-articles',
  templateUrl: './user-articles.component.html',
  styleUrls: ['./user-articles.component.css']
})
export class UserArticlesComponent implements OnInit {

  articles: any;

  constructor(private articleService: ServiceSaService) { }

  ngOnInit() {
    this.articleService.getAuthorsPapers().subscribe(
    res => {
      this.articles = res;
    }
    );
  }

}
