import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import { StudentFaculty } from '../model/StudentFaculty.model';
import { StudentService } from '../student.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSelect } from '@angular/material/select';

@Component({
  selector: 'app-faculty',
  templateUrl: './faculty.component.html',
  styleUrls: ['./faculty.component.css']
})
export class FacultyComponent implements OnInit{


  displayedColumns: string[] = [
    'name',
    'surname',
    'index',
    'email',
    'year',
    'budget',
    'averageGrade',
    'studyType',
    'actions'
  ];

  dataSource = new MatTableDataSource<StudentFaculty>([]);

  constructor(private studentService: StudentService,private matSnackBar: MatSnackBar) {}
  
  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents() {
    this.studentService.getAllFacultyStudents().subscribe({
      next: (students) => {
        students.sort((a, b) => b.averageGrade - a.averageGrade);
        students.forEach(s => (s as any).edited = false);
        this.dataSource.data = students;
      },
      error: (err) => 
      {
        console.error('Error during loading student', err);
        this.matSnackBar.open("Nije moguce dobaviti studente","Close",{duration:3000,horizontalPosition:'center'});
      }
    });
  }

  markAsEdited(student: StudentFaculty) {
    (student as any).edited = true;
  }

  changeStatus(student: StudentFaculty){
    this.studentService.changeStudentStatus(student).subscribe({
      next:()=>{
        this.matSnackBar.open('Izmene sacuvane!','Close',{duration:3000,horizontalPosition:'center'});
        (student as any).edited=false;
      },
      error: (err)=>{
          console.error('Error during loading changes',err);
          this.matSnackBar.open('Greska pri ucitavanju izmena','Close',{duration:3000, horizontalPosition:'center'});
      }
    });
    
  }
  
}
