import { Component, OnInit } from '@angular/core';
import { StudentMeal } from '../model/StudentMeal.model';
import { StudentService } from '../student.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/auth.service';

type MealType = 'breakfast' | 'lunch' | 'dinner';

@Component({
  selector: 'app-student-meal',
  templateUrl: './student-meal.component.html',
  styleUrls: ['./student-meal.component.css']
})
export class StudentMealComponent implements OnInit {
  student!: StudentMeal;

  mealCounts: Record<MealType, number> = {
    breakfast: 0,
    lunch: 0,
    dinner: 0
  };

  readonly meals = ['breakfast', 'lunch', 'dinner'] as const;

  constructor(private authService:AuthService, private studentService: StudentService, private matSnackBar: MatSnackBar) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    const id = currentUser?.userId 
    if (id === undefined) return;
    this.studentService.getStudentMealInfo(id).subscribe({
      next: (res) => (this.student = res),
      error: () => this.matSnackBar.open('Greška pri učitavanju podataka', 'Zatvori', { duration: 3000 }),
    });
  }

  getPrice(meal: 'breakfast' | 'lunch' | 'dinner'): number {
    const budget = this.student.budget;
    switch (meal) {
      case 'breakfast':
        return budget ? 56 : 112;
      case 'lunch':
        return budget ? 120 : 300;
      case 'dinner':
        return budget ? 90 : 250;
      default:
        return 0;
    }
  }

  increment(meal: 'breakfast' | 'lunch' | 'dinner') {
    if (this.mealCounts[meal] < 30) this.mealCounts[meal]++;
  }

  decrement(meal: 'breakfast' | 'lunch' | 'dinner') {
    if (this.mealCounts[meal] > 0) this.mealCounts[meal]--;
  }

  buyMeals() {
    const total =
      this.mealCounts.breakfast * this.getPrice('breakfast') +
      this.mealCounts.lunch * this.getPrice('lunch') +
      this.mealCounts.dinner * this.getPrice('dinner');

    if (total > this.student.money) {
      this.matSnackBar.open('Nema dovoljno sredstava za kupovinu', 'Zatvori', { duration: 3000 });
      return;
    }

    this.student.money -= total;
    this.student.breakfast += this.mealCounts.breakfast;
    this.student.lunch += this.mealCounts.lunch;
    this.student.dinner += this.mealCounts.dinner;

    this.studentService.changeMealNumber(this.student).subscribe({
      next: ()=>{
        console.log("Success change meal number");
        const id = this.authService.getCurrentUser()?.userId;
        if (id) {
          this.studentService.getStudentMealInfo(id).subscribe(res => this.student = res);
        }
        this.mealCounts = { breakfast: 0, lunch: 0, dinner: 0 };
      },
      error:(err)=>{
        console.error("Error during change meal number",err);
        this.matSnackBar.open("Nije moguce kupiti obroke","Close",{duration:3000,horizontalPosition:'center'});
      }
    });

    this.matSnackBar.open('Uspešno ste kupili obroke!', 'Zatvori', { duration: 3000 });
  }



  isMealDisabled(meal: 'breakfast' | 'lunch' | 'dinner'): boolean {
  if (!this.student) return true; // ako student još nije učitan

  switch (meal) {
    case 'breakfast':
      return this.student.breakfast + this.mealCounts.breakfast >= 30;
    case 'lunch':
      return this.student.lunch + this.mealCounts.lunch >= 30;
    case 'dinner':
      return this.student.dinner + this.mealCounts.dinner >= 30;
    default:
      return false;
  }
}

}
