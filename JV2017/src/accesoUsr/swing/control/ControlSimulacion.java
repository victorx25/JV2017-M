/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de una simulación. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlSimulación.java 
 *  @version: 2.2 - 2018.06.01
 *  @author: ajp
 */

package accesoUsr.swing.control;

import accesoDatos.Datos;
import accesoUsr.swing.vista.VistaSimulacion;
import modelo.Mundo;
import modelo.Simulacion;

public class ControlSimulacion {
	Datos datos = new Datos();
	final int CICLOS = 120;
	VistaSimulacion vistaSimulacion;
	Simulacion simulacion;
	Mundo mundo;
	
	public ControlSimulacion(Simulacion simulacion) {
		this.simulacion = simulacion;
		initControlSimulacion();
	}
	
	private void initControlSimulacion() {	
		mundo = simulacion.getMundo();	
		vistaSimulacion = new VistaSimulacion();
		configListener();
		vistaSimulacion.pack();
		vistaSimulacion.setVisible(true);
		arrancarSimulacion();
	}
	
	private void configListener() {
		// Hay que escuchar todos los componentes que tengan interacción de la vista
		// registrándoles la clase control que los escucha.
		//vistaSimulacion.getBotonOk().addActionListener(this);
		//vistaSimulacion.getBotonCancelar().addActionListener(this);
		//vistaSimulacion.getCampoUsuario().addActionListener(this);
		//vistaSimulacion.getCampoClaveAcceso().addActionListener(this);
		//vistaSimulacion.getLblAyuda().addMouseListener(this);
}
	
	/**
	 * Ejecuta una simulación del juego de la vida, en la consola,
	 * durante un número de CICLOS.
	 */
	public void arrancarSimulacion() {
		int gen = 0; 		//Generaciones
		do {
			vistaSimulacion.getTextAreaVisualizacion().append("\nGeneración: " + gen + "\n");
			vistaSimulacion.mostrarSimulacion(this);
			mundo.actualizarMundo();
			gen++;
		}
		while (gen <= CICLOS);
	}
	
	public Simulacion getSimulacion() {
		return simulacion;
	}
	
	public Mundo getMundo() {
		return mundo;
	}
	
} // class
