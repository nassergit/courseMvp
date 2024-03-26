import { Component } from '@angular/core';
import { CourseserviceService } from '../../../../services/courseservice.service';
import { Course } from '../../../../../../generated-api-client/mvp/courses-api';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-get-course',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './get-course.component.html',
  styleUrl: './get-course.component.scss'
})
export class GetCourseComponent {

  public course: Course = {};

  constructor(private courseService: CourseserviceService, private route: ActivatedRoute) {}
/**
 * ngOnInit is called after the constructor and called  after the first ngOnChanges() 
 * ngOnInit is called once after the first ngOnChanges() 
 */
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.courseService.getCourseByNameAndNum(params['nomCourse'], params['numCourse']).subscribe(value => {
        this.course = value;
      })
    });
  }
}
