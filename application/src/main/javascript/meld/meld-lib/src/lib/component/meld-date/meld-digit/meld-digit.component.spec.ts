import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldDigitComponent } from './meld-digit.component';

describe('MeldDigitComponent', () => {
  let component: MeldDigitComponent;
  let fixture: ComponentFixture<MeldDigitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldDigitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldDigitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
