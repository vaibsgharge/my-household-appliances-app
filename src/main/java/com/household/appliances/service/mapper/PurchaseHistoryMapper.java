package com.household.appliances.service.mapper;


import com.household.appliances.domain.*;
import com.household.appliances.service.dto.PurchaseHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseHistory} and its DTO {@link PurchaseHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseHistoryMapper extends EntityMapper<PurchaseHistoryDTO, PurchaseHistory> {



    default PurchaseHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setId(id);
        return purchaseHistory;
    }
}
