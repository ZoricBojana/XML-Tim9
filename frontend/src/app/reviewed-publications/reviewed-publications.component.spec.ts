import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewedPublicationsComponent } from './reviewed-publications.component';

describe('ReviewedPublicationsComponent', () => {
  let component: ReviewedPublicationsComponent;
  let fixture: ComponentFixture<ReviewedPublicationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewedPublicationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewedPublicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
