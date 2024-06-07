package org.example.pnubookstorebatch.config;

import org.example.pnubookstorebatch.dto.SubjectDto;
import org.example.pnubookstorebatch.service.SubjectWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
	private final FlatFileItemReader<SubjectDto> reader;
	private final SubjectWriter subjectWriter;
	private static final int CHUNK_SIZE = 500;

	@Bean(name = "SubjectCsvReaderJob")
	public Job subjectCsvReaderJob(
		JobRepository jobRepository,
		PlatformTransactionManager transactionManager) {
		return new JobBuilder("subjectCsvReaderJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(subjectCsvReaderStep(jobRepository, transactionManager))
			.build();
	}

	@Bean
	public Step subjectCsvReaderStep(
		JobRepository jobRepository,
		PlatformTransactionManager transactionManager
	) {
		return new StepBuilder("subjectCsvReaderStep", jobRepository)
			.<SubjectDto, SubjectDto>chunk(CHUNK_SIZE, transactionManager)
			.reader(reader)
			.writer(subjectWriter)
			.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<SubjectDto> reader(@Value("#{jobParameters['inputFile']}") String inputFile) {
		return new FlatFileItemReaderBuilder<SubjectDto>()
			.name("csvReader")
			.resource(new ClassPathResource(inputFile))
			.delimited()
			.names(new String[]{"num", "college", "department", "division", "grade", "code", "bunban",
				"subjectName", "gubun", "score", "theory", "practice", "professor", "limit", "timetable",
				"cultureRegion", "english", "team", "remote", "etc"})
			.fieldSetMapper( fieldSet -> SubjectDto.builder()
				.college(fieldSet.readString("college"))
				.department(getDepartment(fieldSet))
				.subjectName(fieldSet.readRawString("subjectName")) // 공백 포함
				.professor(getValidProfessor(fieldSet.readRawString("professor")))		  // 공백 포함
				.build())
			.linesToSkip(3)
			.build();
	}

	private String getValidProfessor(String professor) {
		if (professor == null || professor.isBlank()) {
			return "미정";
		}
		return professor;
	}

	private String getDepartment(FieldSet fieldSet) {
		String department = fieldSet.readString("department");
		String division = fieldSet.readString("division");
		if (department.isBlank()) {
			return division;
		}
		return department;
	}
}
