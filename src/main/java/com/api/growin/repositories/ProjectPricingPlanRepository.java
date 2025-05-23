package com.api.growin.repositories;

import com.api.growin.models.Project;
import com.api.growin.models.ProjectPricingPlan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ProjectPricingPlanRepository extends JpaRepository<ProjectPricingPlan, UUID> {
    /**
     * Retrieves the pricing plan associated with a given product.
     *
     * @param project the product whose pricing plan are to be retrieved.
     * @return a list of {@link ProjectPricingPlan} entities linked to the specified product.
     */
    ProjectPricingPlan findByProject(Project project);

    void deleteByProject(Project project);

}
