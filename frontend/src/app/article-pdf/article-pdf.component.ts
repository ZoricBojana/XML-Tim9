import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceSaService } from '../services/service-sa.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-article-pdf',
  templateUrl: './article-pdf.component.html',
  styleUrls: ['./article-pdf.component.css']
})
export class ArticlePdfComponent implements OnInit {

  id: string;
  url: any;
  constructor(private route: ActivatedRoute, private router: Router, private serviceSA: ServiceSaService,
              private sanitizer: DomSanitizer) { }

  ngOnInit() {

    this.id = this.route.snapshot.params.id;

    this.serviceSA.getPDF(this.id)
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
