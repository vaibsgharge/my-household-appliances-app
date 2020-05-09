package com.household.appliances.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplianceCategoriesMapperTest {

    private ApplianceCategoriesMapper applianceCategoriesMapper;

    @BeforeEach
    public void setUp() {
        applianceCategoriesMapper = new ApplianceCategoriesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applianceCategoriesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applianceCategoriesMapper.fromId(null)).isNull();
    }
}
