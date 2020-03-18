import { Component, OnInit } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';
import { ScientificArticle } from '../model/scientific-article';
import { Author } from '../model/author';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {

  articles: any;
  constructor(private articleService: ServiceSaService) { }

  ngOnInit() {

    this.articleService.searchByText(' ')
    .subscribe (res => {

      this.articles = res;
    });
  }

}
