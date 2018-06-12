/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit 4 para pruebas del DAO de usuarios y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: UsuariosDAO.java 
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
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuariosDAOTest {

	private static Datos fachada;
	private Usuario usrPrueba;


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
			// Usuario con idUsr "PMA8P"
			usrPrueba =  new Usuario(new Nif("00000008P"), "Pepe",
					"Márquez Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("pepe@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2018, 02, 05), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta al terminar de cada @test.
	 */
	@After
	public void borraDatosPrueba() {
		fachada.borrarTodosUsuarios();
		usrPrueba = null;
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
	public void testObtenerUsuario() {
		try {
			fachada.altaUsuario(usrPrueba);
			// Busca el mismo Usuario almacenado.
			assertSame(usrPrueba, fachada.obtenerUsuario(usrPrueba));
		} 
		catch (DatosException e) {
		}
	}

	@Test
	public void testAltaUsuario() {
		try {
			// Usuario nuevo, que no existe.
			fachada.altaUsuario(usrPrueba);
			// Busca el mismo Usuario almacenado.
			assertSame(usrPrueba, fachada.obtenerUsuario(usrPrueba));
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testBajaUsuario() {
		try {
			fachada.altaUsuario(usrPrueba);
			// Baja del mismo Usuario almacenado.
			assertSame(usrPrueba, fachada.bajaUsuario(usrPrueba.getIdUsr()));
		} 
		catch (DatosException e) { 
		}
	}

	@Test
	public void testActualizarUsuario() {
		try {
			// Usuario nuevo, que no existe.
			fachada.altaUsuario(usrPrueba);
			usrPrueba.setApellidos("Ramírez Pinto");
			fachada.actualizarUsuario(usrPrueba);
			assertEquals(fachada.obtenerUsuario(usrPrueba).getApellidos(), "Ramírez Pinto");
		} 
		catch (DatosException | ModeloException e) {
		}
	}

	@Test
	public void testToStringDatosUsuarios() {
		assertNotNull(fachada.toStringDatosUsuarios());
	}

	@Test
	public void testGetEquivalenciaId() {
		try {		
			// Usuario nuevo, que no existe.
			fachada.altaUsuario(usrPrueba);
			assertEquals(fachada.obtenerUsuario("PMA8P").getIdUsr(), "PMA8P");
			assertEquals(fachada.obtenerUsuario("00000008P").getIdUsr(), "PMA8P");
			assertEquals(fachada.obtenerUsuario("pepe@gmail.com").getIdUsr(), "PMA8P");
		} 
		catch (DatosException e) { 
		}
	}

} //class
