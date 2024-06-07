package org.example.pnubookstorebatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TaskExecution implements ApplicationRunner {
	private final JobLauncher jobLauncher;
	private final Job subjectCsvReaderJob;
	private static final String INPUT_FILE_NAME = "2024_pnu_subject_list.csv";

	@Override
	public void run(ApplicationArguments args) throws Exception {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("inputFile", INPUT_FILE_NAME);
		jobParametersBuilder.addLong("uniqueness", System.nanoTime());
		jobLauncher.run(subjectCsvReaderJob, jobParametersBuilder.toJobParameters());
	}
}
