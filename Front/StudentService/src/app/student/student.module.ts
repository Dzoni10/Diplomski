import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentDormitoryAccessComponent } from './student-dormitory-access/student-dormitory-access.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';



@NgModule({
  declarations: [
    StudentDormitoryAccessComponent
  ],
  imports: [
    CommonModule,
    MatSnackBarModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatTableModule
  ]
})
export class StudentModule { }
