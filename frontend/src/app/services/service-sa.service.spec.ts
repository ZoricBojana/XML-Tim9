import { TestBed } from '@angular/core/testing';

import { ServiceSaService } from './service-sa.service';

describe('ServiceSaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ServiceSaService = TestBed.get(ServiceSaService);
    expect(service).toBeTruthy();
  });
});
