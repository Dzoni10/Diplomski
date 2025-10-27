export interface ExamRegistration {
  subjectId: number;
  subjectName: string;
  espb: number;
  year: number;
  field: string;
  professorName: string;
  professorSurname: string;
  professorEmail: string;
  registered: boolean;
  selected?: boolean; 
}
