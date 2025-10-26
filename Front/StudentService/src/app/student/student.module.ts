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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StudentMealComponent } from './student-meal/student-meal.component';
import { FinanceCardComponent } from './finance-card/finance-card.component';
import { DormitoryPaymentComponent } from './dormitory-payment/dormitory-payment.component';
import { MatInputModule } from '@angular/material/input';


@NgModule({
  declarations: [
    StudentDormitoryAccessComponent,
    ProfileComponent,
    FacultyComponent,
    StudentMealComponent,
    FinanceCardComponent,
    DormitoryPaymentComponent
  ],
  imports: [
    CommonModule,
    MatSnackBarModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatTableModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule
  ]
})
export class StudentModule { }
