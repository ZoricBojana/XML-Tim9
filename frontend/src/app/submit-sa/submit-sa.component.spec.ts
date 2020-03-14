import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitSAComponent } from './submit-sa.component';

describe('SubmitSAComponent', () => {
  let component: SubmitSAComponent;
  let fixture: ComponentFixture<SubmitSAComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitSAComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitSAComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
