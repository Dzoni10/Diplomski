import { SubjectFaculty } from "src/app/subject/model/SubjectFaculty.model";

export interface StudentProfile{

  name: string;
  surname: string;
  index: string;
  email: string;
  year: number;
  budget: boolean;
  averageGrade: number;
  dormitoryStatus: string;
  passedSubjects: SubjectFaculty[];

}