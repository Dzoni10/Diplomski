import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { StudentDormitoryAccessComponent } from './student/student-dormitory-access/student-dormitory-access.component';
import { ProfileComponent } from './student/profile/profile.component';
import { FacultyComponent } from './student/faculty/faculty.component';

const routes: Routes = [
   { path: 'login', component: LoginComponent },
   { path: 'signup', component: SignupComponent},
   { path: 'student-dormitory-access', component: StudentDormitoryAccessComponent},
   { path: 'student-profile', component: ProfileComponent},
   { path: 'student-faculty', component: FacultyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
