import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SubjectService } from '../subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-profesor',
  templateUrl: './add-profesor.component.html',
  styleUrls: ['./add-profesor.component.css']
})
export class AddProfesorComponent {

  form!:FormGroup;
  professors: any[]=[];
  subjects: any[]=[];

  constructor(private fb: FormBuilder,private subjectService:SubjectService,private matSnackBar:MatSnackBar){}

  ngOnInit(): void {
    this.form = this.fb.group({
      professorId: ['', Validators.required],
      subjectId: ['', Validators.required]
    });

    this.loadProfessors();
    this.loadSubjects();
  }

  loadProfessors() {
    this.subjectService.getAllProfessors().subscribe({
      next: (res) => (this.professors = res),
      error: () => this.matSnackBar.open('Greška pri učitavanju profesora', 'Zatvori', { duration: 3000 })
    });
  }

  loadSubjects() {
    this.subjectService.getAllSubjects().subscribe({
      next: (res) => (this.subjects = res),
      error: () => this.matSnackBar.open('Greška pri učitavanju predmeta', 'Zatvori', { duration: 3000 })
    });
  }

  assign() {
    if (this.form.invalid) return;

    this.subjectService.assignProfessorToSubject(this.form.value).subscribe({
      next: (res) => {
        this.matSnackBar.open(res.message, 'Zatvori', { duration: 3000 });
        this.form.reset();
      },
      error: (err) => {
        const msg = err.error?.message || 'Profesor već dodeljen ovom predmetu';
        this.matSnackBar.open(msg, 'Zatvori', { duration: 3000 });
      }
    });
  }
}
