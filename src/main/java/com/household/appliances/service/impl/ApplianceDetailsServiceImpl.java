package com.household.appliances.service.impl;

import com.household.appliances.service.ApplianceDetailsService;
import com.household.appliances.domain.ApplianceDetails;
import com.household.appliances.repository.ApplianceDetailsRepository;
import com.household.appliances.service.dto.ApplianceDetailsDTO;
import com.household.appliances.service.mapper.ApplianceDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplianceDetails}.
 */
@Service
@Transactional
public class ApplianceDetailsServiceImpl implements ApplianceDetailsService {

    private final Logger log = LoggerFactory.getLogger(ApplianceDetailsServiceImpl.class);

    private final ApplianceDetailsRepository applianceDetailsRepository;

    private final ApplianceDetailsMapper applianceDetailsMapper;

    public ApplianceDetailsServiceImpl(ApplianceDetailsRepository applianceDetailsRepository, ApplianceDetailsMapper applianceDetailsMapper) {
        this.applianceDetailsRepository = applianceDetailsRepository;
        this.applianceDetailsMapper = applianceDetailsMapper;
    }

    /**
     * Save a applianceDetails.
     *
     * @param applianceDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplianceDetailsDTO save(ApplianceDetailsDTO applianceDetailsDTO) {
        log.debug("Request to save ApplianceDetails : {}", applianceDetailsDTO);
        ApplianceDetails applianceDetails = applianceDetailsMapper.toEntity(applianceDetailsDTO);
        applianceDetails = applianceDetailsRepository.save(applianceDetails);
        return applianceDetailsMapper.toDto(applianceDetails);
    }

    /**
     * Get all the applianceDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplianceDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplianceDetails");
        return applianceDetailsRepository.findAll(pageable)
            .map(applianceDetailsMapper::toDto);
    }

    /**
     * Get one applianceDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplianceDetailsDTO> findOne(Long id) {
        log.debug("Request to get ApplianceDetails : {}", id);
        return applianceDetailsRepository.findById(id)
            .map(applianceDetailsMapper::toDto);
    }

    /**
     * Delete the applianceDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplianceDetails : {}", id);
        applianceDetailsRepository.deleteById(id);
    }
}
