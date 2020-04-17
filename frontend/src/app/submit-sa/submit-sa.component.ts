import { Component, OnInit } from '@angular/core';
import { ServiceSaService } from '../services/service-sa.service';
import { Router } from '@angular/router';

declare var Xonomy: any;

@Component({
  selector: 'app-submit-sa',
  templateUrl: './submit-sa.component.html',
  styleUrls: ['./submit-sa.component.css']
})
export class SubmitSAComponent implements OnInit {

  constructor(
    private SAservice: ServiceSaService,
    private router: Router
  ) { }

  docSpec = {
    onchange() {
      // console.log('I been changed now!');
    },
    validate(obj) {
      // console.log('I be validatin\' now!');
    },
    elements: {
      scientific_article: {
      },
      article_info: {
      },
      title: {
        hasText: true
      },
      publisher: {
        hasText: true
      },
      key_words: {
        menu: [{
          caption: 'Append a <key_word>',
          action: Xonomy.newElementChild,
          actionParameter: '<key_word></key_word>'
          }]
      },
      key_word: {
        hasText: true,
        menu: [{
          caption: 'Delete this <key_word>',
          action: Xonomy.deleteElement
          }, {
            caption: 'New <key_word> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<key_word/>'
            }, {
            caption: 'New <key_word> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<key_word/>'
            }
        ]
      },
      abstract: {
        menu: [{
          caption: 'Append a <paragraph>',
          action: Xonomy.newElementChild,
          actionParameter: '<paragraph></paragraph>'
          }]
      },
      introduction: {
        menu: [{
          caption: 'Append a <paragraph>',
          action: Xonomy.newElementChild,
          actionParameter: '<paragraph></paragraph>'
          }]
      },
      body: {
        menu: [{
        caption: 'Append a <paragraph>',
        action: Xonomy.newElementChild,
        actionParameter: '<paragraph></paragraph>'
        }]
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
      chapters: {
        menu: [{
          caption: 'Append a <chapter>',
          action: Xonomy.newElementChild,
          actionParameter: '<chapter><title></title><chapter_paragraph></chapter_paragraph></chapter>'
          }]
      },
      chapter: {
        menu: [
          {
            caption: 'Delete this <chapter>',
            action: Xonomy.deleteElement
          },
          {
            caption: 'Append a <chapter_paragraph>',
            action: Xonomy.newElementChild,
            actionParameter: '<chapter_paragraph/>'
          },
          {
            caption: 'New <chapter> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<chapter><title></title><chapter_paragraph></chapter_paragraph></chapter>'
          }, {
            caption: 'New <chapter> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<chapter><title></title><chapter_paragraph></chapter_paragraph></chapter>'
          }
      ]
      },
      chapter_paragraph: {
        hasText: true,
        menu: [
          {
            caption: 'Delete this <chapter_paragraph>',
            action: Xonomy.deleteElement
          },
          {
            caption: 'New <chapter_paragraph> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<chapter_paragraph/>'
          }, {
            caption: 'New <chapter_paragraph> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<chapter_paragraph/>'
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
            caption: 'Append a <picture>',
            action: Xonomy.newElementChild,
            actionParameter: '<picture src=""><picture_description/></picture>'
          }, {
            caption: 'Append a <table>',
            action: Xonomy.newElementChild,
            actionParameter: '<table><table_description/></table>'
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
      picture: {
        attributes: {
          src: {
            asker: Xonomy.askString,
            mandatory: true
          },
          width: {
            asker: Xonomy.askString
          },
          height: {
            asker: Xonomy.askString
          }
        },
        menu: [
          {
            caption: 'Add @src=""',
            action: Xonomy.newAttribute,
            actionParameter: {name: 'src', value: ''},
            hideIf(jsElement: any) {
              return jsElement.hasAttribute('src');
            }
          }, {
            caption: 'Add @width=""',
            action: Xonomy.newAttribute,
            actionParameter: {name: 'width', value: ''},
            hideIf(jsElement: any) {
              return jsElement.hasAttribute('width');
            }
          }, {
            caption: 'Add @height=""',
            action: Xonomy.newAttribute,
            actionParameter: {name: 'height', value: ''},
            hideIf(jsElement: any) {
              return jsElement.hasAttribute('height');
            }
          }
        ]
      },
      picture_description: {
        hasText: true
      },
      table_description: {
        hasText: true
      },
      table: {
        attributes: {
          width: {
            asker: Xonomy.askString,
            menu: [{
              caption: 'Delete this @width',
              action: Xonomy.deleteAttribute
            }]
          },
          height: {
            asker: Xonomy.askString,
            menu: [{
              caption: 'Delete this @width',
              action: Xonomy.deleteAttribute
            }]
          }
        },
        menu: [
          {
            caption: 'Append a <th>',
            action: Xonomy.newElementChild,
            actionParameter: '<th/>'
          }, {
            caption: 'Append a <tr>',
            action: Xonomy.newElementChild,
            actionParameter: '<tr/>'
          }, {
            caption: 'Add @height=""',
            action: Xonomy.newAttribute,
            actionParameter: {name: 'height', value: ''},
            hideIf(jsElement: any) {
              return jsElement.hasAttribute('height');
            }
          }, {
            caption: 'Add @width=""',
            action: Xonomy.newAttribute,
            actionParameter: {name: 'width', value: ''},
            hideIf(jsElement: any) {
              return jsElement.hasAttribute('width');
            }
          }
        ]
      },
      tr: {
        menu: [
          {
            caption: 'Append a <td>',
            action: Xonomy.newElementChild,
            actionParameter: '<td/>'
          }, {
            caption: 'New <tr> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<tr/>'
          }, {
            caption: 'New <tr> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<tr/>'
          }
        ]
      },
      td: {
        hasText: true,
        menu: [
          {
            caption: 'New <td> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<td/>'
          }, {
            caption: 'New <td> after this',
            action: Xonomy.newsElementAfter,
            actionParameter: '<td/>'
          }
        ]
      },
      th: {
        canDropTo: ['table'],
        menu: [
          {
            caption: 'Append a <td>',
            action: Xonomy.newElementChild,
            actionParameter: '<td/>'
          }, {
            caption: 'New <th> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<th/>'
          }, {
            caption: 'New <th> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<th/>'
          }
        ]
      },
      description: {
        hasText: true
      },
      ref: {
      },
      element_id: {},
      formula: {},
      conclusion: {
        menu: [{
          caption: 'Append a <paragraph>',
          action: Xonomy.newElementChild,
          actionParameter: '<paragraph></paragraph>'
          }]
      }
    }
  };

  ngOnInit() {
    this.start();
  }

start() {

    const xml = '<scientific_article xmlns="http://www.uns.ac.rs/MSB" xmlns:msb="http://www.uns.ac.rs/MSB">' +
                  '<article_info><title></title><publisher></publisher></article_info>' +
                  '<key_words></key_words><abstract></abstract><introduction></introduction>' +
                  '<chapters></chapters><conclusion></conclusion></scientific_article>';
    const editor = document.getElementById('editor');
    Xonomy.render(xml, editor, this.docSpec);
    }
submit() {
    const xml = Xonomy.harvest();
    // do something with xml...
    console.log(xml);
    this.SAservice.add(xml as string).subscribe(
      result => {
        this.router.navigate(['home']);
      }
    );
    }

}
