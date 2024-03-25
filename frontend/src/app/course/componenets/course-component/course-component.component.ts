import { AfterViewChecked, Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Course } from '../../../../generated-api-client/mvp/courses-api';
import { CommonModule } from '@angular/common';
import { CourseserviceService } from '../../services/courseservice.service';

@Component({
  selector: 'app-course-component',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
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

  
  constructor(private formBuilder: FormBuilder, private courseservice: CourseserviceService) {
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
    this.course = Object.assign(this.course, this.courseForm.value);
    this.courseservice.createCourse(this.course).subscribe(value=>{
      console.log(value);
    });
    console.log(this.courseForm.value);
  }

}
