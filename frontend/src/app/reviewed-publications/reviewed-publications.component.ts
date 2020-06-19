import { Component, OnInit } from '@angular/core';
import { ProcessService } from '../services/process.service';
import { ServiceSaService } from '../services/service-sa.service';

@Component({
  selector: 'app-reviewed-publications',
  templateUrl: './reviewed-publications.component.html',
  styleUrls: ['./reviewed-publications.component.css']
})
export class ReviewedPublicationsComponent implements OnInit {

  articles: any;

  constructor(private saService: ServiceSaService) { }

  ngOnInit() {
    this.saService.getReviewedForEditor().subscribe(
      res => {
        this.articles = res;
      }
    );
  }

}
