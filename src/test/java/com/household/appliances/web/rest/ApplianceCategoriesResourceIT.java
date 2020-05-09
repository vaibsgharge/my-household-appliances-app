package com.household.appliances.web.rest;

import com.household.appliances.HouseholdAplicationApp;
import com.household.appliances.domain.ApplianceCategories;
import com.household.appliances.repository.ApplianceCategoriesRepository;
import com.household.appliances.service.ApplianceCategoriesService;
import com.household.appliances.service.dto.ApplianceCategoriesDTO;
import com.household.appliances.service.mapper.ApplianceCategoriesMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.household.appliances.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ApplianceCategoriesResource} REST controller.
 */
@SpringBootTest(classes = HouseholdAplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ApplianceCategoriesResourceIT {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_DESC = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_DESC = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ApplianceCategoriesRepository applianceCategoriesRepository;

    @Autowired
    private ApplianceCategoriesMapper applianceCategoriesMapper;

    @Autowired
    private ApplianceCategoriesService applianceCategoriesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplianceCategoriesMockMvc;

    private ApplianceCategories applianceCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplianceCategories createEntity(EntityManager em) {
        ApplianceCategories applianceCategories = new ApplianceCategories()
            .categoryName(DEFAULT_CATEGORY_NAME)
            .categoryDesc(DEFAULT_CATEGORY_DESC)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return applianceCategories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplianceCategories createUpdatedEntity(EntityManager em) {
        ApplianceCategories applianceCategories = new ApplianceCategories()
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDesc(UPDATED_CATEGORY_DESC)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return applianceCategories;
    }

    @BeforeEach
    public void initTest() {
        applianceCategories = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplianceCategories() throws Exception {
        int databaseSizeBeforeCreate = applianceCategoriesRepository.findAll().size();

        // Create the ApplianceCategories
        ApplianceCategoriesDTO applianceCategoriesDTO = applianceCategoriesMapper.toDto(applianceCategories);
        restApplianceCategoriesMockMvc.perform(post("/api/appliance-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceCategoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplianceCategories in the database
        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        ApplianceCategories testApplianceCategories = applianceCategoriesList.get(applianceCategoriesList.size() - 1);
        assertThat(testApplianceCategories.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testApplianceCategories.getCategoryDesc()).isEqualTo(DEFAULT_CATEGORY_DESC);
        assertThat(testApplianceCategories.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApplianceCategories.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createApplianceCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applianceCategoriesRepository.findAll().size();

        // Create the ApplianceCategories with an existing ID
        applianceCategories.setId(1L);
        ApplianceCategoriesDTO applianceCategoriesDTO = applianceCategoriesMapper.toDto(applianceCategories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplianceCategoriesMockMvc.perform(post("/api/appliance-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplianceCategories in the database
        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applianceCategoriesRepository.findAll().size();
        // set the field null
        applianceCategories.setCategoryName(null);

        // Create the ApplianceCategories, which fails.
        ApplianceCategoriesDTO applianceCategoriesDTO = applianceCategoriesMapper.toDto(applianceCategories);

        restApplianceCategoriesMockMvc.perform(post("/api/appliance-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = applianceCategoriesRepository.findAll().size();
        // set the field null
        applianceCategories.setCategoryDesc(null);

        // Create the ApplianceCategories, which fails.
        ApplianceCategoriesDTO applianceCategoriesDTO = applianceCategoriesMapper.toDto(applianceCategories);

        restApplianceCategoriesMockMvc.perform(post("/api/appliance-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = applianceCategoriesRepository.findAll().size();
        // set the field null
        applianceCategories.setCreatedAt(null);

        // Create the ApplianceCategories, which fails.
        ApplianceCategoriesDTO applianceCategoriesDTO = applianceCategoriesMapper.toDto(applianceCategories);

        restApplianceCategoriesMockMvc.perform(post("/api/appliance-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplianceCategories() throws Exception {
        // Initialize the database
        applianceCategoriesRepository.saveAndFlush(applianceCategories);

        // Get all the applianceCategoriesList
        restApplianceCategoriesMockMvc.perform(get("/api/appliance-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applianceCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].categoryDesc").value(hasItem(DEFAULT_CATEGORY_DESC)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }
    
    @Test
    @Transactional
    public void getApplianceCategories() throws Exception {
        // Initialize the database
        applianceCategoriesRepository.saveAndFlush(applianceCategories);

        // Get the applianceCategories
        restApplianceCategoriesMockMvc.perform(get("/api/appliance-categories/{id}", applianceCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applianceCategories.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.categoryDesc").value(DEFAULT_CATEGORY_DESC))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingApplianceCategories() throws Exception {
        // Get the applianceCategories
        restApplianceCategoriesMockMvc.perform(get("/api/appliance-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplianceCategories() throws Exception {
        // Initialize the database
        applianceCategoriesRepository.saveAndFlush(applianceCategories);

        int databaseSizeBeforeUpdate = applianceCategoriesRepository.findAll().size();

        // Update the applianceCategories
        ApplianceCategories updatedApplianceCategories = applianceCategoriesRepository.findById(applianceCategories.getId()).get();
        // Disconnect from session so that the updates on updatedApplianceCategories are not directly saved in db
        em.detach(updatedApplianceCategories);
        updatedApplianceCategories
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDesc(UPDATED_CATEGORY_DESC)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        ApplianceCategoriesDTO applianceCategoriesDTO = applianceCategoriesMapper.toDto(updatedApplianceCategories);

        restApplianceCategoriesMockMvc.perform(put("/api/appliance-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceCategoriesDTO)))
            .andExpect(status().isOk());

        // Validate the ApplianceCategories in the database
        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeUpdate);
        ApplianceCategories testApplianceCategories = applianceCategoriesList.get(applianceCategoriesList.size() - 1);
        assertThat(testApplianceCategories.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testApplianceCategories.getCategoryDesc()).isEqualTo(UPDATED_CATEGORY_DESC);
        assertThat(testApplianceCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApplianceCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingApplianceCategories() throws Exception {
        int databaseSizeBeforeUpdate = applianceCategoriesRepository.findAll().size();

        // Create the ApplianceCategories
        ApplianceCategoriesDTO applianceCategoriesDTO = applianceCategoriesMapper.toDto(applianceCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplianceCategoriesMockMvc.perform(put("/api/appliance-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applianceCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplianceCategories in the database
        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplianceCategories() throws Exception {
        // Initialize the database
        applianceCategoriesRepository.saveAndFlush(applianceCategories);

        int databaseSizeBeforeDelete = applianceCategoriesRepository.findAll().size();

        // Delete the applianceCategories
        restApplianceCategoriesMockMvc.perform(delete("/api/appliance-categories/{id}", applianceCategories.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplianceCategories> applianceCategoriesList = applianceCategoriesRepository.findAll();
        assertThat(applianceCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
