import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { StudentFaculty } from 'src/app/student/model/StudentFaculty.model';
import { SubjectService } from '../subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProfessorSubjectList } from '../model/ProfessorSubjectList.model';

@Component({
  selector: 'app-professor-subject-list',
  templateUrl: './professor-subject-list.component.html',
  styleUrls: ['./professor-subject-list.component.css']
})
export class ProfessorSubjectListComponent {

  displayedColumns: string[] = [
    'name',
    'espb',
    'year',
    'semester',
    'field',
    'professor'
  ];

  dataSource = new MatTableDataSource<ProfessorSubjectList>([]);
  
  constructor(private subjectService: SubjectService,private matSnackBar: MatSnackBar) {}
    
  ngOnInit(): void {
    this.loadSubjects();
  }

  loadSubjects(){
    this.subjectService.getAllSubjectsWithProfessors().subscribe({
      next: (res) => this.dataSource.data = res,
      error: () => this.matSnackBar.open('Greška pri učitavanju predmeta', 'Zatvori', { duration: 3000 })
    });
  }

}
