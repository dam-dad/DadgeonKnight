package nx.util.report;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import nx.engine.scenes.FinalScene;
import nx.game.Main;

public class JasperGenerator {
	public static void generarPdf() throws JRException, IOException {

		// compila el informe
		JasperReport report = JasperCompileManager.compileReport(Main.class.getResourceAsStream("/reports/Score.jrxml"));		

		// mapa de parámetros para el informe
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("USER_SCORE", FinalScene.time); // no lo uso, pero se lo paso
		parameters.put("USER_NAME", FinalScene.username); // no lo uso, pero se lo paso
		
		// generamos el informe (combinamos el informe compilado con los datos) 
	    JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        
        // exporta el informe a un fichero PDF
        JasperExportManager.exportReportToPdfFile(print, "pdf/juegos.pdf");
        
        // Abre el archivo PDF generado con el programa predeterminado del sistema
//		Desktop.getDesktop().open(new File("pdf/juegos.pdf"));
	}

}
