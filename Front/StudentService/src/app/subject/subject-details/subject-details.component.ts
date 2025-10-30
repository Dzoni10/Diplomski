import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SubjectService } from '../subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-subject-details',
  templateUrl: './subject-details.component.html',
  styleUrls: ['./subject-details.component.css']
})
export class SubjectDetailsComponent implements OnInit {

  students: any[]=[];
  subjectId!:number;

  constructor(private route: ActivatedRoute,private subjectService:SubjectService,private matSnackBar:MatSnackBar){}

  ngOnInit(): void {
    this.subjectId = +this.route.snapshot.paramMap.get('id')!;
    this.loadStudents();
  }

  loadStudents() {
    this.subjectService.getRegisteredStudents(this.subjectId)
      .subscribe(data => this.students = data.map(s => ({ ...s, points: null, grade: null })));
  }

  canConfirm(s: any): boolean {
    return s.points != null && s.grade != null;
  }

  confirm(s: any) {
    this.subjectService.confirmGrade({
      studentId: s.studentId,
      subjectId: this.subjectId,
      points: s.points,
      grade: s.grade
    }).subscribe(() => {
      this.matSnackBar.open('Ocena upisana', 'Close', { duration: 2000 });
      this.loadStudents();
    });
  }
}
