import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { StudentDormitoryAccessComponent } from './student/student-dormitory-access/student-dormitory-access.component';

const routes: Routes = [
   { path: 'login', component: LoginComponent },
   { path: 'signup', component: SignupComponent},
   { path: 'student-dormitory-access', component: StudentDormitoryAccessComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
