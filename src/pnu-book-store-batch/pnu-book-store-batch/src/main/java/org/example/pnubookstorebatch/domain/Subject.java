package org.example.pnubookstorebatch.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="subject_tb")
public class Subject extends AuditingEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String subjectName;
	@Column(nullable = false)
	private String professor;
	@Column(nullable = false)
	private String college;
	@Column(nullable = false)
	private String department;

	@Builder
	public Subject(Long id, String subjectName, String professor, String college, String department) {
		this.id = id;
		this.subjectName = subjectName;
		this.professor = professor;
		this.college = college;
		this.department = department;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Subject subject = (Subject)o;
		return Objects.equals(getId(), subject.getId()) && Objects.equals(getSubjectName(),
			subject.getSubjectName()) && Objects.equals(getProfessor(), subject.getProfessor())
			&& Objects.equals(getCollege(), subject.getCollege()) && Objects.equals(getDepartment(),
			subject.getDepartment());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getSubjectName(), getProfessor(), getCollege(), getDepartment());
	}
}
