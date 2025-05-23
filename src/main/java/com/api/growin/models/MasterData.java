package com.api.growin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MasterData")
public class MasterData {
	 /**
		* Unique identifier for the master data.
		*/
	 @Id
	 @GeneratedValue(strategy = GenerationType.UUID)
	 private UUID id;

	 /**
		*  Grade of the master data
		*/
	 @Enumerated(EnumType.STRING)
	 private Grade grade;

	 /**
		*  The name of the master data
		*/
	 private String name;

	 /**
		*  The brand of the master data
		*/
	 private String brand;

	 /**
		*  The price of the master data
		*/
	 private long price;

	 /**
		* Timestamp indicating when the user was created.
		*/
	 @CreationTimestamp
	 @Column(name = "created_at", updatable = false)
	 private LocalDateTime createdAt;

	 /**
		* Timestamp indicating the last update of user details.
		*/
	 @UpdateTimestamp
	 @Column(name = "updated_at")
	 private LocalDateTime updatedAt;
}
