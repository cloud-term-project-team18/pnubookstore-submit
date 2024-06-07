package org.example.pnubookstore.domain.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.domain.product.entity.QSubject;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.pnubookstore.domain.product.entity.QSubject.*;

@Repository
@RequiredArgsConstructor
public class SubjectCustomRepositoryImpl implements SubjectCustomRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Subject> findSubjects(String college, String department, String professor, String subjectName) {
        return queryFactory
                .selectFrom(subject)
                .distinct()
                .where(collegeEq(college), departmentEq(department), professorEq(professor), subjectNameEq(subjectName))
                .fetch();
    }

    private BooleanExpression collegeEq(String college){
        if (college == "") return null;
        return subject.college.eq(college);
    }

    private BooleanExpression departmentEq(String department){
        if (department == "") return null;
        return subject.department.eq(department);
    }

    private BooleanExpression professorEq(String professor){
        if (professor == "") return null;
        return subject.professor.eq(professor);
    }

    private BooleanExpression subjectNameEq(String subjectName){
        if (subjectName == "") return null;
        return subject.subjectName.eq(subjectName);
    }
}
