package com.household.appliances.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplianceDetailsMapperTest {

    private ApplianceDetailsMapper applianceDetailsMapper;

    @BeforeEach
    public void setUp() {
        applianceDetailsMapper = new ApplianceDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applianceDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applianceDetailsMapper.fromId(null)).isNull();
    }
}
