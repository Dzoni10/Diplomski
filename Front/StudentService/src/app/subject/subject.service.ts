import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Observable } from 'rxjs';
import { SubjectFaculty } from './model/SubjectFaculty.model';
import { Professor } from './model/Professor.model';
import { ProfessorSubjectList } from './model/ProfessorSubjectList.model';
import { StudentExamInfo } from './model/StudentExamInfo.model';
import { ExamRegistration } from './model/ExamRegistration.model';
import { StudentExam } from '../student/model/StudentExam.model';

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

getStudentInfo(studentId: number): Observable<StudentExamInfo> {
    return this.http.get<StudentExamInfo>(`${this.apiUrl}/exams/info/${studentId}`,{ headers: this.getAuthHeaders() });
}

getAvailableSubjects(studentId: number): Observable<ExamRegistration[]> {
    return this.http.get<ExamRegistration[]>(`${this.apiUrl}/exams/available/${studentId}`,{ headers: this.getAuthHeaders() });
}

registerExams(studentId: number, subjectIds: number[]): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/exams/register/${studentId}`, subjectIds,{ headers: this.getAuthHeaders() });
}

unregisterExams(studentId: number, subjectIds: number[]): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/exams/unregister/${studentId}`, subjectIds,{ headers: this.getAuthHeaders() });
}

getProfessorSubjects(professorId: number): Observable<SubjectFaculty[]> {
    return this.http.get<SubjectFaculty[]>(`${this.apiUrl}/professor/subjects/${professorId}`,{ headers: this.getAuthHeaders() });
}

getRegisteredStudents(subjectId: number): Observable<StudentExam[]> {
    return this.http.get<StudentExam[]>(`${this.apiUrl}/professor/subject/students/${subjectId}`,{ headers: this.getAuthHeaders() });
}

confirmGrade(req:{studentId: number, subjectId: number, points: number, grade: number}): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/professor/grade`,req, {headers:this.getAuthHeaders()});
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
