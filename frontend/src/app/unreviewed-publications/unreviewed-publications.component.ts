import { Component, OnInit } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';
import { PersonService } from '../services/person.service';
import { ProcessDto } from '../dto/process-dto';
import { ProcessService } from '../services/process.service';

@Component({
  selector: 'app-unreviewed-publications',
  templateUrl: './unreviewed-publications.component.html',
  styleUrls: ['./unreviewed-publications.component.css']
})
export class UnreviewedPublicationsComponent implements OnInit {

  articles: any;
  reviewers: any;
  selectedReviewers: string[];

  constructor(private articleService: ServiceSaService, private personService: PersonService, private processService: ProcessService) { }

  ngOnInit() {
    this.articleService.getPapersForReview().subscribe(
      res => {
        this.articles = res;
        console.log(this.articles);
      }
    );

    this.personService.getReviewers().subscribe(
     res => {
        this.reviewers = res;
        console.log(this.reviewers);
     }
   );
  }

  submitReviewers(paperId: string) {
    console.log(paperId);
    console.log(this.selectedReviewers);

    const process = new ProcessDto();
    process.articleId = paperId;
    process.reviewersId = this.selectedReviewers;

    this.processService.sendProcessData(process).subscribe(
      res => {
        console.log(res);
      }
    );
  }

}
