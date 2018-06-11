/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Usuario 
 * utilizando acceso a base de datos mySQL.
 * Colabora en el patron Fachada.
 * @since: prototipo2.2
 * @source: UsuariosDAO.java 
 * @version: 2.2 - 04/11/1999 
 * @author: Adrian Sanchez
 */

package accesoDatos.mySql;

import java.sql.SQLException;

import java.util.List;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuariosDAO implements OperacionesDAO {

	private static UsuariosDAO instancia = null;

	/**
	 * Método estático de acceso a la instancia única. Si no existe la crea
	 * invocando al constructor interno. Utiliza inicialización diferida. Sólo se
	 * crea una vez; instancia única -patrón singleton-
	 * 
	 * @return instancia
	 */
	public static UsuariosDAO getInstancia() {
		if (instancia == null) {
			try {
				instancia = new UsuariosDAO();
			} catch (SQLException | DatosException e) {
				e.printStackTrace();
			}
		}
		return instancia;
	}// getInstancia()

	
	
	/**
	 * Constructor por defecto de uso interno. Sólo se ejecutará una vez.
	 */
	private UsuariosDAO() throws SQLException, DatosException {
		// inicializar(); //Proximamente...
		if (obtener("III1R") == null) {
			cargarPredeterminados();
		}
	}

	
	/**
	 * Método para generar datos predeterminados.
	 * 
	 * @throws SQLException
	 * @throws DatosException
	 */
	private void cargarPredeterminados() throws SQLException, DatosException {
		try {
			String nombreUsr = Configuracion.get().getProperty("usuario.admin");
			String password = Configuracion.get().getProperty("usuario.passwordPredeterminada");
			Usuario usrPredeterminado = new Usuario(new Nif("00000000T"), nombreUsr, "Admin Admin",
					new DireccionPostal("Iglesia", "00", "30012", "Murcia"), new Correo("jv.admin" + "@gmail.com"),
					new Fecha(2000, 01, 01), new Fecha(2005, 05, 05), new ClaveAcceso(password),
					RolUsuario.ADMINISTRADOR);
			alta(usrPredeterminado);

			nombreUsr = Configuracion.get().getProperty("usuario.invitado");
			usrPredeterminado = new Usuario(new Nif("00000001R"), nombreUsr, "Invitado Invitado",
					new DireccionPostal("Iglesia", "00", "30012", "Murcia"), new Correo("jv.invitado" + "@gmail.com"),
					new Fecha(2000, 01, 01), new Fecha(2005, 05, 05), new ClaveAcceso(password), RolUsuario.INVITADO);
			alta(usrPredeterminado);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
	}// cargarPredeterminados()

	
	
	
	
	
	
	
	@Override
	public List obtenerTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alta(Object obj) throws DatosException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object baja(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(Object obj) throws DatosException {
		// TODO Auto-generated method stub

	}

	@Override
	public String listarDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listarId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarTodo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cerrar() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object obtener(Object obj) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object obtener(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

} // class