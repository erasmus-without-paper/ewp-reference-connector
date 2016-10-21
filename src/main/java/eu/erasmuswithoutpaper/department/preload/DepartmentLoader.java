/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.erasmuswithoutpaper.department.preload;

import eu.erasmuswithoutpaper.department.entity.Department;
import eu.erasmuswithoutpaper.internal.JsonToObject;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * DepartmentLoader.
 */
public class DepartmentLoader {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public void createDemoData() {
        
        try {
            persistDepartment("{'ownerUnitId':'owner123', 'name':'computing science', 'address':{'street':'Education Road 1', 'city':'Umeå', 'postCode':'1'}, "
                    + "'country':'se', 'url':'www.umu.se', 'description':'CS Department for programming students', 'recLangComp':{'lang':'en', 'level':'0'}}");
            
        } catch (IOException ex) {
            Logger.getLogger(DepartmentLoader.class.getName()).log(Level.SEVERE, "Could not load Department json to object", ex);
        }
    }
    
    private void persistDepartment(String departmentJson) throws IOException {
        Department department = JsonToObject.mapToObject(Department.class, departmentJson);
        department.setDepartmentId(UUID.randomUUID().toString());
        em.persist(department);
    }
}
