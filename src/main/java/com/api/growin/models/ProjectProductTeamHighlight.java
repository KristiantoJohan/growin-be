package com.api.growin.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_product_team_highlight")
public class ProjectProductTeamHighlight {
    /**
     * Unique identifier for the product team highlight.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * One To many relation with product
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectProduct project;

    /**
     * Team in charge of the product development
     */
    private String teamInCharge;

    /**
     * Team member total involved in development of the product
     */
    private int teamMemberTotal;

    /**
     * Hustler total involved in development of the product
     */
    private int hustler;

    /**
     * hipster total involved in development of the product
     */
    private int hipster;
    
    /**
     * hacker total involved in development of the product
     */
    private int hacker;

    /**
     * Leader of the team in charge
     */
    private String teamLeader;

    /**
     * Email of the team leader
     */
    private String teamLeaderEmail;

    /**
     * Contact of the team leader
     */
    private String teamLeaderPhone;
}
