import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewerForReviewComponent } from './reviewer-for-review.component';

describe('ReviewerForReviewComponent', () => {
  let component: ReviewerForReviewComponent;
  let fixture: ComponentFixture<ReviewerForReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewerForReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewerForReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
