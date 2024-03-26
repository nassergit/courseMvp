import { Injectable } from '@angular/core';
import { Course, CourseEndPointService, CreateCourse200Response } from '../../../generated-api-client/mvp/courses-api';
import { Observable} from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CourseserviceService {

  constructor(private courseApi: CourseEndPointService, private http: HttpClient) { 

  }

  public getCourseByNameAndNum(nomCourse: string, numCourse:number): Observable<Course>{
    return this.courseApi.getCourse(nomCourse,numCourse);
  }

  public createCourse(course: Course): Observable<HttpResponse<CreateCourse200Response>>{
    return this.courseApi.createCourse(course,'response');
  }
}
