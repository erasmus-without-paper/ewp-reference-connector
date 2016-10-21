/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.department.boundary;

import eu.erasmuswithoutpaper.api.department.Response;
import eu.erasmuswithoutpaper.department.control.DepartmentController;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * Department (OrganizationUnit).
 */
@Stateless
@Path("department")
public class DepartmentResource {
    
    @Inject
    private DepartmentController departmentController;

    @GET
    @Path("get")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getGet(@QueryParam("hei_id") String heiId, @QueryParam("department_id") List<String> departmentIdList) {
        return departmentController.getAllDepartments();
    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public Response getPost(@FormParam("hei_id") String heiId, @FormParam("department_id") List<String> departmentIdList) {
        return departmentController.getAllDepartments();
    }
}
