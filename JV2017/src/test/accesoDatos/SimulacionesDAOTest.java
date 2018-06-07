/** 
 *  Proyecto: Juego de la vida.
  Clase JUnit 4 para pruebas del DAO de simulaciones y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: SimulacionesDAOTest.java 
 *  @version: 2.1 - 2018/05/17 
 *  @author: ajp
 */

package test.accesoDatos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoDatos.db4o.MundosDAO;
import accesoDatos.db4o.UsuariosDAO;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import util.Fecha;

public class SimulacionesDAOTest {

	private static Datos fachada;
	private Simulacion simulacionPrueba;

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void crearFachadaDatos() {
		fachada = new Datos();
	}
	
	/**
	 * Método que se ejecuta antes de cada @test.
	 * @throws ModeloException 
	 * @throws DatosException 
	 */
	@Before
	public void crearDatosPrueba() {
		try {
			simulacionPrueba = new Simulacion(fachada.obtenerUsuario("III1R"), new Fecha(), new Mundo(), EstadoSimulacion.PREPARADA);
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta al terminar de cada @test.
	 */
	@After
	public void borraDatosPrueba() {
		fachada.borrarTodasSimulaciones();
		simulacionPrueba = null;
	}

	@Test
	public void testObtenerSimulacion() {
		try {
			fachada.altaSimulacion(simulacionPrueba);
			// Busca la misma Simulacion almacenada.
			assertSame(simulacionPrueba, fachada.obtenerSimulacion(simulacionPrueba));
		} 
		catch (DatosException e) {
		}
	}

	@Test
	public void testObtenerSimulacionPredeterminada() {
		try {
			Usuario usrPredeterminado = UsuariosDAO.getInstancia().obtener("III1R");
			Mundo mundoPredeterminado = MundosDAO.getInstancia().obtener("Demo0");
			Simulacion simulacionPredeterminada = new Simulacion(usrPredeterminado, 
					new Fecha(2005, 05, 05), mundoPredeterminado, 
					EstadoSimulacion.PREPARADA);
			assertEquals(simulacionPredeterminada, fachada.obtenerSimulacion("III1R:20050505000000"));
		} 
		catch (DatosException e) { 
		}	
	}
	
	@Test
	public void testAltaSimulacion() {
		try {
			// Simulacion nueva, que no existe.
			fachada.altaSimulacion(simulacionPrueba);
			// Busca el mismo Simulacion almacenado.
			assertSame(simulacionPrueba, fachada.obtenerSimulacion(simulacionPrueba));
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testBajaSimulacion() {
		try {
			fachada.altaSimulacion(simulacionPrueba);
			// Baja de la misma Simulacion almacenada.
			assertSame(simulacionPrueba, fachada.bajaSimulacion(simulacionPrueba.getIdSimulacion()));
		} 
		catch (DatosException e) { 
		}	
	}

	@Test
	public void testActualizarSimulacion() {	
		Simulacion simulacionNueva = null;
		try {
			simulacionNueva = new Simulacion(simulacionPrueba);
			fachada.altaSimulacion(simulacionPrueba);
			simulacionNueva.setEstado(EstadoSimulacion.COMPLETADA);
			fachada.actualizarSimulacion(simulacionNueva);
			assertEquals(fachada.obtenerSimulacion(simulacionPrueba), simulacionNueva);
		} 
		catch (DatosException e) { 
		}
	}
	
	@Test
	public void testToStringDatosSimulaciones() {
		assertNotNull(fachada.toStringDatosSimulaciones());
	}

} //class
