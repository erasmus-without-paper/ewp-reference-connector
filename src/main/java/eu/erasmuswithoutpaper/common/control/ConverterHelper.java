
package eu.erasmuswithoutpaper.common.control;

import eu.erasmuswithoutpaper.api.architecture.HTTPWithOptionalLang;
import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.types.address.FlexibleAddress;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;
import eu.erasmuswithoutpaper.organization.entity.LanguageItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class ConverterHelper {
    public static FlexibleAddress convertToFlexibleAddress(eu.erasmuswithoutpaper.organization.entity.FlexibleAddress flexibleAddress) {
        if (flexibleAddress == null) {
            return null;
        }
        
        FlexibleAddress address = new FlexibleAddress();
        Optional.ofNullable(flexibleAddress.getAddressLine()).ifPresent(address.getAddressLine()::addAll);
        Optional.ofNullable(flexibleAddress.getDeliveryPointCode()).ifPresent(address.getDeliveryPointCode()::addAll);
        Optional.ofNullable(flexibleAddress.getRecipientName()).ifPresent(address.getRecipientName()::addAll);
        address.setBuildingName(flexibleAddress.getBuildingName());
        address.setBuildingNumber(flexibleAddress.getBuildingNumber());
        address.setCountry(flexibleAddress.getCountry());
        address.setFloor(flexibleAddress.getFloor());
        address.setLocality(flexibleAddress.getLocality());
        address.setPostOfficeBox(flexibleAddress.getPostOfficeBox());
        address.setPostalCode(flexibleAddress.getPostalCode());
        address.setRegion(flexibleAddress.getRegion());
        address.setStreetName(flexibleAddress.getStreetName());
        address.setUnit(flexibleAddress.getUnit());
        
        return address;
    }
    
    public static List<PhoneNumber> convertToPhoneNumber(eu.erasmuswithoutpaper.organization.entity.PhoneNumber phoneNumber) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        if (phoneNumber != null) {
            PhoneNumber pn = new PhoneNumber();
            pn.setE164(phoneNumber.getE164());
            pn.setExt(phoneNumber.getExtensionNumber());
            pn.setOtherFormat(phoneNumber.getOtherFormat());
            phoneNumbers.add(pn);
        }
        
        return phoneNumbers;
    }
    
    public static List<StringWithOptionalLang> convertToStringWithOptionalLang(List<LanguageItem> languageItems) {
        return
            languageItems.stream().map((languageItem) -> {
                StringWithOptionalLang stringWithOptionalLang = new StringWithOptionalLang();
                stringWithOptionalLang.setLang(languageItem.getLang());
                stringWithOptionalLang.setValue(languageItem.getText());
                return stringWithOptionalLang;
            }).collect(Collectors.toList());
    }

    public static List<HTTPWithOptionalLang> convertToHttpWithOptionalLang(List<LanguageItem> languageItems) {
        return languageItems.stream().map((languageItem) -> {
            HTTPWithOptionalLang httpWithOptionalLang = new HTTPWithOptionalLang();
            httpWithOptionalLang.setLang(languageItem.getLang());
            httpWithOptionalLang.setValue(languageItem.getText());
            return httpWithOptionalLang;
        }).collect(Collectors.toList());
    }

    public static XMLGregorianCalendar convertToXmlGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return date2;
    }
}
