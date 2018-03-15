import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldTabComponent } from './meld-tab.component';

describe('MeldTabComponent', () => {
  let component: MeldTabComponent;
  let fixture: ComponentFixture<MeldTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
