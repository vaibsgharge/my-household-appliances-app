package com.household.appliances.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.household.appliances.web.rest.TestUtil;

public class ApplianceDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplianceDetailsDTO.class);
        ApplianceDetailsDTO applianceDetailsDTO1 = new ApplianceDetailsDTO();
        applianceDetailsDTO1.setId(1L);
        ApplianceDetailsDTO applianceDetailsDTO2 = new ApplianceDetailsDTO();
        assertThat(applianceDetailsDTO1).isNotEqualTo(applianceDetailsDTO2);
        applianceDetailsDTO2.setId(applianceDetailsDTO1.getId());
        assertThat(applianceDetailsDTO1).isEqualTo(applianceDetailsDTO2);
        applianceDetailsDTO2.setId(2L);
        assertThat(applianceDetailsDTO1).isNotEqualTo(applianceDetailsDTO2);
        applianceDetailsDTO1.setId(null);
        assertThat(applianceDetailsDTO1).isNotEqualTo(applianceDetailsDTO2);
    }
}
