import { NgModule } from '@angular/core';
import { CourseComponentComponent } from '../../componenets/course-component/course-component.component';
import { GetCourseComponent } from '../../componenets/course-component/get-component/get-course/get-course.component';
import { RouterModule, Routes } from '@angular/router';



const routes: Routes = [{ path: '', redirectTo: 'add', pathMatch: 'full' },
{ path: 'add', component: CourseComponentComponent },
{ path: 'getcourse/:nomCourse/:numCourse', component: GetCourseComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CourseRoutingModule { }
