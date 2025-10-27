import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DormitoryStudentPaymentsComponent } from './dormitory-student-payments.component';

describe('DormitoryStudentPaymentsComponent', () => {
  let component: DormitoryStudentPaymentsComponent;
  let fixture: ComponentFixture<DormitoryStudentPaymentsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DormitoryStudentPaymentsComponent]
    });
    fixture = TestBed.createComponent(DormitoryStudentPaymentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
