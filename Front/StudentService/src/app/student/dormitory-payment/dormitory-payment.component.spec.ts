import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DormitoryPaymentComponent } from './dormitory-payment.component';

describe('DormitoryPaymentComponent', () => {
  let component: DormitoryPaymentComponent;
  let fixture: ComponentFixture<DormitoryPaymentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DormitoryPaymentComponent]
    });
    fixture = TestBed.createComponent(DormitoryPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
