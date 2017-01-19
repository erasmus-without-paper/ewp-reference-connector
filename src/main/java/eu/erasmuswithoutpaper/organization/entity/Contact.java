
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = Contact.findAll, query = "SELECT c FROM Contact c"),
    @NamedQuery(name = Contact.findByInstAndOrgUnit, query = "SELECT c FROM Contact c WHERE c.institutionId = :institutionId AND c.organizationUnitId = :organizationUnitId"),
    @NamedQuery(name = Contact.findByInstWithNoOrgUnit, query = "SELECT c FROM Contact c WHERE c.institutionId = :institutionId AND c.organizationUnitId is NULL")
})
public class Contact implements Serializable {
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.organization.entity.Contact.";
    public static final String findAll = PREFIX + "all";
    public static final String findByInstAndOrgUnit = PREFIX + "byInstAndOrgUnit";
    public static final String findByInstWithNoOrgUnit = PREFIX + "byInstWithNoOrgUnit";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String institutionId;
    private String organizationUnitId;
    
    @Column(name = "contact_role")
    private String role;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "contact_name")
    private List<LanguageItem> name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "contact_description")
    private List<LanguageItem> description;
    
    @ElementCollection
    private List<String> email;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "contact_url")
    private List<LanguageItem> url;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "phone_number")
    private PhoneNumber phoneNumber;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fax_number")
    private PhoneNumber faxNumber;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address")
    private FlexibleAddress address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<LanguageItem> getName() {
        return name;
    }

    public void setName(List<LanguageItem> name) {
        this.name = name;
    }

    public List<LanguageItem> getDescription() {
        return description;
    }

    public void setDescription(List<LanguageItem> description) {
        this.description = description;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<LanguageItem> getUrl() {
        return url;
    }

    public void setUrl(List<LanguageItem> url) {
        this.url = url;
    }

    public PhoneNumber getPhone() {
        return phoneNumber;
    }

    public void setPhone(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber getFax() {
        return faxNumber;
    }

    public void setFax(PhoneNumber faxNumber) {
        this.faxNumber = faxNumber;
    }

    public FlexibleAddress getAddress() {
        return address;
    }

    public void setAddress(FlexibleAddress address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Contact other = (Contact) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
