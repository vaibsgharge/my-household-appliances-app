package com.household.appliances.web.rest;

import com.household.appliances.HouseholdAplicationApp;
import com.household.appliances.domain.PurchaseHistory;
import com.household.appliances.repository.PurchaseHistoryRepository;
import com.household.appliances.service.PurchaseHistoryService;
import com.household.appliances.service.dto.PurchaseHistoryDTO;
import com.household.appliances.service.mapper.PurchaseHistoryMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PurchaseHistoryResource} REST controller.
 */
@SpringBootTest(classes = HouseholdAplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PurchaseHistoryResourceIT {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    private PurchaseHistoryMapper purchaseHistoryMapper;

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseHistoryMockMvc;

    private PurchaseHistory purchaseHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseHistory createEntity(EntityManager em) {
        PurchaseHistory purchaseHistory = new PurchaseHistory()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return purchaseHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseHistory createUpdatedEntity(EntityManager em) {
        PurchaseHistory purchaseHistory = new PurchaseHistory()
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return purchaseHistory;
    }

    @BeforeEach
    public void initTest() {
        purchaseHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseHistory() throws Exception {
        int databaseSizeBeforeCreate = purchaseHistoryRepository.findAll().size();

        // Create the PurchaseHistory
        PurchaseHistoryDTO purchaseHistoryDTO = purchaseHistoryMapper.toDto(purchaseHistory);
        restPurchaseHistoryMockMvc.perform(post("/api/purchase-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseHistory testPurchaseHistory = purchaseHistoryList.get(purchaseHistoryList.size() - 1);
        assertThat(testPurchaseHistory.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testPurchaseHistory.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testPurchaseHistory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPurchaseHistory.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createPurchaseHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseHistoryRepository.findAll().size();

        // Create the PurchaseHistory with an existing ID
        purchaseHistory.setId(1L);
        PurchaseHistoryDTO purchaseHistoryDTO = purchaseHistoryMapper.toDto(purchaseHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseHistoryMockMvc.perform(post("/api/purchase-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchaseHistories() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        // Get all the purchaseHistoryList
        restPurchaseHistoryMockMvc.perform(get("/api/purchase-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseHistory() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        // Get the purchaseHistory
        restPurchaseHistoryMockMvc.perform(get("/api/purchase-histories/{id}", purchaseHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseHistory.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseHistory() throws Exception {
        // Get the purchaseHistory
        restPurchaseHistoryMockMvc.perform(get("/api/purchase-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseHistory() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();

        // Update the purchaseHistory
        PurchaseHistory updatedPurchaseHistory = purchaseHistoryRepository.findById(purchaseHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseHistory are not directly saved in db
        em.detach(updatedPurchaseHistory);
        updatedPurchaseHistory
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        PurchaseHistoryDTO purchaseHistoryDTO = purchaseHistoryMapper.toDto(updatedPurchaseHistory);

        restPurchaseHistoryMockMvc.perform(put("/api/purchase-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
        PurchaseHistory testPurchaseHistory = purchaseHistoryList.get(purchaseHistoryList.size() - 1);
        assertThat(testPurchaseHistory.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testPurchaseHistory.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testPurchaseHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPurchaseHistory.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();

        // Create the PurchaseHistory
        PurchaseHistoryDTO purchaseHistoryDTO = purchaseHistoryMapper.toDto(purchaseHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseHistoryMockMvc.perform(put("/api/purchase-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseHistory() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        int databaseSizeBeforeDelete = purchaseHistoryRepository.findAll().size();

        // Delete the purchaseHistory
        restPurchaseHistoryMockMvc.perform(delete("/api/purchase-histories/{id}", purchaseHistory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
