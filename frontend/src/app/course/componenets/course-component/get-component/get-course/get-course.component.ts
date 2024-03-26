import { Component } from '@angular/core';
import { CourseserviceService } from '../../../../services/courseservice.service';
import { Course, Partant } from '../../../../../../generated-api-client/mvp/courses-api';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import {MatTableModule} from '@angular/material/table';

@Component({
  selector: 'app-get-course',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './get-course.component.html',
  styleUrl: './get-course.component.scss'
})
export class GetCourseComponent {

  public course: Course = {};
  displayedColumns: string[] = ['Nom', 'Numero'];
  public partants: Partant[] = [];

  constructor(private courseService: CourseserviceService, private route: ActivatedRoute) {}
/**
 * ngOnInit is called after the constructor and called  after the first ngOnChanges() 
 * ngOnInit is called once after the first ngOnChanges() 
 */
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.courseService.getCourseByNameAndNum(params['nomCourse'], params['numCourse']).subscribe(value => {
        this.course = value;
        if (this.course.partants?.length) {
          this.partants = [...this.course.partants];
        }
      })
    });
  }
}
