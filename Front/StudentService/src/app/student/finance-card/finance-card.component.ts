import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudentService } from '../student.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/auth.service';
import { StudentDeposit } from '../model/StudentDeposit.mode';

@Component({
  selector: 'app-finance-card',
  templateUrl: './finance-card.component.html',
  styleUrls: ['./finance-card.component.css']
})
export class FinanceCardComponent implements OnInit {

  form!:FormGroup
  studentDeposit!:StudentDeposit;

  constructor(private fb:FormBuilder,private studentService:StudentService,private snackBar:MatSnackBar,private authService:AuthService){}

  ngOnInit(): void {
    this.form=this.fb.group({
      accountNumber: ['',[Validators.required,Validators.minLength(5)]],
      pin:['',[Validators.required,Validators.pattern(/^\d{4}$/)]],
      amount:[0,[Validators.required,Validators.min(1)]]
    });

    const userId = this.authService.getCurrentUser()?.userId;

    if(!userId) return;

    this.studentService.getCurrentAmount(userId).subscribe({
      next: data => this.studentDeposit = data,
      error: err => console.error(err)
    });
  }

  deposit(){
    if(this.form.invalid) return;

    const user = this.authService.getCurrentUser();
    const studentId = user?.userId;

    if(!studentId){
      this.snackBar.open('Student nije pronadjen',"Close",{duration:3000});
      return;
    }

    this.studentDeposit.money += this.form.value.amount;

    this.studentService.depositMoney(this.studentDeposit).subscribe({
      next:(res)=>{
        this.snackBar.open('Uspesno izvrsena transakcija','Close',{duration:3000});
        this.form.reset();
      }, error: (err)=>{
        const msg = err.error?.message || "Greska pri prenosu novca";
        this.snackBar.open(msg,"Close",{duration:3000});
      }
    })
  }

}
