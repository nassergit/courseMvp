import { AfterViewChecked, Component, OnInit } from '@angular/core';
import { Course, CreateCourse200Response } from '../../../../generated-api-client/mvp/courses-api';
import { CommonModule } from '@angular/common';
import { CourseserviceService } from '../../services/courseservice.service';
import { HttpClientModule, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { catchError, tap, throwError } from 'rxjs';
import { FormArray, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-course-component',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './course-component.component.html',
  styleUrl: './course-component.component.scss'
})
export class CourseComponentComponent implements OnInit, AfterViewChecked {

  private formgroupPartant={
    nom: ['', Validators.required],
    numero: [0, Validators.required]
  };

  public course: Course = {};
  public courseForm = this.formBuilder.group({
    date: [new Date(), Validators.required],
    nom: ['', Validators.required],
    numero:[0, Validators.required],
    partants: this.formBuilder.array([this.formBuilder.group(this.formgroupPartant)],
     [Validators.required, Validators.minLength(3)])
  });

  
  constructor(private formBuilder: FormBuilder,
     private courseservice: CourseserviceService,
     private router: Router) {
  }
  ngAfterViewChecked(): void {
  }

  ngOnInit(): void {
  }

  get partants() {
    return this.courseForm.get('partants') as FormArray;
  }

  addPartant() {
    this.partants.push(this.formBuilder.group(this.formgroupPartant));
  }

  submitForm() {
    if (this.courseForm.valid) {
      this.course = Object.assign(this.course, this.courseForm.value);
      this.courseservice.createCourse(this.course).subscribe(
        (response: HttpResponse<CreateCourse200Response>) => {
            this.partants.clear();
            this.courseForm.reset();
            this.redirectTo('getcourse/' + this.course.nom + '/' + this.course.numero);
        }
      );
    }
  }

  redirectTo(uri: string) {
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
      this.router.navigate([uri])); 
  }
}


