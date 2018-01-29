import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterSchoolComponent } from './filter-school.component';

describe('FilterSchoolComponent', () => {
  let component: FilterSchoolComponent;
  let fixture: ComponentFixture<FilterSchoolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilterSchoolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterSchoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
