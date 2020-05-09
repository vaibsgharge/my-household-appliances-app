package com.household.appliances.repository;

import com.household.appliances.domain.ApplianceCategories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplianceCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplianceCategoriesRepository extends JpaRepository<ApplianceCategories, Long> {
}
