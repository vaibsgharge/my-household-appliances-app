package com.household.appliances.web.rest;

import com.household.appliances.service.ApplianceCategoriesService;
import com.household.appliances.web.rest.errors.BadRequestAlertException;
import com.household.appliances.service.dto.ApplianceCategoriesDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.household.appliances.domain.ApplianceCategories}.
 */
@RestController
@RequestMapping("/api")
public class ApplianceCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(ApplianceCategoriesResource.class);

    private static final String ENTITY_NAME = "applianceCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplianceCategoriesService applianceCategoriesService;

    public ApplianceCategoriesResource(ApplianceCategoriesService applianceCategoriesService) {
        this.applianceCategoriesService = applianceCategoriesService;
    }

    /**
     * {@code POST  /appliance-categories} : Create a new applianceCategories.
     *
     * @param applianceCategoriesDTO the applianceCategoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applianceCategoriesDTO, or with status {@code 400 (Bad Request)} if the applianceCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appliance-categories")
    public ResponseEntity<ApplianceCategoriesDTO> createApplianceCategories(@Valid @RequestBody ApplianceCategoriesDTO applianceCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to save ApplianceCategories : {}", applianceCategoriesDTO);
        if (applianceCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new applianceCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplianceCategoriesDTO result = applianceCategoriesService.save(applianceCategoriesDTO);
        return ResponseEntity.created(new URI("/api/appliance-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appliance-categories} : Updates an existing applianceCategories.
     *
     * @param applianceCategoriesDTO the applianceCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applianceCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the applianceCategoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applianceCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appliance-categories")
    public ResponseEntity<ApplianceCategoriesDTO> updateApplianceCategories(@Valid @RequestBody ApplianceCategoriesDTO applianceCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to update ApplianceCategories : {}", applianceCategoriesDTO);
        if (applianceCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplianceCategoriesDTO result = applianceCategoriesService.save(applianceCategoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applianceCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appliance-categories} : get all the applianceCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applianceCategories in body.
     */
    @GetMapping("/appliance-categories")
    public ResponseEntity<List<ApplianceCategoriesDTO>> getAllApplianceCategories(Pageable pageable) {
        log.debug("REST request to get a page of ApplianceCategories");
        Page<ApplianceCategoriesDTO> page = applianceCategoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appliance-categories/:id} : get the "id" applianceCategories.
     *
     * @param id the id of the applianceCategoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applianceCategoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appliance-categories/{id}")
    public ResponseEntity<ApplianceCategoriesDTO> getApplianceCategories(@PathVariable Long id) {
        log.debug("REST request to get ApplianceCategories : {}", id);
        Optional<ApplianceCategoriesDTO> applianceCategoriesDTO = applianceCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applianceCategoriesDTO);
    }

    /**
     * {@code DELETE  /appliance-categories/:id} : delete the "id" applianceCategories.
     *
     * @param id the id of the applianceCategoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appliance-categories/{id}")
    public ResponseEntity<Void> deleteApplianceCategories(@PathVariable Long id) {
        log.debug("REST request to delete ApplianceCategories : {}", id);
        applianceCategoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
