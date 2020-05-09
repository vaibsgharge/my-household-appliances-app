package com.household.appliances.service;

import com.household.appliances.service.dto.ApplianceDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.household.appliances.domain.ApplianceDetails}.
 */
public interface ApplianceDetailsService {

    /**
     * Save a applianceDetails.
     *
     * @param applianceDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ApplianceDetailsDTO save(ApplianceDetailsDTO applianceDetailsDTO);

    /**
     * Get all the applianceDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplianceDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applianceDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplianceDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" applianceDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
