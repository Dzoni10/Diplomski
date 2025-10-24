import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentDormitoryAccessComponent } from './student-dormitory-access/student-dormitory-access.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { ProfileComponent } from './profile/profile.component';
import { FacultyComponent } from './faculty/faculty.component';
import { MatSelectModule} from '@angular/material/select'
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    StudentDormitoryAccessComponent,
    ProfileComponent,
    FacultyComponent
  ],
  imports: [
    CommonModule,
    MatSnackBarModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatTableModule,
    MatSelectModule,
    FormsModule
  ]
})
export class StudentModule { }
