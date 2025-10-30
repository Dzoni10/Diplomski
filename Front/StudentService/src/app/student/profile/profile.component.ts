import { Component } from '@angular/core';
import { StudentProfile } from '../model/StudentProfile.model';
import { StudentService } from '../student.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/auth.service';
import { PassedSubjectWithGrade } from 'src/app/subject/model/PassedSubjectWithGrade.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  
  displayedColumns: string[] = ['subjectName', 'espb', 'year','semester','points', 'grade','professorName'];
  studentProfile?: StudentProfile;
  totalEspb: number = 0;
  

  constructor(private authService: AuthService,private studentService: StudentService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    const id = currentUser?.userId // ili neki drugi izvor
    if(id===undefined) return;
    this.authService.getFullStudentById(id).subscribe({
        next: (data)=>{
          this.studentProfile=data
          this.totalEspb = this.calculateEspb(this.studentProfile.passedSubjects);
        },
        error:()=> this.snackBar.open('Greska pri ucitavanju studenta',"Close",{duration:3000})
    });

    
  }

  calculateEspb(passedSubjects: PassedSubjectWithGrade[]): number{
    if (!passedSubjects || passedSubjects.length === 0) {
      return 0;
    }
    return passedSubjects.reduce((sum, subject) => sum + subject.espb, 0);
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

  convertStudyType(studyType:string):string{
    switch(studyType){
      case 'OAS':
        return "OAS (Osnovne akademske studije)";
      case 'MAS':
        return "MAS (Master studije)";
      case 'PHD':
        return 'PHD (Doktorske studije)';
      default:
        return 'nedefinisano';
    }
  }



}
