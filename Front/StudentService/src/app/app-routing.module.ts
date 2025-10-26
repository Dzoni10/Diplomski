import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { StudentDormitoryAccessComponent } from './student/student-dormitory-access/student-dormitory-access.component';
import { ProfileComponent } from './student/profile/profile.component';
import { FacultyComponent } from './student/faculty/faculty.component';
import { ProfesorSubjectComponent } from './subject/profesor-subject/profesor-subject.component';
import { ExamRegistrationComponent } from './subject/exam-registration/exam-registration.component';
import { AddProfesorComponent } from './subject/add-profesor/add-profesor.component';
import { ProfessorSubjectListComponent } from './subject/professor-subject-list/professor-subject-list.component';
import { StudentMealComponent } from './student/student-meal/student-meal.component';

const routes: Routes = [
   { path: 'login', component: LoginComponent },
   { path: 'signup', component: SignupComponent},
   { path: 'student-dormitory-access', component: StudentDormitoryAccessComponent},
   { path: 'student-profile', component: ProfileComponent},
   { path: 'student-faculty', component: FacultyComponent},
   { path: 'profesor-subject', component: ProfesorSubjectComponent},
   { path: 'exam-registration', component: ExamRegistrationComponent},
   { path: 'add-profesor-subject', component: AddProfesorComponent},
   { path: 'profesor-subject-list', component: ProfessorSubjectListComponent},
   { path: 'student-meal', component: StudentMealComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
