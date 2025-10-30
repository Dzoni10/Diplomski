import { Component, OnInit } from '@angular/core';
import { SubjectService } from '../subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-profesor-subject',
  templateUrl: './profesor-subject.component.html',
  styleUrls: ['./profesor-subject.component.css']
})
export class ProfesorSubjectComponent implements OnInit {

  subjects: any[]=[];
  displayedColumns: string[] = [
    'name',
    'espb',
    'semester',
    'year',
    'field',
    'details'
  ];

  constructor(private subjectService: SubjectService,private matSnackBar: MatSnackBar,private router: Router, private authService:AuthService) {}


  ngOnInit(): void {
    const prof = this.authService.getCurrentUser();
    if(!prof) return;
    this.subjectService.getProfessorSubjects(prof?.userId).subscribe(data => this.subjects = data);
  }

  goToDetails(id: number) {
    this.router.navigate(['subject-details', id]);
  }

}
