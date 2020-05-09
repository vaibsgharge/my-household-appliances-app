package com.household.appliances.repository;

import com.household.appliances.domain.PurchaseHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PurchaseHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
}
