import { Component, OnInit, Input } from '@angular/core';
import { ScientificArticle } from '../model/scientific-article';

@Component({
  selector: 'app-article-table',
  templateUrl: './article-table.component.html',
  styleUrls: ['./article-table.component.css']
})
export class ArticleTableComponent implements OnInit {

  @Input() articles: any;

  constructor() { }

  ngOnInit() {
  }

}
