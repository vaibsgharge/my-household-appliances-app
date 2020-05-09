package com.household.appliances.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.household.appliances.web.rest.TestUtil;

public class ApplianceCategoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplianceCategories.class);
        ApplianceCategories applianceCategories1 = new ApplianceCategories();
        applianceCategories1.setId(1L);
        ApplianceCategories applianceCategories2 = new ApplianceCategories();
        applianceCategories2.setId(applianceCategories1.getId());
        assertThat(applianceCategories1).isEqualTo(applianceCategories2);
        applianceCategories2.setId(2L);
        assertThat(applianceCategories1).isNotEqualTo(applianceCategories2);
        applianceCategories1.setId(null);
        assertThat(applianceCategories1).isNotEqualTo(applianceCategories2);
    }
}
