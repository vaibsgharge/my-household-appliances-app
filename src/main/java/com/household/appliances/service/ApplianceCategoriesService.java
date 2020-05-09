package com.household.appliances.service;

import com.household.appliances.service.dto.ApplianceCategoriesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.household.appliances.domain.ApplianceCategories}.
 */
public interface ApplianceCategoriesService {

    /**
     * Save a applianceCategories.
     *
     * @param applianceCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    ApplianceCategoriesDTO save(ApplianceCategoriesDTO applianceCategoriesDTO);

    /**
     * Get all the applianceCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplianceCategoriesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applianceCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplianceCategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" applianceCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
