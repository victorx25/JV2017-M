/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de inicio de sesión de usuario. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlInicioSesion.java 
 *  @version: 2.2 - 2018.05.25
 *  @author: ajp
 */

package accesoUsr.swing.control;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoUsr.swing.vista.VistaInicioSesion;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Usuario;
import util.Fecha;

public class ControlInicioSesion implements ActionListener, MouseListener {
	private int maxIntentosFallidos;
	private VistaInicioSesion vistaInicioSesion;
	private Usuario usrSesion;
	private SesionUsuario sesion;
	private Datos fachada;

	public ControlInicioSesion() {
		initControlSesion();
	}

	private void initControlSesion() {
		maxIntentosFallidos = new Integer(Configuracion.get().getProperty("sesion.intentosPermitidos"));	
		fachada = new Datos();
		vistaInicioSesion = new VistaInicioSesion();
		vistaInicioSesion.setModalityType(ModalityType.MODELESS);
		configListener();
		vistaInicioSesion.pack();
		vistaInicioSesion.setVisible(true);
	}

	private void configListener() {
		// Hay que escuchar todos los componentes que tengan interacción de la vista
		// registrándoles la clase control que los escucha.
		vistaInicioSesion.getBotonOk().addActionListener(this);
		vistaInicioSesion.getBotonCancelar().addActionListener(this);
		vistaInicioSesion.getCampoUsuario().addActionListener(this);
		vistaInicioSesion.getCampoClaveAcceso().addActionListener(this);
		vistaInicioSesion.getLblAyuda().addMouseListener(this);
	}

	//Manejador de eventos de componentes... ActionListener
	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() == vistaInicioSesion.getBotonOk()) {
			iniciarSesionUsuario();
		}

		if(evento.getSource() == vistaInicioSesion.getBotonCancelar()) {
			fachada.cerrar();
			System.exit(0);
		}

		if(evento.getSource() == vistaInicioSesion.getCampoUsuario()) {
			vistaInicioSesion.getCampoClaveAcceso().requestFocus();
		}

		if(evento.getSource() == vistaInicioSesion.getCampoClaveAcceso()) {
			iniciarSesionUsuario();
		}
	}

	//Manejador de evento de raton...
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == vistaInicioSesion.getLblAyuda()) {
			vistaInicioSesion.mostrarMensaje("Sólo pueden iniciar sesión los usuarios registrados...");
		}
	}

	/**
	 * Controla el acceso de usuario 
	 * y registro de la sesión correspondiente.
	 * @throws DatosException 
	 */
	private void iniciarSesionUsuario() {	
		String credencialUsr = vistaInicioSesion.getCampoUsuario().getText().toUpperCase();
		String clave = new String(vistaInicioSesion.getCampoClaveAcceso().getPassword());
		try {
			usrSesion = fachada.obtenerUsuario(credencialUsr);
		} catch (DatosException e) {
			e.printStackTrace();
		}
		if ( usrSesion != null) {			
			try {
				if (usrSesion.getClaveAcceso().equals(new ClaveAcceso(clave))) {
					registrarSesion();
					vistaInicioSesion.dispose();
					return;
				}
			} 
			catch (ModeloException e) {
				//e.printStackTrace();
			}
		}
		maxIntentosFallidos--;
		vistaInicioSesion.mostrarMensaje("Credenciales incorrectas...\n"
				+ "Quedan " + maxIntentosFallidos + " intentos... ");
		vistaInicioSesion.getCampoUsuario().setText("");
		vistaInicioSesion.getCampoClaveAcceso().setText("");

		if (maxIntentosFallidos <= 0){
			vistaInicioSesion.mostrarMensaje("Fin del programa...");
			fachada.cerrar();
			System.exit(0);	
		}
	}

	public SesionUsuario getSesion() {
		return sesion;
	}

	/**
	 * Crea la sesion de usuario 
	 */
	private void registrarSesion() {
		// Registra sesión.
		// Crea la sesión de usuario en el sistema.
		try {
			sesion = new SesionUsuario(usrSesion, new Fecha(), EstadoSesion.ACTIVA);
			fachada.altaSesion(sesion);
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}	
		vistaInicioSesion.mostrarMensaje("Sesión: " + sesion.getIdSesion()
		+ '\n' + "Iniciada por: " + usrSesion.getNombre());	
	}

	// Manejadores de eventos de ratón no usados.
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

} //class
