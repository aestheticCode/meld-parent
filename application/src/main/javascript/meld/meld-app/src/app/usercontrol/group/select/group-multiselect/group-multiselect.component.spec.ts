import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupMultiselectComponent } from './group-multiselect.component';

describe('GroupMultiselectComponent', () => {
  let component: GroupMultiselectComponent;
  let fixture: ComponentFixture<GroupMultiselectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupMultiselectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupMultiselectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
