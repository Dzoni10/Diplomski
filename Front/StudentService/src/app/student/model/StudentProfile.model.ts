import { PassedSubjectWithGrade } from "src/app/subject/model/PassedSubjectWithGrade.model";
import { SubjectFaculty } from "src/app/subject/model/SubjectFaculty.model";

export interface StudentProfile{

  name: string;
  surname: string;
  index: string;
  email: string;
  year: number;
  budget: boolean;
  averageGrade: number;
  studyType:string;
  dormitoryStatus: string;
  passedSubjects: PassedSubjectWithGrade[];
}