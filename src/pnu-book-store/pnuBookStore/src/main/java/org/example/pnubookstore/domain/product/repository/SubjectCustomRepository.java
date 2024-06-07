package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Subject;

import java.util.List;

public interface SubjectCustomRepository {
    List<Subject> findSubjects(String college, String department, String professor, String subjectName);
}
