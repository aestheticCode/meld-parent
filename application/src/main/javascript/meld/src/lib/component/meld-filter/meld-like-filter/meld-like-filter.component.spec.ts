import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldLikeFilterComponent } from './meld-like-filter.component';

describe('MeldLikeFilterComponent', () => {
  let component: MeldLikeFilterComponent;
  let fixture: ComponentFixture<MeldLikeFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldLikeFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldLikeFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
