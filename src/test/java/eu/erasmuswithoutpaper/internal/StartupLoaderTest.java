package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.course.preload.AcademicTermLoader;
import eu.erasmuswithoutpaper.course.preload.AcademicYearLoader;
import eu.erasmuswithoutpaper.course.preload.LoiLoader;
import eu.erasmuswithoutpaper.course.preload.LosLoader;
import eu.erasmuswithoutpaper.iia.preload.IiaLoader;
import eu.erasmuswithoutpaper.iia.preload.MobilityTypeLoader;
import eu.erasmuswithoutpaper.mobility.preload.MobilityLoader;
import eu.erasmuswithoutpaper.organization.preload.ContactLoader;
import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
import eu.erasmuswithoutpaper.organization.preload.MobilityParticipantLoader;
import eu.erasmuswithoutpaper.organization.preload.PersonLoader;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartupLoaderTest {
    EntityManager em;
    EntityTransaction tx;
    StartupLoader startupLoader;

    @Before
    public void setUp() {
        this.em = Persistence.createEntityManagerFactory("connector-test").createEntityManager();
        this.tx = this.em.getTransaction();
       
        startupLoader = new StartupLoader();
        startupLoader.properties = mock(GlobalProperties.class);
        startupLoader.institutionLoader = new InstitutionLoader();
        startupLoader.personLoader = new PersonLoader();
        startupLoader.contactLoader = new ContactLoader();
        startupLoader.mobilityParticipantLoader = new MobilityParticipantLoader();
        startupLoader.academicTermLoader = new AcademicTermLoader();
        startupLoader.academicYearLoader = new AcademicYearLoader();
        startupLoader.mobilityTypeLoader = new MobilityTypeLoader();
        startupLoader.mobilityLoader = new MobilityLoader();
        startupLoader.learningOppSpecLoader = new LosLoader();
        startupLoader.learningOppInstLoader = new LoiLoader();
        startupLoader.iiaLoader = new IiaLoader();

        startupLoader.setEntityManger(em);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLoadDemoData_IKEA_U() throws Exception {
        when(startupLoader.properties.getUniversity()).thenReturn(GlobalProperties.University.IKEA_U);
        loadDemoData("Exception loading Ikea");
    }
    
    @Test
    public void testLoadDemoData_POMODORO_U() throws Exception {
        when(startupLoader.properties.getUniversity()).thenReturn(GlobalProperties.University.POMODORO_U);
        loadDemoData("Exception loading Pomodoro");
    }
    
    private void loadDemoData(String message) {
        try {
            this.tx.begin();
            startupLoader.loadDemoData();
            this.tx.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            this.tx.rollback();
            fail(message);
        }
    }
}
