package com.household.appliances.service;

import com.household.appliances.service.dto.PurchaseHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.household.appliances.domain.PurchaseHistory}.
 */
public interface PurchaseHistoryService {

    /**
     * Save a purchaseHistory.
     *
     * @param purchaseHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    PurchaseHistoryDTO save(PurchaseHistoryDTO purchaseHistoryDTO);

    /**
     * Get all the purchaseHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurchaseHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" purchaseHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
