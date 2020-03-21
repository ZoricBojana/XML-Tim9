import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ServiceSaService } from '../services/service-sa.service'; 

@Component({
  selector: 'app-article-html',
  templateUrl: './article-html.component.html',
  styleUrls: ['./article-html.component.css']
})
export class ArticleHtmlComponent implements OnInit {

  id: string;
  response: string;

  constructor(private route: ActivatedRoute, private router: Router, private serviceSA: ServiceSaService) { }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.serviceSA.getHTML(this.id)
      .subscribe(res => {
        console.log(res);
        this.response = res;
      });
  }

}
