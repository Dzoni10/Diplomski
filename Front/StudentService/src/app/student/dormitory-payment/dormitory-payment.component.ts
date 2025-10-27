import { Component, OnInit } from '@angular/core';
import { StudentDormitoryPayment } from '../model/StudentDormitoryPayment.model';
import { StudentService } from '../student.service';
import { AuthService } from 'src/app/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dormitory-payment',
  templateUrl: './dormitory-payment.component.html',
  styleUrls: ['./dormitory-payment.component.css']
})
export class DormitoryPaymentComponent implements OnInit {

  student!: StudentDormitoryPayment;
  readonly homePrice = 2150;

  constructor(private studentService: StudentService,private authService:AuthService,private matSnackBar: MatSnackBar){}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    const id = currentUser?.userId;
    if(!id) return;

    this.studentService.getStudentDormitoryPayment(id).subscribe({
      next:(res) => this.student=res,
      error:()=> this.matSnackBar.open('Greska pri ucitavanju podataka',"Close",{duration:3000, horizontalPosition:'center'})
    });
  }

  payDormitory(): void{
    if(!this.student) return;

    if(this.student.money<this.homePrice){
      this.matSnackBar.open("Nemate dovoljno raspolozivih sredstava","Close",{duration:3000,horizontalPosition:'center'});
      return;
    }

    this.student.money-=this.homePrice;
    this.student.payed=true;

    this.studentService.payDorm(this.student).subscribe({
      next:()=>{
        this.matSnackBar.open("Uspesno ste platili dom","Close",{duration:3000,horizontalPosition:'center'});
      },
      error:(err)=>{
        this.matSnackBar.open("Doslo je do greske prilikom placanja doma","Close",{duration:3000,horizontalPosition:'center'});
      }
    });
  }
}
