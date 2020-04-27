import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KeyWordInfoComponent } from './key-word-info.component';

describe('KeyWordInfoComponent', () => {
  let component: KeyWordInfoComponent;
  let fixture: ComponentFixture<KeyWordInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KeyWordInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KeyWordInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
