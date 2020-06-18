import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorNavComponent } from './author-nav.component';

describe('AuthorNavComponent', () => {
  let component: AuthorNavComponent;
  let fixture: ComponentFixture<AuthorNavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorNavComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
