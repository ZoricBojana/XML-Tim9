import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScArticlesComponent } from './sc-articles.component';

describe('ScArticlesComponent', () => {
  let component: ScArticlesComponent;
  let fixture: ComponentFixture<ScArticlesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScArticlesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScArticlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
