import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentDormitoryAccessComponent } from './student-dormitory-access.component';

describe('StudentDormitoryAccessComponent', () => {
  let component: StudentDormitoryAccessComponent;
  let fixture: ComponentFixture<StudentDormitoryAccessComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentDormitoryAccessComponent]
    });
    fixture = TestBed.createComponent(StudentDormitoryAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
