import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldGoogleMapsDetailsComponent } from './meld-google-maps-details.component';

describe('MeldGoogleMapsDetailsComponent', () => {
  let component: MeldGoogleMapsDetailsComponent;
  let fixture: ComponentFixture<MeldGoogleMapsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldGoogleMapsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldGoogleMapsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
