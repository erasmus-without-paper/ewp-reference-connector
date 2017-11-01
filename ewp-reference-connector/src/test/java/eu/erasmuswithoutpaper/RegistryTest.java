package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.registryclient.ApiSearchConditions;
import eu.erasmuswithoutpaper.registryclient.ClientImpl;
import eu.erasmuswithoutpaper.registryclient.ClientImplOptions;
import eu.erasmuswithoutpaper.registryclient.RegistryClient;
import java.io.StringWriter;
import java.util.Collection;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.Test;
import org.w3c.dom.Element;

public class RegistryTest {

	@Test
	public void testRegistry() throws RegistryClient.RefreshFailureException, TransformerException {
		try {
			ClientImplOptions options = new ClientImplOptions();
			ClientImpl client = new ClientImpl(options);
			client.refresh();
			Collection<Element> joddel = client.findApis(new ApiSearchConditions());
			for (Element element : joddel) {
				System.err.println("Element: " + element);
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				StreamResult result = new StreamResult(new StringWriter());
				DOMSource source = new DOMSource(element);
				transformer.transform(source, result);
				String xmlString = result.getWriter().toString();
				System.out.println(xmlString);

			}

			System.err.println(client.getExpiryDate());
		} catch (Exception e) {
			System.err.println("Fel vid hämtning av registret" + e);
                        e.printStackTrace();
		}
	}
}
