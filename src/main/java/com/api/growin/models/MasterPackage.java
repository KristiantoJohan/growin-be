package com.api.growin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "MasterPackage")
public class MasterPackage {
	 /**
		* Unique identifier for the master package.
		*/
	 @Id
	 @GeneratedValue(strategy = GenerationType.UUID)
	 private UUID id;

	 /**
		*  Pricing Status of the overall project
		*/
	 @Enumerated(EnumType.STRING)
	 private PackageLevel packageLevel;

	 /**
		*  The name of the master package
		*/
	 private String name;

	 /**
		*  The price of the master package
		*/
	 private Long price;

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
