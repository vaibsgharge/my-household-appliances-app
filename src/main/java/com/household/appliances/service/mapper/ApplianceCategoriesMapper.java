package com.household.appliances.service.mapper;


import com.household.appliances.domain.*;
import com.household.appliances.service.dto.ApplianceCategoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplianceCategories} and its DTO {@link ApplianceCategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplianceCategoriesMapper extends EntityMapper<ApplianceCategoriesDTO, ApplianceCategories> {


    @Mapping(target = "appliances", ignore = true)
    @Mapping(target = "removeAppliances", ignore = true)
    ApplianceCategories toEntity(ApplianceCategoriesDTO applianceCategoriesDTO);

    default ApplianceCategories fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplianceCategories applianceCategories = new ApplianceCategories();
        applianceCategories.setId(id);
        return applianceCategories;
    }
}
