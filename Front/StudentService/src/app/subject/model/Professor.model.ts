import { SubjectFaculty } from "./SubjectFaculty.model";

export interface Professor {
  name: string;
  surname: string;
  email: string;
  subjects: SubjectFaculty[];
}
