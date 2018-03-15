import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldDateComponent } from './meld-date.component';

describe('MeldDateComponent', () => {
  let component: MeldDateComponent;
  let fixture: ComponentFixture<MeldDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldDateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
