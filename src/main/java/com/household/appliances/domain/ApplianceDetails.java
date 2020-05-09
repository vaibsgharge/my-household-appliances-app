package com.household.appliances.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

import com.household.appliances.domain.enumeration.Status;

/**
 * The Appliance Details entity.
 */
@Entity
@Table(name = "appliance_details")
public class ApplianceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @NotNull
    @Column(name = "appliance_name", nullable = false)
    private String applianceName;

    @NotNull
    @Column(name = "appliance_desc", nullable = false)
    private String applianceDesc;

    @Column(name = "category_id")
    private Integer categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "appliance_brand")
    private String applianceBrand;

    @Column(name = "appliance_model")
    private String applianceModel;

    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToOne
    @JoinColumn(unique = true)
    private PurchaseHistory purchases;

    @ManyToOne
    @JsonIgnoreProperties("appliances")
    private ApplianceCategories categories;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ApplianceDetails serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public ApplianceDetails applianceName(String applianceName) {
        this.applianceName = applianceName;
        return this;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public String getApplianceDesc() {
        return applianceDesc;
    }

    public ApplianceDetails applianceDesc(String applianceDesc) {
        this.applianceDesc = applianceDesc;
        return this;
    }

    public void setApplianceDesc(String applianceDesc) {
        this.applianceDesc = applianceDesc;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public ApplianceDetails categoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Status getStatus() {
        return status;
    }

    public ApplianceDetails status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getApplianceBrand() {
        return applianceBrand;
    }

    public ApplianceDetails applianceBrand(String applianceBrand) {
        this.applianceBrand = applianceBrand;
        return this;
    }

    public void setApplianceBrand(String applianceBrand) {
        this.applianceBrand = applianceBrand;
    }

    public String getApplianceModel() {
        return applianceModel;
    }

    public ApplianceDetails applianceModel(String applianceModel) {
        this.applianceModel = applianceModel;
        return this;
    }

    public void setApplianceModel(String applianceModel) {
        this.applianceModel = applianceModel;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public ApplianceDetails purchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ApplianceDetails createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public ApplianceDetails updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PurchaseHistory getPurchases() {
        return purchases;
    }

    public ApplianceDetails purchases(PurchaseHistory purchaseHistory) {
        this.purchases = purchaseHistory;
        return this;
    }

    public void setPurchases(PurchaseHistory purchaseHistory) {
        this.purchases = purchaseHistory;
    }

    public ApplianceCategories getCategories() {
        return categories;
    }

    public ApplianceDetails categories(ApplianceCategories applianceCategories) {
        this.categories = applianceCategories;
        return this;
    }

    public void setCategories(ApplianceCategories applianceCategories) {
        this.categories = applianceCategories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplianceDetails)) {
            return false;
        }
        return id != null && id.equals(((ApplianceDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplianceDetails{" +
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
            "}";
    }
}
