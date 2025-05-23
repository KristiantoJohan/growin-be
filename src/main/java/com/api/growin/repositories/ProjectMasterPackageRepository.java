package com.api.growin.repositories;

import com.api.growin.models.MasterPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ProjectMasterPackageRepository extends JpaRepository <MasterPackage, UUID> {

}
