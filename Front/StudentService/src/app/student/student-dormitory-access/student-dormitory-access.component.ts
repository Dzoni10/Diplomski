import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import { StudentDormitory } from '../model/StudentDormitory.model';
import { StudentService } from '../student.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-student-dormitory-access',
  templateUrl: './student-dormitory-access.component.html',
  styleUrls: ['./student-dormitory-access.component.css']
})
export class StudentDormitoryAccessComponent implements OnInit {


  displayedColumns: string[] = [
    'name',
    'surname',
    'index',
    'email',
    'year',
    'budget',
    'averageGrade',
    'studyType',
    'dormitoryStatus',
    'actions'
  ];

  dataSource = new MatTableDataSource<StudentDormitory>([]);

  constructor(private studentService: StudentService,private matSnackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents() {
    this.studentService.getAllStudents().subscribe({
      next: (students) => {
        students.sort((a, b) => b.averageGrade - a.averageGrade);
        this.dataSource.data = students;
      },
      error: (err) => 
      {
        console.error('Error during loading student', err);
        this.matSnackBar.open("Nije moguce dobaviti studente","Close",{duration:3000,horizontalPosition:'center'});
      }
    });
  }

  changeStatus(student: StudentDormitory, newStatus: string){

      student.dormitoryStatus=newStatus;

    this.studentService.changeDormitoryAccess(student).subscribe({
      next: ()=>{
        console.log("Success change status");
        this.matSnackBar.open("Status promenjen","Close",{duration:3000,horizontalPosition:'center'});
        this.loadStudents();
      },
      error:(err)=>{
        console.error("Error during change status",err);
        this.matSnackBar.open("Nije moguce promeniti status","Close",{duration:3000,horizontalPosition:'center'});
      }
    });
  }

  convertStatus(status:string): string{
    
    switch(status){
      case "COMPETED":
        return "KONKURISAO";
      case "ACCEPTED":
        return "PRIHVACENO";
      case "REJECTED":
        return "ODBIJENO";
      default:
        return "NIJE KONKURISAO";
    }
  }

}
