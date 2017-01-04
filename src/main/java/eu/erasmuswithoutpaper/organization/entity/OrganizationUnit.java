
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = OrganizationUnit.findByOrganizationUnitId, query = "SELECT o FROM OrganizationUnit o WHERE o.organizationUnitId = :organizationUnitId")
})
public class OrganizationUnit implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.organization.entity.OrganizationUnit.";
    public static final String findByOrganizationUnitId = PREFIX + "byOrganizationUnitId";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String organizationUnitId;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "ou_other_id")
    private List<IdentificationItem> otherId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "organization_unit_name")
    private List<LanguageItem> name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "org_unit_org_unit")
    private List<OrganizationUnit> organizationUnits;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "street_address")
    private FlexibleAddress streetAddress;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "mailing_address")
    private FlexibleAddress mailingAddress;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "ou_website_url")
    private List<LanguageItem> websiteUrl;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "ou_factsheet_url")
    private List<LanguageItem> factsheetUrl;
    
    private String logoUrl;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public List<IdentificationItem> getOtherId() {
        return otherId;
    }

    public void setOtherId(List<IdentificationItem> otherId) {
        this.otherId = otherId;
    }

    public List<LanguageItem> getName() {
        return name;
    }

    public void setName(List<LanguageItem> name) {
        this.name = name;
    }

    public List<OrganizationUnit> getOrganizationUnits() {
        return organizationUnits;
    }

    public void setOrganizationUnits(List<OrganizationUnit> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

    public FlexibleAddress getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(FlexibleAddress streetAddress) {
        this.streetAddress = streetAddress;
    }

    public FlexibleAddress getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(FlexibleAddress mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<LanguageItem> getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(List<LanguageItem> websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public List<LanguageItem> getFactsheetUrl() {
        return factsheetUrl;
    }

    public void setFactsheetUrl(List<LanguageItem> factsheetUrl) {
        this.factsheetUrl = factsheetUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrganizationUnit other = (OrganizationUnit) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
