import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldPhotoItemComponent } from './meld-photo-item.component';

describe('MeldPhotoItemComponent', () => {
  let component: MeldPhotoItemComponent;
  let fixture: ComponentFixture<MeldPhotoItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldPhotoItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldPhotoItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
