import { TestBed, inject } from '@angular/core/testing';

import { MeldRouterService } from './meld-router.service';

describe('MeldRouterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MeldRouterService]
    });
  });

  it('should be created', inject([MeldRouterService], (service: MeldRouterService) => {
    expect(service).toBeTruthy();
  }));
});
