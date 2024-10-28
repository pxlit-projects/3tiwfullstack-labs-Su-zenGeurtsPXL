import { TestBed } from '@angular/core/testing';

import { InmemoryDataService } from './inmemory-data.service';

describe('InmemoryDataService', () => {
  let service: InmemoryDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InmemoryDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
