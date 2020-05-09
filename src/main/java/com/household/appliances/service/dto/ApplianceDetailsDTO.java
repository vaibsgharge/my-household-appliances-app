package com.household.appliances.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.household.appliances.domain.enumeration.Status;

/**
 * A DTO for the {@link com.household.appliances.domain.ApplianceDetails} entity.
 */
@ApiModel(description = "The Appliance Details entity.")
public class ApplianceDetailsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String serialNumber;

    @NotNull
    private String applianceName;

    @NotNull
    private String applianceDesc;

    private Integer categoryId;

    private Status status;

    private String applianceBrand;

    private String applianceModel;

    private Integer purchaseId;

    private LocalDate createdAt;

    private LocalDate updatedAt;


    private Long purchasesId;

    private Long categoriesId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public String getApplianceDesc() {
        return applianceDesc;
    }

    public void setApplianceDesc(String applianceDesc) {
        this.applianceDesc = applianceDesc;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getApplianceBrand() {
        return applianceBrand;
    }

    public void setApplianceBrand(String applianceBrand) {
        this.applianceBrand = applianceBrand;
    }

    public String getApplianceModel() {
        return applianceModel;
    }

    public void setApplianceModel(String applianceModel) {
        this.applianceModel = applianceModel;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getPurchasesId() {
        return purchasesId;
    }

    public void setPurchasesId(Long purchaseHistoryId) {
        this.purchasesId = purchaseHistoryId;
    }

    public Long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Long applianceCategoriesId) {
        this.categoriesId = applianceCategoriesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplianceDetailsDTO applianceDetailsDTO = (ApplianceDetailsDTO) o;
        if (applianceDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applianceDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplianceDetailsDTO{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", applianceName='" + getApplianceName() + "'" +
            ", applianceDesc='" + getApplianceDesc() + "'" +
            ", categoryId=" + getCategoryId() +
            ", status='" + getStatus() + "'" +
            ", applianceBrand='" + getApplianceBrand() + "'" +
            ", applianceModel='" + getApplianceModel() + "'" +
            ", purchaseId=" + getPurchaseId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", purchasesId=" + getPurchasesId() +
            ", categoriesId=" + getCategoriesId() +
            "}";
    }
}
