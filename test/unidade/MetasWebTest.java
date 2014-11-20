package unidade;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import javax.persistence.EntityManager;
import models.Objetivo;
import models.dao.GenericDAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.GlobalSettings;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

public class MetasWebTest {
	
	private GenericDAO dao = new GenericDAO();

	@Test
	public void deveIniciarSemObjetivosNoBD(){
		List<Objetivo> objetivos = dao.findAllByClassName(Objetivo.class.getName());
		assertThat(objetivos).isEmpty();
	}
	
	
	@Test
	public void deveCriarObjetivosNoBD() {
		Objetivo objetivo = new Objetivo("fazer o lab2 de SI1");
		dao.persist(objetivo);
		
		List<Objetivo> objetivos = dao.findAllByClassName(Objetivo.class.getName());
		assertThat(objetivos.size()).isEqualTo(1);
		assertThat(objetivos.get(0).getObjetivo()).isEqualTo("fazer o lab2 de SI1");
	}
	
	
	
	 public EntityManager em;

	    @Before
	    public void setUp() {
	        FakeApplication app = Helpers.fakeApplication(new GlobalSettings());
	        Helpers.start(app);
	        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
	        em = jpaPlugin.get().em("default");
	        JPA.bindForCurrentThread(em);
	        em.getTransaction().begin();
	    }

	    @After
	    public void tearDown() {
	        em.getTransaction().commit();
	        JPA.bindForCurrentThread(null);
	        em.close();
	    }
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
}
