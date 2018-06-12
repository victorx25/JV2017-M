/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit 4 para pruebas del DAO de sesiones y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: DatosTest.java 
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
import modelo.ModeloException;
import modelo.Patron;

public class PatronesDAOTest {

	private static Datos fachada;
	private Patron patronPrueba;

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
			patronPrueba = fachada.obtenerPatron("Demo0");
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
		fachada.borrarTodosPatrones();
		patronPrueba = null;
	}

	@Test
	public void testObtenerUsuarioId() {
		try {
			assertEquals(fachada.obtenerUsuario("III1R").getIdUsr(), "III1R");
		} 
		catch (DatosException e) {
		}
	}

	@Test
	public void testObtenerPatronString() {
		try {
			fachada.altaPatron(patronPrueba);
			// Busca el mismo Patron almacenado.
			assertSame(patronPrueba, fachada.obtenerPatron(patronPrueba.getNombre()));
		} 
		catch (DatosException e) {
		}	
	}

	@Test
	public void testObtenerPatronPatron() {
		try {
			fachada.altaPatron(patronPrueba);
			// Busca el mismo Patron almacenado.
			assertSame(patronPrueba, fachada.obtenerPatron(patronPrueba));
		} 
		catch (DatosException e) {
		}	
	}

	@Test
	public void testAltaPatron() {
		try {
			// Patron nuevo, que no existe.
			fachada.altaPatron(patronPrueba);
			// Busca el mismo Patron almacenado.
			assertSame(patronPrueba, fachada.obtenerPatron(patronPrueba));
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testBajaPatron() {
		try {
			fachada.altaPatron(patronPrueba);
			// Baja del mismo Patron almacenado.
			assertSame(patronPrueba, fachada.bajaPatron(patronPrueba.getNombre()));
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testActualizarPatron() {
		byte[][] esquemaPrueba = new byte[5][4];
		try {
			fachada.altaPatron(patronPrueba);
			patronPrueba.setEsquema(esquemaPrueba);
			fachada.actualizarMundo(fachada.obtenerMundo("Demo0"));
			assertSame(fachada.obtenerPatron(patronPrueba).getEsquema(), esquemaPrueba);
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testToStringDatosPatrones() {
		assertNotNull(fachada.toStringDatosPatrones());
	}

} //class
