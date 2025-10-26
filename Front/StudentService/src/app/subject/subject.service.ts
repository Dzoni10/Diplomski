import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Observable } from 'rxjs';
import { SubjectFaculty } from './model/SubjectFaculty.model';
import { Professor } from './model/Professor.model';
import { ProfessorSubjectList } from './model/ProfessorSubjectList.model';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient,private authService: AuthService) { }

  getAllSubjectsWithProfessors(): Observable<ProfessorSubjectList[]> {
  return this.http.get<ProfessorSubjectList[]>(`${this.apiUrl}/subject/with-professors`, { headers: this.getAuthHeaders() });
}

  assignProfessorToSubject(dto: any): Observable<any> {
  return this.http.post(`${this.apiUrl}/professorSubject/assign`, dto, {
    headers: this.getAuthHeaders(),
    responseType: 'json'
  });
}

getAllProfessors(): Observable<Professor[]> {
  return this.http.get<Professor[]>(`${this.apiUrl}/professor/professors`, { headers: this.getAuthHeaders() });
}

getAllSubjects(): Observable<SubjectFaculty[]> {
  return this.http.get<SubjectFaculty[]>(`${this.apiUrl}/subject/subjects`, { headers: this.getAuthHeaders() });
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
