import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldFileContainerComponent } from './meld-file-container.component';

describe('MeldFileContainerComponent', () => {
  let component: MeldFileContainerComponent;
  let fixture: ComponentFixture<MeldFileContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldFileContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldFileContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
