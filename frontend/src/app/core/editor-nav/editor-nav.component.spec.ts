import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorNavComponent } from './editor-nav.component';

describe('EditorNavComponent', () => {
  let component: EditorNavComponent;
  let fixture: ComponentFixture<EditorNavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorNavComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
