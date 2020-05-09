package com.household.appliances.service.impl;

import com.household.appliances.service.ApplianceCategoriesService;
import com.household.appliances.domain.ApplianceCategories;
import com.household.appliances.repository.ApplianceCategoriesRepository;
import com.household.appliances.service.dto.ApplianceCategoriesDTO;
import com.household.appliances.service.mapper.ApplianceCategoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplianceCategories}.
 */
@Service
@Transactional
public class ApplianceCategoriesServiceImpl implements ApplianceCategoriesService {

    private final Logger log = LoggerFactory.getLogger(ApplianceCategoriesServiceImpl.class);

    private final ApplianceCategoriesRepository applianceCategoriesRepository;

    private final ApplianceCategoriesMapper applianceCategoriesMapper;

    public ApplianceCategoriesServiceImpl(ApplianceCategoriesRepository applianceCategoriesRepository, ApplianceCategoriesMapper applianceCategoriesMapper) {
        this.applianceCategoriesRepository = applianceCategoriesRepository;
        this.applianceCategoriesMapper = applianceCategoriesMapper;
    }

    /**
     * Save a applianceCategories.
     *
     * @param applianceCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplianceCategoriesDTO save(ApplianceCategoriesDTO applianceCategoriesDTO) {
        log.debug("Request to save ApplianceCategories : {}", applianceCategoriesDTO);
        ApplianceCategories applianceCategories = applianceCategoriesMapper.toEntity(applianceCategoriesDTO);
        applianceCategories = applianceCategoriesRepository.save(applianceCategories);
        return applianceCategoriesMapper.toDto(applianceCategories);
    }

    /**
     * Get all the applianceCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplianceCategoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplianceCategories");
        return applianceCategoriesRepository.findAll(pageable)
            .map(applianceCategoriesMapper::toDto);
    }

    /**
     * Get one applianceCategories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplianceCategoriesDTO> findOne(Long id) {
        log.debug("Request to get ApplianceCategories : {}", id);
        return applianceCategoriesRepository.findById(id)
            .map(applianceCategoriesMapper::toDto);
    }

    /**
     * Delete the applianceCategories by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplianceCategories : {}", id);
        applianceCategoriesRepository.deleteById(id);
    }
}
