package com.household.appliances.web.rest;

import com.household.appliances.service.ApplianceDetailsService;
import com.household.appliances.web.rest.errors.BadRequestAlertException;
import com.household.appliances.service.dto.ApplianceDetailsDTO;

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
 * REST controller for managing {@link com.household.appliances.domain.ApplianceDetails}.
 */
@RestController
@RequestMapping("/api")
public class ApplianceDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ApplianceDetailsResource.class);

    private static final String ENTITY_NAME = "applianceDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplianceDetailsService applianceDetailsService;

    public ApplianceDetailsResource(ApplianceDetailsService applianceDetailsService) {
        this.applianceDetailsService = applianceDetailsService;
    }

    /**
     * {@code POST  /appliance-details} : Create a new applianceDetails.
     *
     * @param applianceDetailsDTO the applianceDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applianceDetailsDTO, or with status {@code 400 (Bad Request)} if the applianceDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appliance-details")
    public ResponseEntity<ApplianceDetailsDTO> createApplianceDetails(@Valid @RequestBody ApplianceDetailsDTO applianceDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ApplianceDetails : {}", applianceDetailsDTO);
        if (applianceDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new applianceDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplianceDetailsDTO result = applianceDetailsService.save(applianceDetailsDTO);
        return ResponseEntity.created(new URI("/api/appliance-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appliance-details} : Updates an existing applianceDetails.
     *
     * @param applianceDetailsDTO the applianceDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applianceDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the applianceDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applianceDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appliance-details")
    public ResponseEntity<ApplianceDetailsDTO> updateApplianceDetails(@Valid @RequestBody ApplianceDetailsDTO applianceDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ApplianceDetails : {}", applianceDetailsDTO);
        if (applianceDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplianceDetailsDTO result = applianceDetailsService.save(applianceDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applianceDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appliance-details} : get all the applianceDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applianceDetails in body.
     */
    @GetMapping("/appliance-details")
    public ResponseEntity<List<ApplianceDetailsDTO>> getAllApplianceDetails(Pageable pageable) {
        log.debug("REST request to get a page of ApplianceDetails");
        Page<ApplianceDetailsDTO> page = applianceDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appliance-details/:id} : get the "id" applianceDetails.
     *
     * @param id the id of the applianceDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applianceDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appliance-details/{id}")
    public ResponseEntity<ApplianceDetailsDTO> getApplianceDetails(@PathVariable Long id) {
        log.debug("REST request to get ApplianceDetails : {}", id);
        Optional<ApplianceDetailsDTO> applianceDetailsDTO = applianceDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applianceDetailsDTO);
    }

    /**
     * {@code DELETE  /appliance-details/:id} : delete the "id" applianceDetails.
     *
     * @param id the id of the applianceDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appliance-details/{id}")
    public ResponseEntity<Void> deleteApplianceDetails(@PathVariable Long id) {
        log.debug("REST request to delete ApplianceDetails : {}", id);
        applianceDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
