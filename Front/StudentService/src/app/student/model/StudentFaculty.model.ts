import { SubjectFaculty } from "src/app/subject/model/SubjectFaculty.model";

export interface StudentFaculty{
  name: string;
  surname: string;
  index: string;
  email: string;
  year: number;
  budget: boolean;
  money: number;
  averageGrade: number;
  studyType: string;
  passedSubjects: SubjectFaculty[];
}