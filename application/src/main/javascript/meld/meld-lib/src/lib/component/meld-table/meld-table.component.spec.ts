import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldTableComponent } from './meld-table.component';

describe('MeldTableComponent', () => {
  let component: MeldTableComponent;
  let fixture: ComponentFixture<MeldTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
