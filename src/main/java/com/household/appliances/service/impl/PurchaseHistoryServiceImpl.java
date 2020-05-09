package com.household.appliances.service.impl;

import com.household.appliances.service.PurchaseHistoryService;
import com.household.appliances.domain.PurchaseHistory;
import com.household.appliances.repository.PurchaseHistoryRepository;
import com.household.appliances.service.dto.PurchaseHistoryDTO;
import com.household.appliances.service.mapper.PurchaseHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PurchaseHistory}.
 */
@Service
@Transactional
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private final Logger log = LoggerFactory.getLogger(PurchaseHistoryServiceImpl.class);

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    private final PurchaseHistoryMapper purchaseHistoryMapper;

    public PurchaseHistoryServiceImpl(PurchaseHistoryRepository purchaseHistoryRepository, PurchaseHistoryMapper purchaseHistoryMapper) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.purchaseHistoryMapper = purchaseHistoryMapper;
    }

    /**
     * Save a purchaseHistory.
     *
     * @param purchaseHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseHistoryDTO save(PurchaseHistoryDTO purchaseHistoryDTO) {
        log.debug("Request to save PurchaseHistory : {}", purchaseHistoryDTO);
        PurchaseHistory purchaseHistory = purchaseHistoryMapper.toEntity(purchaseHistoryDTO);
        purchaseHistory = purchaseHistoryRepository.save(purchaseHistory);
        return purchaseHistoryMapper.toDto(purchaseHistory);
    }

    /**
     * Get all the purchaseHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseHistories");
        return purchaseHistoryRepository.findAll(pageable)
            .map(purchaseHistoryMapper::toDto);
    }

    /**
     * Get one purchaseHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseHistoryDTO> findOne(Long id) {
        log.debug("Request to get PurchaseHistory : {}", id);
        return purchaseHistoryRepository.findById(id)
            .map(purchaseHistoryMapper::toDto);
    }

    /**
     * Delete the purchaseHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseHistory : {}", id);
        purchaseHistoryRepository.deleteById(id);
    }
}
