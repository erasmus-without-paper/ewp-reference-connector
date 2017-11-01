package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.httpsig.CliauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.none.CliauthAnonymous;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.tlscert.CliauthTlscert;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.httpsig.SrvauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.tlscert.SrvauthTlscert;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Provider
@Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class JaxbContextResolver implements ContextResolver<JAXBContext> {

  @Override
  public JAXBContext getContext(Class<?> type) {

    try {
      return JAXBContext.newInstance(type,
              CliauthTlscert.class,
              CliauthAnonymous.class,
              CliauthHttpsig.class,
              SrvauthHttpsig.class,
              SrvauthTlscert.class);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }
}