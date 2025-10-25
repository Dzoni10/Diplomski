import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfesorSubjectComponent } from './profesor-subject.component';

describe('ProfesorSubjectComponent', () => {
  let component: ProfesorSubjectComponent;
  let fixture: ComponentFixture<ProfesorSubjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfesorSubjectComponent]
    });
    fixture = TestBed.createComponent(ProfesorSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
