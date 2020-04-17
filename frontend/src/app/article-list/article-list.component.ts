import { Component, OnInit, Input } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';
import { ScientificArticle } from '../model/scientific-article';
import { Author } from '../model/author';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {

  @Input()
  articles: any;
  constructor(private articleService: ServiceSaService, private router: Router) { }

  ngOnInit() {
  }

  getHTML(id: string) {
    this.router.navigate(['articles', id]);
  }

  getPDF(id: string) {
    this.router.navigate(['articles/pdf', id]);
  }

}
