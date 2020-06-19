import { Component, OnInit } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-author-info',
  templateUrl: './author-info.component.html',
  styleUrls: ['./author-info.component.css']
})
export class AuthorInfoComponent implements OnInit {

  articles: any;
  id: string;

  constructor(private route: ActivatedRoute, private articleService: ServiceSaService) { }

  ngOnInit() {

    this.id = this.route.snapshot.params.id;
    console.log(this.id);

    this.articleService.searchByAuthorUsername(this.id)
    .subscribe (res => {
      console.log(res);
      this.articles = res;
    });
  }

}
