import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinanceCardComponent } from './finance-card.component';

describe('FinanceCardComponent', () => {
  let component: FinanceCardComponent;
  let fixture: ComponentFixture<FinanceCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FinanceCardComponent]
    });
    fixture = TestBed.createComponent(FinanceCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
