import { Injectable } from '@angular/core';
import { Course, CourseEndPointService } from '../../../generated-api-client/mvp/courses-api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseserviceService {

  constructor(private courseApi: CourseEndPointService) { 

  }

  public getCourseByNameAndNum(nomCourse: string, numCourse:number): Observable<Course>{
    return this.courseApi.getCourse(nomCourse,numCourse);
  }

  public createCourse(course: Course): Observable<string>{
    return this.courseApi.createCourse(course);
  }
}
