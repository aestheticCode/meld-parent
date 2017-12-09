import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldSelectViewComponent } from './meld-select-view.component';

describe('MeldSelectViewComponent', () => {
  let component: MeldSelectViewComponent;
  let fixture: ComponentFixture<MeldSelectViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldSelectViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldSelectViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
