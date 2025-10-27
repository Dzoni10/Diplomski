package com.example.studentservice.service;

import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.Subject;
import com.example.studentservice.dto.ExamRegistrationDTO;
import com.example.studentservice.dto.StudentExamInfoDTO;
import com.example.studentservice.repositoryInterfaces.StudentRepositoryInterface;
import com.example.studentservice.repositoryInterfaces.SubjectRepositoryInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {
    private final StudentRepositoryInterface studentRepository;
    private final SubjectRepositoryInterface subjectRepository;

    public ExamService(StudentRepositoryInterface studentRepository, SubjectRepositoryInterface subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public StudentExamInfoDTO getStudentInfo(int studentId) {
        Student s = studentRepository.findById(studentId).orElseThrow();
        return new StudentExamInfoDTO(
                s.getName(),
                s.getSurname(),
                s.getIndex(),
                s.getYear(),
                s.getStudyType(),
                s.getAverageGrade(),
                s.getMoney()
        );
    }
    @Transactional(readOnly = true)
    public List<ExamRegistrationDTO> getAvailableSubjects(int studentId) {
        Student s = studentRepository.findById(studentId).orElseThrow();
        String fieldPrefix = s.getIndex().substring(0, 2);

        return subjectRepository.findAll().stream()
                .filter(sub -> sub.getYear() == s.getYear()
                        && sub.getField().startsWith(fieldPrefix)
                        && !s.getPassedSubjects().contains(sub))
                .map(sub -> new ExamRegistrationDTO(
                        sub.getId(),
                        sub.getName(),
                        sub.getEspb(),
                        sub.getYear(),
                        sub.getField(),
                        sub.getProfessor().getName(),
                        sub.getProfessor().getSurname(),
                        sub.getProfessor().getEmail(),
                        s.getSubjects().contains(sub)
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void registerExams(int studentId, List<Integer> subjectIds) {
        Student s = studentRepository.findById(studentId).orElseThrow();
        double totalCost = 200*subjectIds.size();
        if(s.getMoney()<totalCost) {
            throw new RuntimeException("Nema dovoljno raspolozivih sredstava");
        }

        s.setMoney(s.getMoney()-totalCost);

        List<Subject> toRegister = subjectRepository.findAllById(subjectIds);
        s.getSubjects().addAll(toRegister);
        studentRepository.save(s);
    }
    @Transactional
    public void unregisterExams(int studentId, List<Integer> subjectIds) {
        Student s = studentRepository.findById(studentId).orElseThrow();

        double refundPerExam = 200;
        double refundTotal = refundPerExam*subjectIds.size();

        s.setMoney(s.getMoney()+refundTotal);

        s.getSubjects().removeIf(sub -> subjectIds.contains(sub.getId()));
        studentRepository.save(s);
    }
}
