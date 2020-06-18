import { Component, OnInit } from '@angular/core';
import { CoverLetterService } from '../services/cover-letter.service';
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from '@angular/router';

declare var Xonomy;

@Component({
  selector: 'app-cover-letter',
  templateUrl: './cover-letter.component.html',
  styleUrls: ['./cover-letter.component.css']
})
export class CoverLetterComponent implements OnInit {

  constructor(private clService: CoverLetterService, private route: ActivatedRoute, private router: Router) { }

  docSpec = {
    elements : {
      cover_letter: {

      },
      body: {
        hasText: true
      }
    }
  };

  ngOnInit() {
    this.start();
  }

  start() {
    const xml = '<cover_letter xmlns="http://www.uns.ac.rs/MSB" xmlns:msb="http://www.uns.ac.rs/MSB"><body></body></cover_letter>';
    const editor = document.getElementById('editor');
    Xonomy.render(xml, editor, this.docSpec);
    }
  submit() {
    const xml = Xonomy.harvest();
    // do something with xml...
    console.log(xml);
    const id = this.route.snapshot.params.id;
    this.clService.add(xml as string, id).subscribe(
      result => {
        this.router.navigate(['myPapers']);
      }
    );
    }

}
