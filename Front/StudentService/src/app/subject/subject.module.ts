import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfesorSubjectComponent } from './profesor-subject/profesor-subject.component';
import { ExamRegistrationComponent } from './exam-registration/exam-registration.component';
import { AddProfesorComponent } from './add-profesor/add-profesor.component';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ProfessorSubjectListComponent } from './professor-subject-list/professor-subject-list.component';
import {MatCheckboxModule} from '@angular/material/checkbox'


@NgModule({
  declarations: [
    ProfesorSubjectComponent,
    ExamRegistrationComponent,
    AddProfesorComponent,
    ProfessorSubjectListComponent
  ],
  imports: [
    CommonModule,
    MatOptionModule,
    MatSelectModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatSnackBarModule,
    FormsModule,
    MatCheckboxModule
  ]
})
export class SubjectModule { }
