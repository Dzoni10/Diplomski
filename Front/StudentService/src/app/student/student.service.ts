import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudentDormitory } from './model/StudentDormitory.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/api/student';

  constructor(private http: HttpClient,private authService: AuthService) { }

  getAllStudents(): Observable<StudentDormitory[]> {
  return this.http.get<StudentDormitory[]>(`${this.apiUrl}/dormitory`,{headers:this.getAuthHeaders()});
}

changeDormitoryAccess(change: StudentDormitory): Observable<any> {
  return this.http.put(`${this.apiUrl}/changeStatus`,change,{headers:this.getAuthHeaders()});
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
