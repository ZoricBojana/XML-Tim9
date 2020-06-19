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
  url: any;

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

  getPdf(id: string) {
    this.articleService.getPDF(id)
    .subscribe(
      res => {
        const file = new Blob([res], {type: 'application/pdf'});
        const fileURL = URL.createObjectURL(file);
        this.url = fileURL;
        console.log(fileURL);
        window.open(fileURL);
      }
    );
  }

}
