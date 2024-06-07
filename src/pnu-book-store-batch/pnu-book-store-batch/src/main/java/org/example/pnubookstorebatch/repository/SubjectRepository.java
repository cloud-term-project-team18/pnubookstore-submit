package org.example.pnubookstorebatch.repository;


import java.util.List;

import org.example.pnubookstorebatch.dto.SubjectDto;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository {
	void insertNoDuplicatedSubject(List<SubjectDto> subjectDtoList);
}
