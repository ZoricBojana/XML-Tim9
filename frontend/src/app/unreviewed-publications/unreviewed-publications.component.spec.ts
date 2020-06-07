import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnreviewedPublicationsComponent } from './unreviewed-publications.component';

describe('UnreviewedPublicationsComponent', () => {
  let component: UnreviewedPublicationsComponent;
  let fixture: ComponentFixture<UnreviewedPublicationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnreviewedPublicationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnreviewedPublicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
