import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldGridComponent } from './meld-grid.component';

describe('MeldGridComponent', () => {
  let component: MeldGridComponent;
  let fixture: ComponentFixture<MeldGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
