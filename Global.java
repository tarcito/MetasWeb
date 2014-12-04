import models.dao.GenericDAO;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import models.Meta;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    @Override
    public void onStart(Application app) {
        JPA.withTransaction(new play.libs.F.Callback0() {
            
        	public void invoke() throws Throwable {
                Meta meta0 = new Meta("Fazer o lab2 de SI1",0);
                Calendar data0 = new GregorianCalendar(2014, 11, 05, 23, 00);
                data0.setFirstDayOfWeek(Calendar.SUNDAY);
                meta0.setPrazo(data0);
                meta0.setSemana(getSemana(data0));
                
                //Meu irmao que deu a ideia
                Meta meta1 = new Meta("Assistir a Novela da Globo",-1);
                Calendar data1 = new GregorianCalendar(2015, 01, 27, 19, 00);
                data1.setFirstDayOfWeek(Calendar.SUNDAY);
                meta1.setPrazo(data1);
                meta1.setSemana(getSemana(data1));

                Meta meta2 = new Meta("Comemorar o Natal",0);
                Calendar data2 = new GregorianCalendar(2014, 11, 25, 23, 00);
                data2.setFirstDayOfWeek(Calendar.SUNDAY);
                meta2.setPrazo(data2);
                meta2.setSemana(getSemana(data2));

                Meta meta3 = new Meta("Comemorar o Reveillon",0);
                Calendar data3 = new GregorianCalendar(2014, 11, 31, 23, 00);
                data3.setFirstDayOfWeek(Calendar.SUNDAY);
                meta3.setPrazo(data3);
                meta3.setSemana(getSemana(data3));

                Meta meta4 = new Meta("Jogar o LOLzinho",-1);
                Calendar data4 = new GregorianCalendar(2014, 11, 23, 8, 00);
                data4.setFirstDayOfWeek(Calendar.SUNDAY);
                meta4.setPrazo(data4);
                meta4.setSemana(getSemana(data4));

                Meta meta5 = new Meta("Formatar o PC",0);
                Calendar data5 = new GregorianCalendar(2015, 00, 25, 10, 00);
                data5.setFirstDayOfWeek(Calendar.SUNDAY);
                meta5.setPrazo(data5);
                meta5.setSemana(getSemana(data5));

                Meta meta6 = new Meta("Estudar Para a Prova de Fisica moderna",0);
                Calendar data6 = new GregorianCalendar(2014, 11, 18, 14, 00);
                data6.setFirstDayOfWeek(Calendar.SUNDAY);
                meta6.setPrazo(data6);
                meta6.setSemana(getSemana(data6));

                Meta meta7 = new Meta("Entrar no Piazza",0);
                Calendar data7 = new GregorianCalendar(2014, 11, 22, 23, 00);
                data7.setFirstDayOfWeek(Calendar.SUNDAY);
                meta7.setPrazo(data7);
                meta7.setSemana(getSemana(data7));

                Meta meta8 = new Meta("Inicio do Recesso Academico",0);
                Calendar data8 = new GregorianCalendar(2014, 11, 22, 18, 00);
                data8.setFirstDayOfWeek(Calendar.SUNDAY);
                meta8.setPrazo(data8);
                meta8.setSemana(getSemana(data8));

                Meta meta9 = new Meta("Reinicio do Periodo Letivo",0);
                Calendar data9 = new GregorianCalendar(2015, 00, 26, 6, 00);
                data9.setFirstDayOfWeek(Calendar.SUNDAY);
                meta9.setPrazo(data9);
                meta9.setSemana(getSemana(data9));

                dao.persist(meta0);
                dao.persist(meta1);
                dao.persist(meta2);
                dao.persist(meta3);
                dao.persist(meta4);
                dao.persist(meta5);
                dao.persist(meta6);
                dao.persist(meta7);
                dao.persist(meta8);
                dao.persist(meta9);
                dao.flush();
            }
        });
    }
    
    private static int getSemana(Calendar date) {
		Calendar atual = (GregorianCalendar) GregorianCalendar.getInstance();
		atual.setFirstDayOfWeek(Calendar.SUNDAY);
    	if (date.compareTo(atual) > 0 ) {
			if(date.get(Calendar.WEEK_OF_YEAR) < atual.get(Calendar.WEEK_OF_YEAR)){
				return date.get(Calendar.WEEK_OF_YEAR) + ( atual.getActualMaximum(Calendar.WEEK_OF_YEAR)- atual.get(Calendar.WEEK_OF_YEAR));
			}else if(date.get(Calendar.WEEK_OF_YEAR) > atual.get(Calendar.WEEK_OF_YEAR)){
	    		return date.get(Calendar.WEEK_OF_YEAR) - atual.get(Calendar.WEEK_OF_YEAR);
	    	}
    	}
		return 0;
	} 
}
