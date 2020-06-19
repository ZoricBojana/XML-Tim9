import { Component, OnInit } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';
import { Router } from '@angular/router';
import { ProcessService } from '../services/process.service';

@Component({
  selector: 'app-reviewer-for-review',
  templateUrl: './reviewer-for-review.component.html',
  styleUrls: ['./reviewer-for-review.component.css']
})
export class ReviewerForReviewComponent implements OnInit {

  articles: any;

  constructor(private articleService: ServiceSaService, private processService: ProcessService , private router: Router) { }

  ngOnInit() {
    this.getPapers();
  }

  getPapers() {
    this.articleService.getPapersForReviewer().subscribe(
      res => {
        this.articles = res;
        console.log(this.articles);
      }
    );
  }

  makeReview(articleId: string) {
    this.router.navigate(['submitReview', articleId]);
  }

  rejectReview(articleId: string) {
    this.processService.rejectReview(articleId).subscribe(
      res => {
        console.log('rejected');
        this.getPapers();
      }
    );
  }

}
