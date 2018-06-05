/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la presentación 
 *  de una simulación. 
 *  Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: VistaSimulacionTexto.java 
 *  @version: 2.2 - 2018.06.01
 *  @author: ajp
 */

package accesoUsr.swing.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import accesoUsr.OperacionesVista;
import accesoUsr.swing.control.ControlSimulacion;
import config.Configuracion;

public class VistaSimulacion extends JFrame implements OperacionesVista {

	private static final long serialVersionUID = 1L;
	final int CICLOS = 120;
	private JPanel panelControles;
	private JPanel panelSimulacion;
	private JTextArea textAreaVisualizacion;
	private JToolBar toolBar;
	private JPanel panelEstado;
	private JPanel panelVisualizacion;
	
	public VistaSimulacion() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Simulación JV");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelControles = new JPanel();
		getContentPane().add(panelControles, BorderLayout.NORTH);
		
		panelSimulacion = new JPanel();
		getContentPane().add(panelSimulacion, BorderLayout.CENTER);
		panelSimulacion.setLayout(new BorderLayout(0, 0));
		
		panelVisualizacion = new JPanel();
		panelSimulacion.add(panelVisualizacion, BorderLayout.NORTH);
		
		textAreaVisualizacion = new JTextArea();
		textAreaVisualizacion.setEditable(false);
		textAreaVisualizacion.setRows(50);
		textAreaVisualizacion.setColumns(50);
		panelVisualizacion.add(textAreaVisualizacion);
		textAreaVisualizacion.setTabSize(4);
		textAreaVisualizacion.setFont(new Font("Courier New", Font.PLAIN, 15));
		textAreaVisualizacion.setBackground(Color.WHITE);
		
		toolBar = new JToolBar();
		panelSimulacion.add(toolBar, BorderLayout.SOUTH);
		
		panelEstado = new JPanel();
		getContentPane().add(panelEstado, BorderLayout.SOUTH);
	}

	public JTextArea getTextAreaVisualizacion() {
		return textAreaVisualizacion;
	}
	
	/**
	 * Despliega en un panel de texto el estado almacenado correspondiente
	 * a una generación del Juego de la vida.
	 */
	public void mostrarSimulacion(ControlSimulacion control) {
		byte[][] espacio = control.getMundo().getEspacio();
		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {
				mostrarSimple((espacio[i][j] == 1) ? "|o" : "| ");
			}
			textAreaVisualizacion.append("|\n");
		}
	}
	
	private void mostrarSimple(String mensaje) {
		textAreaVisualizacion.append(mensaje);
	}
	
	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, Configuracion.get().getProperty("aplicacion.titulo"), JOptionPane.INFORMATION_MESSAGE);
	}

} // class
