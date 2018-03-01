import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriesSelectViewComponent } from './categories-select-view.component';

describe('CategoriesSelectViewComponent', () => {
  let component: CategoriesSelectViewComponent;
  let fixture: ComponentFixture<CategoriesSelectViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoriesSelectViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriesSelectViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
