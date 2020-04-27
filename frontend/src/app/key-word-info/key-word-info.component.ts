import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ServiceSaService } from '../services/service-sa.service';

@Component({
  selector: 'app-key-word-info',
  templateUrl: './key-word-info.component.html',
  styleUrls: ['./key-word-info.component.css']
})
export class KeyWordInfoComponent implements OnInit {

  keyWord: string;
  articles: any;

  constructor(private route: ActivatedRoute, private articleService: ServiceSaService) {

   }

  ngOnInit() {
    this.keyWord = this.route.snapshot.params.kw;
    console.log('Key word ' + this.keyWord);
    this.articleService.searchByKeyword(this.keyWord).subscribe(res => {
      console.log(res);
      this.articles = res;
    });
  }

}
