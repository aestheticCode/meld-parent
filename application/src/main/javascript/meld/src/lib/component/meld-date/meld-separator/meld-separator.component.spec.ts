import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldSeparatorComponent } from './meld-separator.component';

describe('MeldSeparatorComponent', () => {
  let component: MeldSeparatorComponent;
  let fixture: ComponentFixture<MeldSeparatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldSeparatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldSeparatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
