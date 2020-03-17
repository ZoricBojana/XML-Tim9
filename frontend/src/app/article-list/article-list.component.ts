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

  articles: ScientificArticle[];
  constructor(private articleService: ServiceSaService) { }

  ngOnInit() {
    this.articles = new Array<ScientificArticle>();
    this.articleService.searchByText('prvog')
    .subscribe (res => {
      console.log(res[0]);
      for (const art of res) {
        const article = new ScientificArticle();
        article.id = art.articleInfo.id;
        article.publishDate = art.articleInfo.publishDate;
        article.publisher = art.articleInfo.publisher;
        article.keyWords = art.keyWords;
        article.authors = (art.authors as Author[]);

        this.articles.push(article);
      }
    });

    console.log(this.articles);
  }

}
