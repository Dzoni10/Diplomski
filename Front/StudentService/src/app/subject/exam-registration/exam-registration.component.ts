import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SubjectService } from '../subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/auth.service';
import { ExamRegistration } from '../model/ExamRegistration.model';

@Component({
  selector: 'app-exam-registration',
  templateUrl: './exam-registration.component.html',
  styleUrls: ['./exam-registration.component.css']
})
export class ExamRegistrationComponent implements OnInit {
  
  student: any=[];
  availableSubjects: any[]=[];
  registeredSubjects: any[]=[];

  displayedColumnsAvailable = ['select', 'name','espb','year', 'professor', ];
  displayedColumnsRegistered = ['select', 'name','espb','year', 'professor'];

  constructor(private authService:AuthService,private subjectService:SubjectService,private matSnackBar:MatSnackBar){}
  
  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    const studentId = currentUser?.userId;
    if(!studentId) return;
    this.loadStudent(studentId);
    this.loadSubjects(studentId);
  }

  loadStudent(id: number) {
    this.subjectService.getStudentInfo(id).subscribe(data => this.student = data);
  }

  loadSubjects(id: number) {
    this.subjectService.getAvailableSubjects(id).subscribe(data => {
      this.availableSubjects = data.filter(s => !s.registered);
      this.registeredSubjects = data.filter(s => s.registered);
    });
  }

  hasSelected(list: ExamRegistration[]): boolean {
    return list.some(s => s.selected);
  }

   registerExams() {
    const currentUser = this.authService.getCurrentUser();
    const studentId = currentUser?.userId;
    if(!studentId) return;
    const ids = this.availableSubjects.filter(s => s.selected).map(s => s.subjectId);
    if (ids.length === 0) return;
    this.subjectService.registerExams(studentId, ids).subscribe({next:() => {
      this.moveSubjects(this.availableSubjects, this.registeredSubjects);
      this.loadStudent(studentId);
      this.loadSubjects(studentId);
    },error: (err)=>{
        this.matSnackBar.open("Nema dovoljno sredstava za placanje","Close",{duration:3000, horizontalPosition:'center'});
    }});
  }

  unregisterExams() {
    const currentUser = this.authService.getCurrentUser();
    const studentId = currentUser?.userId;
    if(!studentId) return;
    const ids = this.registeredSubjects.filter(s => s.selected).map(s => s.subjectId);
    if (ids.length === 0) return;
    this.subjectService.unregisterExams(studentId, ids).subscribe(() => {
      this.moveSubjects(this.registeredSubjects, this.availableSubjects);
      this.loadStudent(studentId);
      this.loadSubjects(studentId);
    });
  }

  private moveSubjects(from: ExamRegistration[], to: ExamRegistration[]) {
    const moved = from.filter(s => s.selected);
    to.push(...moved.map(s => ({ ...s, selected: false })));
    from.splice(0, from.length, ...from.filter(s => !s.selected));
  }


}
