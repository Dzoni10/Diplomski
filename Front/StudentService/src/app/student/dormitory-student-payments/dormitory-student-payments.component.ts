import { Component } from '@angular/core';
import { StudentDormitoryPayment } from '../model/StudentDormitoryPayment.model';
import { StudentService } from '../student.service';

@Component({
  selector: 'app-dormitory-student-payments',
  templateUrl: './dormitory-student-payments.component.html',
  styleUrls: ['./dormitory-student-payments.component.css']
})
export class DormitoryStudentPaymentsComponent {
  students: StudentDormitoryPayment[]=[];
  displayedColumns: string[]=['name','surname','email','year','studyType','payed'];

  constructor(private studentService: StudentService){}

  ngOnInit(): void{
    this.loadStudents();
  }

  loadStudents(){
    this.studentService.getAllStudentDormitoryPayment().subscribe({
      next:(data)=>this.students=data,
      error:(err)=>console.error(err)
    });
  }

  markAsPaid(id: number) {
    // this.studentService.payDorm(id).subscribe({
    //   next: () => this.loadStudents()
    // });
  }

  resetPayments() {
    this.studentService.resetDormPayments().subscribe({
      next: () => this.loadStudents()
    });
  }


}
