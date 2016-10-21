/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.department.control;

import eu.erasmuswithoutpaper.api.department.Response;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * DepartmentController.
 */
public class DepartmentController {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    public Response getAllDepartments() {
        Response response = new Response();
        List<eu.erasmuswithoutpaper.department.entity.Department> list;
        list = em.createQuery("select a from Department a", eu.erasmuswithoutpaper.department.entity.Department.class).getResultList();
        list.stream().forEach(i -> {
            
            Response.OrganizationUnit department = new Response.OrganizationUnit();
            department.setId(i.getDepartmentId());
            department.setOwnerUnitId(i.getOwnerUnitId());
            
            Response.OrganizationUnit.Name name = new Response.OrganizationUnit.Name();
            name.setLang("en");
            name.setValue(i.getName());
            department.setName(name);
            
            Response.OrganizationUnit.Address address = new Response.OrganizationUnit.Address();
            address.setCity(i.getAddress().getCity());
            address.setStreet(i.getAddress().getStreet());
            address.setPostCode(i.getAddress().getPostCode());
            department.setAddress(address);
            
            department.setCountry(i.getCountry());
            department.setUrl(i.getUrl());
            
            Response.OrganizationUnit.Description description = new Response.OrganizationUnit.Description();
            description.setLang("en");
            description.setValue(i.getDescription());
            department.setDescription(description);
            
            Response.OrganizationUnit.RecLangComp recLangComp = new Response.OrganizationUnit.RecLangComp();
            recLangComp.setLang(i.getRecLangComp().getLang());
            recLangComp.setLevel(i.getRecLangComp().getLevel());
            department.setRecLangComp(recLangComp);
            
            response.getOrganizationUnit().add(department);
        });
        
        return response;
    }
}
