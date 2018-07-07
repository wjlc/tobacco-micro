package com.alauda.tobacco.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Commerce.
 */
@Entity
@Table(name = "commerce")
public class Commerce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "companyname")
    private String companyname;

    @Column(name = "retailname")
    private String retailname;

    @ManyToOne
    private Industry industry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public Commerce companyname(String companyname) {
        this.companyname = companyname;
        return this;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getRetailname() {
        return retailname;
    }

    public Commerce retailname(String retailname) {
        this.retailname = retailname;
        return this;
    }

    public void setRetailname(String retailname) {
        this.retailname = retailname;
    }

    public Industry getIndustry() {
        return industry;
    }

    public Commerce industry(Industry industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commerce commerce = (Commerce) o;
        if (commerce.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerce.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commerce{" +
            "id=" + getId() +
            ", companyname='" + getCompanyname() + "'" +
            ", retailname='" + getRetailname() + "'" +
            "}";
    }
}
