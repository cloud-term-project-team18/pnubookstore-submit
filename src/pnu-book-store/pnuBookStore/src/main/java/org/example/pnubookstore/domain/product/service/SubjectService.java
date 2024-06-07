package org.example.pnubookstore.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.domain.product.repository.SubjectJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectJpaRepository subjectJpaRepository;

    public List<String> findColleges(){
        return subjectJpaRepository.findColleges();
    }

    public List<String> findDepartment(String college){
        return subjectJpaRepository.findDepartments(college);
    }

    public List<String> findProfessors(String college, String department){
        return subjectJpaRepository.findProfessors(college, department);
    }

    public List<String> findSubjectNames(String college, String department, String professor){
        return subjectJpaRepository.findSubjectNames(college, department, professor);
    }
}
