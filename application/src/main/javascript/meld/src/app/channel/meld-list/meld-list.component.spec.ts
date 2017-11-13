import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldListComponent } from './meld-list.component';

describe('MeldListComponent', () => {
  let component: MeldListComponent;
  let fixture: ComponentFixture<MeldListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
