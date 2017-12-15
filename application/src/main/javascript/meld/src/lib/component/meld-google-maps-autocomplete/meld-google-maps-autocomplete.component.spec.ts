import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldGoogleMapsAutocompleteComponent } from './meld-google-maps-autocomplete.component';

describe('MeldGoogleMapsAutocompleteComponent', () => {
  let component: MeldGoogleMapsAutocompleteComponent;
  let fixture: ComponentFixture<MeldGoogleMapsAutocompleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldGoogleMapsAutocompleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldGoogleMapsAutocompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
