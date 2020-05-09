package com.household.appliances.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The Appliance Category entity.
 */
@Entity
@Table(name = "appliance_categories")
public class ApplianceCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @NotNull
    @Column(name = "category_desc", nullable = false)
    private String categoryDesc;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "categories")
    private Set<ApplianceDetails> appliances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ApplianceCategories categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public ApplianceCategories categoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
        return this;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ApplianceCategories createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ApplianceCategories updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<ApplianceDetails> getAppliances() {
        return appliances;
    }

    public ApplianceCategories appliances(Set<ApplianceDetails> applianceDetails) {
        this.appliances = applianceDetails;
        return this;
    }

    public ApplianceCategories addAppliances(ApplianceDetails applianceDetails) {
        this.appliances.add(applianceDetails);
        applianceDetails.setCategories(this);
        return this;
    }

    public ApplianceCategories removeAppliances(ApplianceDetails applianceDetails) {
        this.appliances.remove(applianceDetails);
        applianceDetails.setCategories(null);
        return this;
    }

    public void setAppliances(Set<ApplianceDetails> applianceDetails) {
        this.appliances = applianceDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplianceCategories)) {
            return false;
        }
        return id != null && id.equals(((ApplianceCategories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplianceCategories{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", categoryDesc='" + getCategoryDesc() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
