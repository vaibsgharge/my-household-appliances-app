package com.household.appliances.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.household.appliances.web.rest.TestUtil;

public class ApplianceDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplianceDetails.class);
        ApplianceDetails applianceDetails1 = new ApplianceDetails();
        applianceDetails1.setId(1L);
        ApplianceDetails applianceDetails2 = new ApplianceDetails();
        applianceDetails2.setId(applianceDetails1.getId());
        assertThat(applianceDetails1).isEqualTo(applianceDetails2);
        applianceDetails2.setId(2L);
        assertThat(applianceDetails1).isNotEqualTo(applianceDetails2);
        applianceDetails1.setId(null);
        assertThat(applianceDetails1).isNotEqualTo(applianceDetails2);
    }
}
