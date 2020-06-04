import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicationsToReviewComponent } from './publications-to-review.component';

describe('PublicationsToReviewComponent', () => {
  let component: PublicationsToReviewComponent;
  let fixture: ComponentFixture<PublicationsToReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublicationsToReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublicationsToReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
