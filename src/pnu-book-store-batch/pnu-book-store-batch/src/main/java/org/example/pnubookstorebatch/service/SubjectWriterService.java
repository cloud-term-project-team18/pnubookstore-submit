package org.example.pnubookstorebatch.service;

import org.example.pnubookstorebatch.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class SubjectWriterService {
	private final SubjectRepository subjectRepository;
}
