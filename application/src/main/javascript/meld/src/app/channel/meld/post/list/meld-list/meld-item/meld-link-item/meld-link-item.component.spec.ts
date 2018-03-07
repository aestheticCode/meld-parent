import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldLinkItemComponent } from './meld-link-item.component';

describe('MeldLinkItemComponent', () => {
  let component: MeldLinkItemComponent;
  let fixture: ComponentFixture<MeldLinkItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldLinkItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldLinkItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
