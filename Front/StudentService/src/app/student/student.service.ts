import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { StudentDormitory } from './model/StudentDormitory.model';
import { AuthService } from '../auth/auth.service';
import { SubjectFaculty } from '../subject/model/SubjectFaculty.model';
import { StudentProfile } from './model/StudentProfile.model';
import { StudentFaculty } from './model/StudentFaculty.model';
import { StudentMeal } from './model/StudentMeal.model';
import { StudentDeposit } from './model/StudentDeposit.mode';
import { StudentDormitoryPayment } from './model/StudentDormitoryPayment.model';
@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/api/student';

  constructor(private http: HttpClient,private authService: AuthService) { }

getStudentMealInfo(studentId: number): Observable<StudentMeal> {
  return this.http.get<StudentMeal>(`${this.apiUrl}/meal-info/${studentId}`, {
    headers: this.getAuthHeaders()
  });
}

getAllFacultyStudents(): Observable<StudentFaculty[]> {
  return this.http.get<StudentFaculty[]>(`${this.apiUrl}/faculty/students`,{headers:this.getAuthHeaders()});
}

changeStudentStatus(change: StudentFaculty): Observable<any> {
  return this.http.put(`${this.apiUrl}/changeFacultyStatus`,change,{headers:this.getAuthHeaders()});
}

getAllStudents(): Observable<StudentDormitory[]> {
  return this.http.get<StudentDormitory[]>(`${this.apiUrl}/dormitory`,{headers:this.getAuthHeaders()});
}

changeDormitoryAccess(change: StudentDormitory): Observable<any> {
  return this.http.put(`${this.apiUrl}/changeStatus`,change,{headers:this.getAuthHeaders()});
}

getUnpassedSubjects(email: string): Observable<SubjectFaculty[]> {
  return this.http.get<SubjectFaculty[]>(`${this.apiUrl}/unpassed/${email}`,{headers:this.getAuthHeaders()});
}

getStudentProfile(email: string): Observable<StudentProfile> {
  return this.http.get<StudentProfile>(`${this.apiUrl}/profile/${email}`,{headers:this.getAuthHeaders()});
}

changeMealNumber(change: StudentMeal):Observable<any>{
  return this.http.put(`${this.apiUrl}/changeMealNumber`,change,{headers:this.getAuthHeaders()});
}

depositMoney(data: StudentDeposit){
  return this.http.post<StudentDeposit>(`${this.apiUrl}/deposit`,data,{headers:this.getAuthHeaders()});
}

getCurrentAmount(id: number): Observable<StudentDeposit>{
  return this.http.get<StudentDeposit>(`${this.apiUrl}/getCurrentDeposit/${id}`,{headers:this.getAuthHeaders()});
}

getAllStudentDormitoryPayment(): Observable<StudentDormitoryPayment[]> {
  return this.http.get<StudentDormitoryPayment[]>(`${this.apiUrl}/dorm-payments`,{headers:this.getAuthHeaders()});
}

resetDormPayments(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/reset-dormitory-payments`, {},{headers:this.getAuthHeaders()});
}


private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }
}
