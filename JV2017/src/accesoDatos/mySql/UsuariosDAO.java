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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

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

	private Connection db; // Conexion
	
	private Statement sentenciasUsr;
	private ResultSet rsUsuarios;
	private ArrayList<Object> bufferObjetos;
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
		inicializar();
		if (obtener("III1R") == null) {
			cargarPredeterminados();
		}
	}

	/**
	 * Inicializa el dao, detecta si existen las tablas de datos capturando la
	 * excepcion SQLException
	 * 
	 * @throws SQLException
	 */

	private void inicializar() throws SQLException {

		db = Conexion.getDb();

		try {
			obtener("III1R");
			obtener("AAA0T");

		} catch (DatosException e) {
			crearTablaUsuarios();
			crearTablaEquivalid();
		}

	}

	/**
	 * Crea la tabla de Usuarios en la base de datos
	 * 
	 * @throws SQLException
	 */
	private void crearTablaUsuarios() throws SQLException {

		// se crea un Statement en la conexion a la BD, para realizar la operacion
		Statement s = db.createStatement();

		// Crea la tabla usuarios
		s.executeUpdate("CREATE TABLE usuarios (" + "idUsr VARCHAR(5) NOT NULL," + "nif VARCHAR(9) NOT NULL,"
				+ "nombre VARCHAR(45) NOT NULL," + "apellidos VARCHAR(45) NOT NULL," + "calle VARCHAR(45) NOT NULL,"
				+ "numero VARCHAR(5) NOT NULL," + "cp VARCHAR(5) NOT NULL," + "poblacion VARCHAR(45) NOT NULL,"
				+ "correo VARCHAR(45) NOT NULL," + "fechaNacimiento DATE," + "fechaAlta DATE,"
				+ "claveAcceso VARCHAR(16) NOT NULL," + "rol VARCHAR(20) NOT NULL," + "PRIMARY KEY(idUSR));");

	}

	/**
	 * Crea la tabla de equivalencias en la base de datos
	 * 
	 * @throws SQLException
	 */
	private void crearTablaEquivalid() throws SQLException {
		// se crea un Statement en la conexion a la BD, para realizar la operacion
		Statement s = db.createStatement();

		// se crea la tabla de equivalencias
		s.executeUpdate("CREATE TABLE equivalid (" + "equival VARCHAR(45) NOT NULL," + "idUsr VARCHAR(5) NOT NULL,"
				+ "PRIMARY KEY(equival));");

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
	public Usuario obtener(Object id) throws DatosException { //Correcto y completo
		// Se realiza la consulta y los resultados quedan en el ResultSet
		try {
			rsUsuarios = sentenciasUsr.executeQuery
						("SELECT * FROM usuarios WHERE idUsr = '" + id + "'");
			
			
			//METODOS A RELLENAR: 
			//Establece columnas y etiquetas
			establecerColumnasModelo();
			
			//Borrado previo de filas
			borrarFilasModelo();
			
			//Volcado desde el ResultSet
			rellenarFilasModelo();
			
			//Actualizar buffer de objetos
			sincronizarBufferObjetos();
			if (bufferObjetos.size() > 0) {
				return (Usuario) bufferObjetos.get(0);
			}
		}
		catch (SQLException e) {
			throw new DatosException("Obtener: "+ id + " no existe");
		}
		return null;
	}
	
	
	private void sincronizarBufferObjetos() {
		// TODO Auto-generated method stub
		
	}

	private void rellenarFilasModelo() {
		// TODO Auto-generated method stub
		
	}

	private void borrarFilasModelo() {
		// TODO Auto-generated method stub
		
	}

	private void establecerColumnasModelo() {
		// TODO Auto-generated method stub
		
	}

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
	public Object obtener(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

} // class