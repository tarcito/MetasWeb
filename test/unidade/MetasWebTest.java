package unidade;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import models.Meta;
import models.Meta.PrioridadeEnum;
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
	public void deveIniciarSemMetasNoBD(){
		List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
		assertThat(metas).isEmpty();
	}
	
	
	@Test
	public void deveCriarMetasNoBD() {
		Meta meta = new Meta("fazer o lab2 de SI1", 0);
		dao.persist(meta);
		
		List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
		assertThat(metas.size()).isEqualTo(1);
		assertThat(metas.get(0).getDescricao()).isEqualTo("fazer o lab2 de SI1");
		assertThat(metas.get(0).getPrioridade()).isEqualTo(PrioridadeEnum.Normal);
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
