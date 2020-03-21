import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlePdfComponent } from './article-pdf.component';

describe('ArticlePdfComponent', () => {
  let component: ArticlePdfComponent;
  let fixture: ComponentFixture<ArticlePdfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticlePdfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticlePdfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
