import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseModule } from '../course/course.module';

const routes: Routes = [
  { path: 'course', loadChildren: () => import('../course/course.module').then(m => m.CourseModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
