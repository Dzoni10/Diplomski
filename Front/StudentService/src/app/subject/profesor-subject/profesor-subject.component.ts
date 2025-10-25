import { Component, OnInit } from '@angular/core';
import { SubjectService } from '../subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profesor-subject',
  templateUrl: './profesor-subject.component.html',
  styleUrls: ['./profesor-subject.component.css']
})
export class ProfesorSubjectComponent implements OnInit {

  displayedColumns: string[] = [
    'name',
    'espb',
    'semester',
    'year',
    'field',
    'actions'
  ];

  constructor(private studentService: SubjectService,private matSnackBar: MatSnackBar) {}


  ngOnInit(): void {
    this.loadSubjects();
  }

 loadSubjects(){
  
 }

}
