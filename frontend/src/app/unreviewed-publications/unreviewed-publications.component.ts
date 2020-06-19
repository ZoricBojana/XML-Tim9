import { Component, OnInit } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';
import { PersonService } from '../services/person.service';
import { ProcessDto } from '../dto/process-dto';
import { ProcessService } from '../services/process.service';
import { CoverLetterService } from '../services/cover-letter.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-unreviewed-publications',
  templateUrl: './unreviewed-publications.component.html',
  styleUrls: ['./unreviewed-publications.component.css']
})
export class UnreviewedPublicationsComponent implements OnInit {

  articles: any;
  reviewers: any;
  selectedReviewers: string[];

  url: any;

  constructor(private articleService: ServiceSaService, private personService: PersonService, private processService: ProcessService,
              private letterService: CoverLetterService, private sanitizer: DomSanitizer) { }

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

  showCoverLetter(id: string) {
    // SA_19062020093256202

    this.letterService.getPDF(id)
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
