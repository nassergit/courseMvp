import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetCourseComponent } from './get-course.component';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('GetCourseComponent', () => {
  let component: GetCourseComponent;
  let fixture: ComponentFixture<GetCourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GetCourseComponent, HttpClientModule],
      providers: [{provide: ActivatedRoute, useValue: {params: of({nomCourse: 'nom', numCourse: 1})}}]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GetCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
