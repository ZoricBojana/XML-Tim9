import { Component, OnInit } from '@angular/core';
import { ProcessService } from '../services/process.service';
import { ServiceSaService } from '../services/service-sa.service';
import { ReviewService } from '../services/review.service';

@Component({
  selector: 'app-reviewed-publications',
  templateUrl: './reviewed-publications.component.html',
  styleUrls: ['./reviewed-publications.component.css']
})
export class ReviewedPublicationsComponent implements OnInit {

  articles: any;

  url: any;

  constructor(private saService: ServiceSaService, private reviewService: ReviewService) { }

  ngOnInit() {
    this.saService.getReviewedForEditor().subscribe(
      res => {
        this.articles = res;
      }
    );
  }

  reload() {
    this.saService.getReviewedForEditor().subscribe(
      res => {
        this.articles = res;
      }
    );
  }

  reject(id: string) {
    this.saService.reject(id).subscribe(
      res =>  {
        console.log('Rejected');
        this.reload();
      }
    );
  }

  publish(id: string) {
    this.saService.publish(id).subscribe(
      res =>  {
        console.log('Published');
        this.reload();
      }
    );
  }

  getReviews(articleId: string){

    this.reviewService.getPDFReviews(articleId)
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
