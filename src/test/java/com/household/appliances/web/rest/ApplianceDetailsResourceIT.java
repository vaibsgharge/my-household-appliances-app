package com.household.appliances.web.rest;

import com.household.appliances.HouseholdAplicationApp;
import com.household.appliances.domain.ApplianceDetails;
import com.household.appliances.repository.ApplianceDetailsRepository;
import com.household.appliances.service.ApplianceDetailsService;
import com.household.appliances.service.dto.ApplianceDetailsDTO;
import com.household.appliances.service.mapper.ApplianceDetailsMapper;

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

import com.household.appliances.domain.enumeration.Status;
/**
 * Integration tests for the {@link ApplianceDetailsResource} REST controller.
 */
@SpringBootTest(classes = HouseholdAplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ApplianceDetailsResourceIT {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_APPLIANCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLIANCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APPLIANCE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_APPLIANCE_DESC = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATEGORY_ID = 1;
    private static final Integer UPDATED_CATEGORY_ID = 2;

    private static final Status DEFAULT_STATUS = Status.OLD;
    private static final Status UPDATED_STATUS = Status.UNUSED;

    private static final String DEFAULT_APPLIANCE_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_APPLIANCE_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_APPLIANCE_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_APPLIANCE_MODEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PURCHASE_ID = 1;
    private static final Integer UPDATED_PURCHASE_ID = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ApplianceDetailsRepository applianceDetailsRepository;

    @Autowired
    private ApplianceDetailsMapper applianceDetailsMapper;

    @Autowired
    private ApplianceDetailsService applianceDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplianceDetailsMockMvc;

    private ApplianceDetails applianceDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplianceDetails createEntity(EntityManager em) {
        ApplianceDetails applianceDetails = new ApplianceDetails()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .applianceName(DEFAULT_APPLIANCE_NAME)
            .applianceDesc(DEFAULT_APPLIANCE_DESC)
            .categoryId(DEFAULT_CATEGORY_ID)
            .status(DEFAULT_STATUS)
            .applianceBrand(DEFAULT_APPLIANCE_BRAND)
            .applianceModel(DEFAULT_APPLIANCE_MODEL)
            .purchaseId(DEFAULT_PURCHASE_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return applianceDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplianceDetails createUpdatedEntity(EntityManager em) {
        ApplianceDetails applianceDetails = new ApplianceDetails()
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .applianceName(UPDATED_APPLIANCE_NAME)
            .applianceDesc(UPDATED_APPLIANCE_DESC)
            .categoryId(UPDATED_CATEGORY_ID)
            .status(UPDATED_STATUS)
            .applianceBrand(UPDATED_APPLIANCE_BRAND)
            .applianceModel(UPDATED_APPLIANCE_MODEL)
            .purchaseId(UPDATED_PURCHASE_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return applianceDetails;
    }

    @BeforeEach
    public void initTest() {
        applianceDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplianceDetails() throws Exception {
        int databaseSizeBeforeCreate = applianceDetailsRepository.findAll().size();

        // Create the ApplianceDetails
        ApplianceDetailsDTO applianceDetailsDTO = applianceDetailsMapper.toDto(applianceDetails);
        restApplianceDetailsMockMvc.perform(post("/api/appliance-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplianceDetails in the database
        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ApplianceDetails testApplianceDetails = applianceDetailsList.get(applianceDetailsList.size() - 1);
        assertThat(testApplianceDetails.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testApplianceDetails.getApplianceName()).isEqualTo(DEFAULT_APPLIANCE_NAME);
        assertThat(testApplianceDetails.getApplianceDesc()).isEqualTo(DEFAULT_APPLIANCE_DESC);
        assertThat(testApplianceDetails.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testApplianceDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplianceDetails.getApplianceBrand()).isEqualTo(DEFAULT_APPLIANCE_BRAND);
        assertThat(testApplianceDetails.getApplianceModel()).isEqualTo(DEFAULT_APPLIANCE_MODEL);
        assertThat(testApplianceDetails.getPurchaseId()).isEqualTo(DEFAULT_PURCHASE_ID);
        assertThat(testApplianceDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApplianceDetails.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createApplianceDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applianceDetailsRepository.findAll().size();

        // Create the ApplianceDetails with an existing ID
        applianceDetails.setId(1L);
        ApplianceDetailsDTO applianceDetailsDTO = applianceDetailsMapper.toDto(applianceDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplianceDetailsMockMvc.perform(post("/api/appliance-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplianceDetails in the database
        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSerialNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = applianceDetailsRepository.findAll().size();
        // set the field null
        applianceDetails.setSerialNumber(null);

        // Create the ApplianceDetails, which fails.
        ApplianceDetailsDTO applianceDetailsDTO = applianceDetailsMapper.toDto(applianceDetails);

        restApplianceDetailsMockMvc.perform(post("/api/appliance-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplianceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applianceDetailsRepository.findAll().size();
        // set the field null
        applianceDetails.setApplianceName(null);

        // Create the ApplianceDetails, which fails.
        ApplianceDetailsDTO applianceDetailsDTO = applianceDetailsMapper.toDto(applianceDetails);

        restApplianceDetailsMockMvc.perform(post("/api/appliance-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplianceDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = applianceDetailsRepository.findAll().size();
        // set the field null
        applianceDetails.setApplianceDesc(null);

        // Create the ApplianceDetails, which fails.
        ApplianceDetailsDTO applianceDetailsDTO = applianceDetailsMapper.toDto(applianceDetails);

        restApplianceDetailsMockMvc.perform(post("/api/appliance-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplianceDetails() throws Exception {
        // Initialize the database
        applianceDetailsRepository.saveAndFlush(applianceDetails);

        // Get all the applianceDetailsList
        restApplianceDetailsMockMvc.perform(get("/api/appliance-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applianceDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].applianceName").value(hasItem(DEFAULT_APPLIANCE_NAME)))
            .andExpect(jsonPath("$.[*].applianceDesc").value(hasItem(DEFAULT_APPLIANCE_DESC)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].applianceBrand").value(hasItem(DEFAULT_APPLIANCE_BRAND)))
            .andExpect(jsonPath("$.[*].applianceModel").value(hasItem(DEFAULT_APPLIANCE_MODEL)))
            .andExpect(jsonPath("$.[*].purchaseId").value(hasItem(DEFAULT_PURCHASE_ID)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getApplianceDetails() throws Exception {
        // Initialize the database
        applianceDetailsRepository.saveAndFlush(applianceDetails);

        // Get the applianceDetails
        restApplianceDetailsMockMvc.perform(get("/api/appliance-details/{id}", applianceDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applianceDetails.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.applianceName").value(DEFAULT_APPLIANCE_NAME))
            .andExpect(jsonPath("$.applianceDesc").value(DEFAULT_APPLIANCE_DESC))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.applianceBrand").value(DEFAULT_APPLIANCE_BRAND))
            .andExpect(jsonPath("$.applianceModel").value(DEFAULT_APPLIANCE_MODEL))
            .andExpect(jsonPath("$.purchaseId").value(DEFAULT_PURCHASE_ID))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplianceDetails() throws Exception {
        // Get the applianceDetails
        restApplianceDetailsMockMvc.perform(get("/api/appliance-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplianceDetails() throws Exception {
        // Initialize the database
        applianceDetailsRepository.saveAndFlush(applianceDetails);

        int databaseSizeBeforeUpdate = applianceDetailsRepository.findAll().size();

        // Update the applianceDetails
        ApplianceDetails updatedApplianceDetails = applianceDetailsRepository.findById(applianceDetails.getId()).get();
        // Disconnect from session so that the updates on updatedApplianceDetails are not directly saved in db
        em.detach(updatedApplianceDetails);
        updatedApplianceDetails
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .applianceName(UPDATED_APPLIANCE_NAME)
            .applianceDesc(UPDATED_APPLIANCE_DESC)
            .categoryId(UPDATED_CATEGORY_ID)
            .status(UPDATED_STATUS)
            .applianceBrand(UPDATED_APPLIANCE_BRAND)
            .applianceModel(UPDATED_APPLIANCE_MODEL)
            .purchaseId(UPDATED_PURCHASE_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        ApplianceDetailsDTO applianceDetailsDTO = applianceDetailsMapper.toDto(updatedApplianceDetails);

        restApplianceDetailsMockMvc.perform(put("/api/appliance-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the ApplianceDetails in the database
        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeUpdate);
        ApplianceDetails testApplianceDetails = applianceDetailsList.get(applianceDetailsList.size() - 1);
        assertThat(testApplianceDetails.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testApplianceDetails.getApplianceName()).isEqualTo(UPDATED_APPLIANCE_NAME);
        assertThat(testApplianceDetails.getApplianceDesc()).isEqualTo(UPDATED_APPLIANCE_DESC);
        assertThat(testApplianceDetails.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testApplianceDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplianceDetails.getApplianceBrand()).isEqualTo(UPDATED_APPLIANCE_BRAND);
        assertThat(testApplianceDetails.getApplianceModel()).isEqualTo(UPDATED_APPLIANCE_MODEL);
        assertThat(testApplianceDetails.getPurchaseId()).isEqualTo(UPDATED_PURCHASE_ID);
        assertThat(testApplianceDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApplianceDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingApplianceDetails() throws Exception {
        int databaseSizeBeforeUpdate = applianceDetailsRepository.findAll().size();

        // Create the ApplianceDetails
        ApplianceDetailsDTO applianceDetailsDTO = applianceDetailsMapper.toDto(applianceDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplianceDetailsMockMvc.perform(put("/api/appliance-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplianceDetails in the database
        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplianceDetails() throws Exception {
        // Initialize the database
        applianceDetailsRepository.saveAndFlush(applianceDetails);

        int databaseSizeBeforeDelete = applianceDetailsRepository.findAll().size();

        // Delete the applianceDetails
        restApplianceDetailsMockMvc.perform(delete("/api/appliance-details/{id}", applianceDetails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplianceDetails> applianceDetailsList = applianceDetailsRepository.findAll();
        assertThat(applianceDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
