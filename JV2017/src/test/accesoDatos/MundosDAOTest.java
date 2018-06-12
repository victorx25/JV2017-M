/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit 4 para pruebas del DAO de mundos y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: MundosDAOTest.java 
 *  @version: 2.1 - 2018/05/17 
 *  @author: ajp
 */

package test.accesoDatos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Hashtable;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Patron;
import modelo.Posicion;

public class MundosDAOTest {

	private static Datos fachada;
	private Mundo mundoPrueba;

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
			mundoPrueba = fachada.obtenerMundo("Demo0");
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
		fachada.borrarTodosMundos();
		mundoPrueba = null;
	}

	@Test
	public void testObtenerMundoString() {
		try {
			fachada.altaMundo(mundoPrueba);
			// Busca el mismo Mundo almacenado.
			assertSame(mundoPrueba, fachada.obtenerMundo(mundoPrueba.getNombre()));
		} 
		catch (DatosException e) {
		}
	}

	@Test
	public void testObtenerMundoMundo() {
		try {
			fachada.altaMundo(mundoPrueba);
			// Busca el mismo Mundo almacenado.
			assertSame(mundoPrueba, fachada.obtenerMundo(mundoPrueba));
		} 
		catch (DatosException e) {
		}
	}

	@Test
	public void testAltaMundo() {
		try {
			// Mundo nuevo, que no existe.
			fachada.altaMundo(mundoPrueba);
			// Busca el mismo Mundo almacenado.
			assertSame(mundoPrueba, fachada.obtenerMundo(mundoPrueba));
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testBajaMundo() {
		try {
			fachada.altaMundo(mundoPrueba);
			// Baja del mismo Mundo almacenado.
			assertSame(mundoPrueba, fachada.bajaMundo(mundoPrueba.getNombre()));
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testActualizarMundo() {
		Map<Patron, Posicion> distribucionPrueba = new Hashtable<Patron, Posicion>();
		Mundo mundoNuevo = null;
		try {
			mundoNuevo = new Mundo(mundoPrueba);
			distribucionPrueba.put(new Patron(), new Posicion(3,5));
			fachada.altaMundo(mundoPrueba);
			mundoNuevo.setDistribucion(distribucionPrueba);
			fachada.actualizarMundo(mundoNuevo);
			assertEquals(fachada.obtenerMundo(mundoNuevo), mundoNuevo);
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testToStringDatosMundos() {
		assertNotNull(fachada.toStringDatosMundos());
	}

} //class
