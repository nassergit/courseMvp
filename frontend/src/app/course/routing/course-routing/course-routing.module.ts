import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseComponentComponent } from '../../componenets/course-component/course-component.component';



const routes: Routes = [{ path: '', redirectTo: 'get-course', pathMatch: 'full' },
{ path: 'get-course', component: CourseComponentComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CourseRoutingModule { }
