package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = ContactDetails.findAll, query = "SELECT c FROM ContactDetails c")
})
public class ContactDetails implements Serializable {
    private static final String PREFIX = "eu.erasmuswithoutpaper.organization.entity.ContactDetails.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    @ElementCollection
    private List<String> email;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "CONTACT_URL")
    private List<LanguageItem> url;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PHONE_NUMBER")
    private PhoneNumber phoneNumber;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FAX_NUMBER")
    private PhoneNumber faxNumber;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "STREET_ADDRESS")
    private FlexibleAddress streetAddress;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "MAILING_ADDRESS")
    private FlexibleAddress mailingAddress;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(PhoneNumber faxNumber) {
        this.faxNumber = faxNumber;
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
    
    @Override
    public int hashCode() {
        int hash = 7;
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
        final ContactDetails other = (ContactDetails) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
