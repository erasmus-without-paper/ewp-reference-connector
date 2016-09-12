package eu.erasmuswithoutpaper.institutions.control;

import eu.erasmuswithoutpaper.api.institutions.Response;
import eu.erasmuswithoutpaper.api.institutions.Response.Institution;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InstitutionController {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public Response getAllInstitutions() {
        Response response = new Response();
        List<eu.erasmuswithoutpaper.institutions.entity.Institution> list = em.createQuery("select a from Institution a", eu.erasmuswithoutpaper.institutions.entity.Institution.class).getResultList();
        list.stream().forEach(i -> {
            Response.Institution institution = new Response.Institution();
            institution.setCountry(i.getCountry());
            institution.setId(i.getIid());
            Response.Institution.Name name = new Response.Institution.Name();
            name.setLang("en");
            name.setValue(i.getName());
            institution.setName(name);
            institution.setUrl(i.getUrl());
            response.getInstitution().add(institution);
        });
        
        return response;
    }
    
    public Response getInstitutionsForHeis() {
        return new Response();
    }
}
