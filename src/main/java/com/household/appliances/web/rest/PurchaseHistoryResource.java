package com.household.appliances.web.rest;

import com.household.appliances.service.PurchaseHistoryService;
import com.household.appliances.web.rest.errors.BadRequestAlertException;
import com.household.appliances.service.dto.PurchaseHistoryDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.household.appliances.domain.PurchaseHistory}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseHistoryResource.class);

    private static final String ENTITY_NAME = "purchaseHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseHistoryService purchaseHistoryService;

    public PurchaseHistoryResource(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }

    /**
     * {@code POST  /purchase-histories} : Create a new purchaseHistory.
     *
     * @param purchaseHistoryDTO the purchaseHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseHistoryDTO, or with status {@code 400 (Bad Request)} if the purchaseHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-histories")
    public ResponseEntity<PurchaseHistoryDTO> createPurchaseHistory(@RequestBody PurchaseHistoryDTO purchaseHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseHistory : {}", purchaseHistoryDTO);
        if (purchaseHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseHistoryDTO result = purchaseHistoryService.save(purchaseHistoryDTO);
        return ResponseEntity.created(new URI("/api/purchase-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-histories} : Updates an existing purchaseHistory.
     *
     * @param purchaseHistoryDTO the purchaseHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the purchaseHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-histories")
    public ResponseEntity<PurchaseHistoryDTO> updatePurchaseHistory(@RequestBody PurchaseHistoryDTO purchaseHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseHistory : {}", purchaseHistoryDTO);
        if (purchaseHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseHistoryDTO result = purchaseHistoryService.save(purchaseHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purchaseHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-histories} : get all the purchaseHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseHistories in body.
     */
    @GetMapping("/purchase-histories")
    public ResponseEntity<List<PurchaseHistoryDTO>> getAllPurchaseHistories(Pageable pageable) {
        log.debug("REST request to get a page of PurchaseHistories");
        Page<PurchaseHistoryDTO> page = purchaseHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchase-histories/:id} : get the "id" purchaseHistory.
     *
     * @param id the id of the purchaseHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-histories/{id}")
    public ResponseEntity<PurchaseHistoryDTO> getPurchaseHistory(@PathVariable Long id) {
        log.debug("REST request to get PurchaseHistory : {}", id);
        Optional<PurchaseHistoryDTO> purchaseHistoryDTO = purchaseHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseHistoryDTO);
    }

    /**
     * {@code DELETE  /purchase-histories/:id} : delete the "id" purchaseHistory.
     *
     * @param id the id of the purchaseHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-histories/{id}")
    public ResponseEntity<Void> deletePurchaseHistory(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseHistory : {}", id);
        purchaseHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
