import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldFilterComponent } from './meld-filter.component';

describe('MeldFilterComponent', () => {
  let component: MeldFilterComponent;
  let fixture: ComponentFixture<MeldFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
