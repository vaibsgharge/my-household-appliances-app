package com.household.appliances.service.mapper;


import com.household.appliances.domain.*;
import com.household.appliances.service.dto.ApplianceDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplianceDetails} and its DTO {@link ApplianceDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PurchaseHistoryMapper.class, ApplianceCategoriesMapper.class})
public interface ApplianceDetailsMapper extends EntityMapper<ApplianceDetailsDTO, ApplianceDetails> {

    @Mapping(source = "purchases.id", target = "purchasesId")
    @Mapping(source = "categories.id", target = "categoriesId")
    ApplianceDetailsDTO toDto(ApplianceDetails applianceDetails);

    @Mapping(source = "purchasesId", target = "purchases")
    @Mapping(source = "categoriesId", target = "categories")
    ApplianceDetails toEntity(ApplianceDetailsDTO applianceDetailsDTO);

    default ApplianceDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplianceDetails applianceDetails = new ApplianceDetails();
        applianceDetails.setId(id);
        return applianceDetails;
    }
}
