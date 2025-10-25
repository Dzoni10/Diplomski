import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorSubjectListComponent } from './professor-subject-list.component';

describe('ProfessorSubjectListComponent', () => {
  let component: ProfessorSubjectListComponent;
  let fixture: ComponentFixture<ProfessorSubjectListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfessorSubjectListComponent]
    });
    fixture = TestBed.createComponent(ProfessorSubjectListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
