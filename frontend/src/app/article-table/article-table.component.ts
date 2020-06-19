import { Component, OnInit, Input } from '@angular/core';
import { ScientificArticle } from '../model/scientific-article';
import { ServiceSaService } from '../services/service-sa.service';

@Component({
  selector: 'app-article-table',
  templateUrl: './article-table.component.html',
  styleUrls: ['./article-table.component.css']
})
export class ArticleTableComponent implements OnInit {

  @Input() articles: any;

  constructor(private articleService: ServiceSaService) { }

  ngOnInit() {
  }

  delete(id: string) {
    this.articleService.delete(id).subscribe(
      res => {
        console.log('deleted');
      }
    );
  }

}
