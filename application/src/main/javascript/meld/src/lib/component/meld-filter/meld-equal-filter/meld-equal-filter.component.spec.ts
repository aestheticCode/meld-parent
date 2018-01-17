import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldEqualFilterComponent } from './meld-equal-filter.component';

describe('MeldEqualFilterComponent', () => {
  let component: MeldEqualFilterComponent;
  let fixture: ComponentFixture<MeldEqualFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldEqualFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldEqualFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
