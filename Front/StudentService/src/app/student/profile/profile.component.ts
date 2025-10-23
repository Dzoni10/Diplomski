import { Component } from '@angular/core';
import { StudentProfile } from '../model/StudentProfile.model';
import { StudentService } from '../student.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/auth.service';
import { User } from 'src/app/auth/model/User.model';
import { SubjectFaculty } from 'src/app/subject/model/SubjectFaculty.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  
  displayedColumns: string[] = ['name', 'espb', 'semester', 'field'];
  studentProfile?: StudentProfile;

  constructor(private authService: AuthService,private studentService: StudentService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    const id = currentUser?.userId // ili neki drugi izvor
    if(id===undefined) return;
    this.authService.getFullStudentById(id).subscribe({
        next: (data)=>this.studentProfile=data,
        error:()=> this.snackBar.open('Greska pri ucitavanju studenta',"Close",{duration:3000})
    });
  }


  calculateEspb(passedSubjects: SubjectFaculty[]): number{
    return passedSubjects.reduce((accumulator, subject) => {
    return accumulator + subject.espb;
  }, 0); 
  }


  convertDormitoryStatus(status:string): string{
    switch(status)
    {
      case 'COMPETED':
        return 'KONKURISAO';
      case 'ACCEPTED':
        return 'PRIMLJEN';
      default:
        return 'NIJE KORISNIK'
    }
  }

}
