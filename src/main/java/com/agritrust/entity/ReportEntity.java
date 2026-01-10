package com.agritrust.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "abuse_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/* Optional batch code or serial number */
	@Column(length = 100)
	private String batchCode;

	/* Reporter info (optional but useful) */
	@Column(length = 100)
	private String name;

	@Column(length = 100)
	private String surname;

	@Column(length = 11)
	private String citizenId;

	/* What is being reported */
	@Column(nullable = false, length = 500)
	private String reason;

	/* Admin workflow */
	@Column(nullable = false)
	private boolean reviewed;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
