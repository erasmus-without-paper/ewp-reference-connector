
package eu.erasmuswithoutpaper.organization.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represetation of phone numbers and fax numbers.
 */

@Entity
public class PhoneNumber implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    private String e164;
    private String extensionNumber;
    private String otherFormat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getE164() {
        return e164;
    }

    public void setE164(String e164) {
        this.e164 = e164;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getOtherFormat() {
        return otherFormat;
    }

    public void setOtherFormat(String otherFormat) {
        this.otherFormat = otherFormat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final PhoneNumber other = (PhoneNumber) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
