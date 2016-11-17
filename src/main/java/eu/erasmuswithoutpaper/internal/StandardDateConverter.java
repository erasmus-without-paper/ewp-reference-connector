package eu.erasmuswithoutpaper.internal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.DatatypeConverter;
import org.apache.johnzon.mapper.Converter;
 
public class StandardDateConverter implements Converter<Date> {
    @Override
    public String toString(final Date instance) {
        final Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(instance);
        return DatatypeConverter.printDateTime(cal);
    }
 
    @Override
    public Date fromString(final String text) {
        return DatatypeConverter.parseDateTime(text).getTime();
    }
}