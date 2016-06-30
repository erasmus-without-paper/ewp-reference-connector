package eu.erasmuswithoutpaper.boundary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("")
public class ManifestResource {

    @GET
    @Path("manifest")
    @Produces(MediaType.APPLICATION_XML)
    public String manifest() throws URISyntaxException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource("manifest.xml")
                .toURI())));
        return content;
    }
}
