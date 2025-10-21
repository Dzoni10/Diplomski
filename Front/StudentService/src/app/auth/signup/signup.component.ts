import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import * as zxcvbn from 'zxcvbn'

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  animations: [
    trigger('slideIn', [
      state('void', style({ transform: 'translateY(0)', opacity: 0 })),
      transition(':enter', [
        animate('2.0s ease-out', style({ transform: 'translateY(0)', opacity: 1 }))
      ])
    ])
  ]
})
export class SignupComponent implements OnInit {

  signupForm!: FormGroup;
  passwordStrength: number = 0;
  passwordFeedback: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(50),
        Validators.pattern(/^[A-Za-zČĆŽŠĐčćžšđ]+(?: [A-Za-zČĆŽŠĐčćžšđ]+)*$/)
      ]],
      surname: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(50),
        Validators.pattern(/^[A-Za-zČĆŽŠĐčćžšđ]+(?: [A-Za-zČĆŽŠĐčćžšđ]+)*$/)
      ]],
      email: ['', [
        Validators.required,
        Validators.email,
        Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(128),
        this.passwordStrengthValidator()
      ]],
      password2: ['', Validators.required],
      index: ['', [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(11),
        Validators.pattern(/^(?:[A-Z]{2}|[A-Z][0-9])-(?:[1-9]|[1-9][0-9]|1[0-9]{2}|2[0-3][0-9]|240)-20(1[1-9]|[2-9][0-9])$/)
      ]]
    }, {
      validators: this.passwordMatchValidator
    });
  }


  register(): void {
    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      this.snackBar.open("Ispravite sve greške pre potvrde", "Close", {
        duration: 3000,
        horizontalPosition: "center"
      });
      return;
    }

    const { password2, ...payload } = this.signupForm.value;
    
    this.authService.register(payload).subscribe({
      next: res => {
        console.log("REGISTER SUCCESS");
        this.snackBar.open("Uspešna registracija!", "Close", {
          duration: 3000,
          horizontalPosition: "center"
        });
        this.signupForm.reset();
        this.passwordStrength = 0;
      },
      error: err => {
        if (err.status === 201) {
          this.snackBar.open("Uspešna registracija!", "Close", {
            duration: 3000,
            horizontalPosition: "center"
          });
          this.signupForm.reset();
          this.passwordStrength = 0;
        } else {
          this.snackBar.open("Registracija nije moguća!", "Close", {
            duration: 3000,
            horizontalPosition: "center"
          });
        }
      }
    });
  }

  passwordStrengthValidator() {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value) return null;

      const hasUpperCase = /[A-Z]/.test(value);
      const hasLowerCase = /[a-z]/.test(value);
      const hasNumeric = /[0-9]/.test(value);
      const hasSpecialChar = /[@$!%*?&]/.test(value);

      const passwordValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar;
      return !passwordValid ? { passwordStrength: true } : null;
    };
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const password2 = control.get('password2');
    if (!password || !password2) return null;
    return password.value === password2.value ? null : { passwordMismatch: true };
  }

  checkPasswordStrength() {
    const pwd = this.signupForm.get('password')?.value || '';
    const result = zxcvbn(pwd);
    this.passwordStrength = result.score;
    this.passwordFeedback = result.feedback.suggestions.join(' ');
  }

  getErrorMessage(fieldName: string): string {
    const control = this.signupForm.get(fieldName);
    if (!control || !control.errors || !control.touched) return '';

    if (control.errors['required']) return `Obavezno polje`;
    if (control.errors['minlength']) return `Minimum ${control.errors['minlength'].requiredLength} karatkera`;
    if (control.errors['maxlength']) return `Maximum ${control.errors['maxlength'].requiredLength} karaktera`;
    if (control.errors['email']) return 'Netačan email format';
    if (control.errors['pattern']) {
      if (fieldName === 'name' || fieldName === 'surname') return 'Samo slova su dozvoljena';
      if (fieldName === 'index') return 'Ispravan format smer(XY)-broj(10)-godina(2021)';
      return 'Neispravan format';
    }
    if (control.errors['passwordStrength']) return 'Lozinka mora sadržati velika, mala slova, broj i specijalni karakter (@$!%*?&)';
    return '';
  }


hasError(fieldName: string): boolean {
    const control = this.signupForm.get(fieldName);
    return !!(control && control.invalid && control.touched);
  }

}
