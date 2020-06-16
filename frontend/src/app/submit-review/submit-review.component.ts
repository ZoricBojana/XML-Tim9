import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../services/review.service';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';

declare var Xonomy: any;

@Component({
  selector: 'app-submit-review',
  templateUrl: './submit-review.component.html',
  styleUrls: ['./submit-review.component.css']
})
export class SubmitReviewComponent implements OnInit {

  constructor( private reviewService: ReviewService,
               private router: Router) { }

  docSpec = {
    onchange() {
       // console.log('I been changed now!');
    },
    validate(obj) {
      // console.log('I be validatin\' now!');
    },
    elements: {
      review: {

      },
      review_form: {

      },
      tehnical_grade: {
      },
      language_grade: {},
      general_impression: {
        menu: [{
          caption: 'Append a <paragraph>',
          action: Xonomy.newElementChild,
          actionParameter: '<paragraph></paragraph>'
          }]
      },
      good_sides: {
        menu: [{
          caption: 'Append a <paragraph>',
          action: Xonomy.newElementChild,
          actionParameter: '<paragraph></paragraph>'
          }]
      },
      bad_sides: {
        menu: [{
          caption: 'Append a <paragraph>',
          action: Xonomy.newElementChild,
          actionParameter: '<paragraph></paragraph>'
          }]
      },
      grade_value: {
        hasText: true
      },
      questionnaire: {},
      item: {},
      question: {},
      response: {
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['Strongly disagree', 'Disagree', 'Neutral', 'Agree', 'Strongly agree'],
      },
      comments: {
        hasText: true
      },
      secret_comments: {
        hasText: true
      },
      recommendation: {
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['Accept', 'Reject', 'Major revision', 'Minor revision'],
      },
      paragraph: {
        hasText: true,
        menu: [
          {
            caption: 'Delete this <paragraph>',
            action: Xonomy.deleteElement
          },
          {
            caption: 'New <paragraph> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<paragraph/>'
          }, {
            caption: 'New <paragraph> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<paragraph/>'
          }, {
            caption: 'Append a <b>',
            action: Xonomy.newElementChild,
            actionParameter: '<b/>'
          }, {
            caption: 'Append a <u>',
            action: Xonomy.newElementChild,
            actionParameter: '<u/>'
          }, {
            caption: 'Append a <i>',
            action: Xonomy.newElementChild,
            actionParameter: '<i/>'
          }, {
            caption: 'Append a <list>',
            action: Xonomy.newElementChild,
            actionParameter: '<list/>'
          }, {
            caption: 'Append a <new_lline>',
            action: Xonomy.newElementChild,
            actionParameter: '<new_lline/>'
          }
      ]
      },
      b: {
        hasText: true,
        menu: [{
          caption: 'Append a <u>',
          action: Xonomy.newElementChild,
          actionParameter: '<u/>'
          }, {
            caption: 'Append a <i>',
            action: Xonomy.newElementChild,
            actionParameter: '<i/>'
            }
        ]
      },
      u: {
        hasText: true,
        menu: [{
          caption: 'Append a <b>',
          action: Xonomy.newElementChild,
          actionParameter: '<b/>'
          }, {
            caption: 'Append a <i>',
            action: Xonomy.newElementChild,
            actionParameter: '<i/>'
            }
        ]
      },
      i: {
        hasText: true,
        menu: [{
          caption: 'Append a <u>',
          action: Xonomy.newElementChild,
          actionParameter: '<u/>'
          }, {
            caption: 'Append a <b>',
            action: Xonomy.newElementChild,
            actionParameter: '<b/>'
            }
        ]
      },
      list: {
        menu: [{
          caption: 'Append a <list_item>',
          action: Xonomy.newElementChild,
          actionParameter: '<list_item/>'
          }
        ]
      },
      list_item: {
        hasText: true,
        menu: [{
          caption: 'Append a <u>',
          action: Xonomy.newElementChild,
          actionParameter: '<u/>'
          }, {
            caption: 'Append a <b>',
            action: Xonomy.newElementChild,
            actionParameter: '<b/>'
          }, {
            caption: 'Append a <i>',
            action: Xonomy.newElementChild,
            actionParameter: '<i/>'
          }, {
            caption: 'New <list_item> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<list_item/>'
          }, {
            caption: 'New <list_item> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<list_item/>'
          }
        ]
      },
      new_lline: {
      },
    }
  };

  ngOnInit() {
    this.start();
  }

start() {

    const xml = '<review xmlns="http://www.uns.ac.rs/MSB" xmlns:msb="http://www.uns.ac.rs/MSB">' +
                '<review_form><technical_grade><grade_value></grade_value></technical_grade>' +
                '<language_grade><grade_value></grade_value></language_grade>' +
                '<general_impression></general_impression>' +
                '<good_sides></good_sides>' +
                '<bad_sides></bad_sides></review_form>' +
                '<questionnaire>' +
                '<item><question>The subject addressed in this article is worthy of investigation' +
                '</question><response></response></item><item><question>The information presented is new' +
                '</question><response></response></item><item><question>The conclusions are supported by the data' +
                '</question><response></response></item><item><question>The manuscript is appropriate for the journal' +
                '</question><response></response></item><item><question>Organization of the manuscript is appropriate' +
                '</question><response></response></item><item><question>Figures, tables and supplementary data are appropriate' +
                '</question><response></response></item></questionnaire>' +
                '<comments></comments><secret_comments></secret_comments>' +
                '<recommendation></recommendation>' +
                '</review>';
    const editor = document.getElementById('editor');
    Xonomy.render(xml, editor, this.docSpec);
    }
submit() {
    const xml = Xonomy.harvest();
    // do something with xml...
    console.log(xml);
    this.reviewService.add(xml as string).subscribe(
      result => {
        this.router.navigate(['home']);
      }
    );
    }
}
