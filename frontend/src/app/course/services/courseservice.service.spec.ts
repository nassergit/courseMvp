import { TestBed } from '@angular/core/testing';

import { CourseserviceService } from './courseservice.service';
import { HttpClientModule } from '@angular/common/http';

describe('CourseserviceService', () => {
  let service: CourseserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(CourseserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
