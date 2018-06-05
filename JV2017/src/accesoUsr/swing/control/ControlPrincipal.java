/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  principal del programa con un menú. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlPrincipal.java 
 *  @version: 2.2 - 2018.05.17
 *  @author: ajp
 */

package accesoUsr.swing.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoUsr.swing.vista.VistaPrincipal;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.Simulacion;

public class ControlPrincipal implements ActionListener, WindowListener {

	private VistaPrincipal vistaPrincipal;
	private SesionUsuario sesionUsr;
	private Datos fachada;

	public ControlPrincipal() {
		initControlPrincipal();	
	}

	private void initControlPrincipal() {
		fachada = new Datos();
		vistaPrincipal = new VistaPrincipal();
		configListener();
		vistaPrincipal.pack();
		vistaPrincipal.setVisible(true);
		sesionUsr = new ControlInicioSesion().getSesion();
	}
	
	private void configListener() {
		// Hay que escuchar todos los componentes que tengan interacción de la vista
		// registrándoles la clase control que los escucha.
		vistaPrincipal.addWindowListener(this);
		vistaPrincipal.getMntnGuardar().addActionListener(this);
		vistaPrincipal.getMntnSalir().addActionListener(this);
		
		vistaPrincipal.getMntnCrearNuevaSimulacion().addActionListener(this);
		vistaPrincipal.getMntnEliminarSimulacion().addActionListener(this);
		vistaPrincipal.getMntnModificarSimulacion().addActionListener(this);
		vistaPrincipal.getMntnMostrarDatosSimulacion().addActionListener(this);
		vistaPrincipal.getMntnDemoSimulacion().addActionListener(this);
		
		vistaPrincipal.getMntnCrearNuevoMundo().addActionListener(this);
		vistaPrincipal.getMntnEliminarMundo().addActionListener(this);
		vistaPrincipal.getMntnModificarMundo().addActionListener(this);
		vistaPrincipal.getMntnMostrarDatosMundo().addActionListener(this);
		
		vistaPrincipal.getMntnCrearNuevoUsuario().addActionListener(this);
		vistaPrincipal.getMntnEliminarUsuario().addActionListener(this);
		vistaPrincipal.getMntnModificarUsuario().addActionListener(this);
		vistaPrincipal.getMntnMostrarDatosUsuario().addActionListener(this);
		
		vistaPrincipal.getMntnEliminarSesion().addActionListener(this);
		vistaPrincipal.getMntnModificarSesion().addActionListener(this);
		vistaPrincipal.getMntnMostrarDatosSesion().addActionListener(this);
		
		vistaPrincipal.getMntnAcercaDe().addActionListener(this);
		
		//...
	}

	//Manejador de eventos de componentes... ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == vistaPrincipal.getMntnGuardar()) {
			// Ejecutar método asociado
		}
		
		if(e.getSource() == vistaPrincipal.getMntnSalir()) {
			salir();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnCrearNuevaSimulacion()) {
			crearNuevaSimulacion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnEliminarSimulacion()) {
			eliminarSimulacion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnModificarSimulacion()) {
			modificarSimulacion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnMostrarDatosSimulacion()) {
			mostrarDatosSimulacion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnDemoSimulacion()) {
			ejecutarDemoSimulacion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnCrearNuevoMundo()) {
			crearNuevoMundo();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnEliminarMundo()) {
			eliminarMundo();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnModificarMundo()) {
			modificarMundo();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnMostrarDatosMundo()) {
			mostrarDatosMundo();
		}
	
		if(e.getSource() == vistaPrincipal.getMntnCrearNuevoUsuario()) {
			crearNuevoUsuario();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnEliminarUsuario()) {
			eliminarUsuario();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnModificarUsuario()) {
			modificarUsuario();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnMostrarDatosUsuario()) {
			mostrarDatosUsuario();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnEliminarSesion()) {
			eliminarSesion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnModificarSesion()) {
			modificarSesion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnMostrarDatosSesion()) {
			mostrarDatosSesion();
		}
		
		if(e.getSource() == vistaPrincipal.getMntnAcercaDe()) {
			vistaPrincipal.muestraDialogoAcercaDe();
		}
	}
	
	// Salida segura única de la aplicación
	private void salir() {
		// Confirmar cierre
		if (vistaPrincipal.mensajeConfirmacion("Confirma que quieres salir...")) {
			fachada.cerrar();
			// Cierra la aplicación
			System.exit(0);
		}
	}
	
	// Simulaciones
	private void crearNuevaSimulacion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void modificarSimulacion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarSimulacion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void mostrarDatosSimulacion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}

	private void ejecutarDemoSimulacion() {
		ArrayList<Simulacion> simulacionesUsrActivo = null;
		try {
			simulacionesUsrActivo = new ArrayList<Simulacion>(fachada.obtenerSimulacionesUsuario("III1R"));
		} catch (ModeloException | DatosException e) {
			e.printStackTrace();
		}
		// La simulación predeterminada-demo es la primera del usuario predeterminado Invitado
		new ControlSimulacion(simulacionesUsrActivo.get(0));
		
	}
	
	// Mundos
	private void crearNuevoMundo() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		// ControlUsuarios controlUsuarios = new ControlUsuarios();
		
	}

	private void modificarMundo() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarMundo() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void mostrarDatosMundo() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	// Usuarios	
	private void crearNuevoUsuario() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void modificarUsuario() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarUsuario() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void mostrarDatosUsuario() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	// Sesiones	
	private void modificarSesion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarSesion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void mostrarDatosSesion() {
		vistaPrincipal.mostrarMensaje("Opción no disponible...");
		
	}
	
	// Manejadores de eventos de ventana... Wnidowslistener
	@Override
	public void windowClosing(WindowEvent arg0) {
		salir();
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// No usado
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// No usado
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// No usado
	}
	
} // class
