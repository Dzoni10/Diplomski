import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentMealComponent } from './student-meal.component';

describe('StudentMealComponent', () => {
  let component: StudentMealComponent;
  let fixture: ComponentFixture<StudentMealComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentMealComponent]
    });
    fixture = TestBed.createComponent(StudentMealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
