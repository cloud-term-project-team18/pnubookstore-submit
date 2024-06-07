package org.example.pnubookstorebatch.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.example.pnubookstorebatch.dto.SubjectDto;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository {
	private static final String TEMP_TABLE = "temp_subject_tb";
	private static final String TABLE = "subject_tb";
	private final NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public void insertNoDuplicatedSubject(List<SubjectDto> subjectDtoList) {
		JdbcTemplate jdbcTemplate = namedJdbcTemplate.getJdbcTemplate();
		String insertQuery = String.format("INSERT INTO %s (subject_name, college, department, professor,"
			+ " created_at, last_modified_at, is_deleted) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)", TEMP_TABLE);

		// 임시 테이블 생성
		jdbcTemplate.execute(String.format("CREATE TEMPORARY TABLE %s (id BIGINT, "
			+ "subject_name VARCHAR(255), "
			+ "college VARCHAR(255), "
			+ "department VARCHAR(255), "
			+ "professor VARCHAR(255), "
			+ "created_at TIMESTAMP, "
			+ "last_modified_at TIMESTAMP, "
			+ "is_deleted BIT)", TEMP_TABLE));

		// 임시 테이블 벌크 Insert
		jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SubjectDto subjectDto = subjectDtoList.get(i);
				ps.setString(1, subjectDto.subjectName());
				ps.setString(2, subjectDto.college());
				ps.setString(3, subjectDto.department());
				ps.setString(4, subjectDto.professor());
				ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
				ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
				ps.setBoolean(7, false);
			}

			@Override
			public int getBatchSize() {
				return subjectDtoList.size();
			}
		});

		jdbcTemplate.execute(String.format("INSERT INTO %s (subject_name, college, department, professor, "
			+ "created_at, last_modified_at, is_deleted) "
			+ "SELECT t.subject_name, t.college, t.department, t.professor, "
			+ "t.created_at, t.last_modified_at, t.is_deleted FROM %s t "
			+ "LEFT JOIN %s s ON s.subject_name = t.subject_name "
			+ "AND s.college = t.college "
			+ "AND s.department = t.department "
			+ "AND s.professor = t.professor "
			+ "WHERE s.subject_name IS NULL",  TABLE, TEMP_TABLE, TABLE));

		jdbcTemplate.execute(String.format("DROP TABLE IF EXISTS %s;", TEMP_TABLE));
	}
}
