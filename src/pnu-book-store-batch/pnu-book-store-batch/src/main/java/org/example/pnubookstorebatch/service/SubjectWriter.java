package org.example.pnubookstorebatch.service;

import org.example.pnubookstorebatch.dto.SubjectDto;
import org.example.pnubookstorebatch.repository.SubjectRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubjectWriter implements ItemWriter<SubjectDto> {
	private final SubjectRepository subjectRepository;
	@Override
	public void write(Chunk<? extends SubjectDto> chunk) throws Exception {

		subjectRepository.insertNoDuplicatedSubject(chunk.getItems()
			.stream()
			.map(subjectDto -> (SubjectDto) subjectDto)
			.distinct()
			.toList());
	}
}
