package com.household.appliances.repository;

import com.household.appliances.domain.ApplianceDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplianceDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplianceDetailsRepository extends JpaRepository<ApplianceDetails, Long> {
}
