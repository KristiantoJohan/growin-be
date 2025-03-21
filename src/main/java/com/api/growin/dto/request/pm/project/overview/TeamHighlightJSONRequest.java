package com.api.growin.dto.request.pm.project.overview;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for submitting team highlight details.
 * <p>
 *     This class captures information about the team structure for a project,
 *     including the number of team members in different roles and the team leader's details.
 *     It also includes validation constraints to ensure data integrity.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-03-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamHighlightJSONRequest {

    /** 
     * The name of the team in charge of the project.
     * <p>
     *     This field must not be null or empty.
     * </p>
     */
    @NotNull(message = "Team in charge cannot be blank")
    private String teamInCharge;

    /** 
     * The number of Hustlers in the team.
     * <p>
     *     Must be at least 1.
     * </p>
     */
    @NotNull(message = "Hustler amount cannot be null")
    @Min(value = 1, message = "Hustler amount must be at least 1")
    private Integer hustler;

    /** 
     * The number of Hipsters in the team.
     * <p>
     *     Must be at least 1.
     * </p>
     */
    @NotNull(message = "Hipster amount cannot be null")
    @Min(value = 1, message = "Hipster amount must be at least 1")
    private Integer hipster;

    /** 
     * The number of Hackers in the team.
     * <p>
     *     Must be at least 1.
     * </p>
     */
    @NotNull(message = "Hacker amount cannot be null")
    @Min(value = 1, message = "Hacker amount must be at least 1")
    private Integer hacker;

    /** 
     * The name of the team leader.
     * <p>
     *     This field must not be null or empty.
     * </p>
     */
    @NotNull(message = "Team leader cannot be blank")
    private String teamLeader;

    /** 
     * The email address of the team leader.
     * <p>
     *     This field must not be null or empty.
     * </p>
     */
    @NotNull(message = "Team leader email cannot be blank")
    private String teamLeaderEmail;

    /** 
     * The phone number of the team leader.
     * <p>
     *     This field must not be null or empty.
     * </p>
     */
    @NotNull(message = "Team leader phone cannot be blank")
    private String teamLeaderPhone;
}
