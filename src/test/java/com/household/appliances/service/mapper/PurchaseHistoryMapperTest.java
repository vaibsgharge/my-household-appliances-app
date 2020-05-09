package com.household.appliances.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseHistoryMapperTest {

    private PurchaseHistoryMapper purchaseHistoryMapper;

    @BeforeEach
    public void setUp() {
        purchaseHistoryMapper = new PurchaseHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(purchaseHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(purchaseHistoryMapper.fromId(null)).isNull();
    }
}
