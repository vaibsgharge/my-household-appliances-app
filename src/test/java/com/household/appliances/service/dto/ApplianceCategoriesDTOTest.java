package com.household.appliances.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.household.appliances.web.rest.TestUtil;

public class ApplianceCategoriesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplianceCategoriesDTO.class);
        ApplianceCategoriesDTO applianceCategoriesDTO1 = new ApplianceCategoriesDTO();
        applianceCategoriesDTO1.setId(1L);
        ApplianceCategoriesDTO applianceCategoriesDTO2 = new ApplianceCategoriesDTO();
        assertThat(applianceCategoriesDTO1).isNotEqualTo(applianceCategoriesDTO2);
        applianceCategoriesDTO2.setId(applianceCategoriesDTO1.getId());
        assertThat(applianceCategoriesDTO1).isEqualTo(applianceCategoriesDTO2);
        applianceCategoriesDTO2.setId(2L);
        assertThat(applianceCategoriesDTO1).isNotEqualTo(applianceCategoriesDTO2);
        applianceCategoriesDTO1.setId(null);
        assertThat(applianceCategoriesDTO1).isNotEqualTo(applianceCategoriesDTO2);
    }
}
