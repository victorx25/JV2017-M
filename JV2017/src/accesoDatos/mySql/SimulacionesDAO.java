/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DAO Simulacion. 
 * Utiliza base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.1
 * @source: SimulacionesDAO.java 
 * @version: 2.2 - 2018.06.12
 * @author: DAM Grupo 1
 */
package accesoDatos.mySql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import accesoDatos.db4o.MundosDAO;
import accesoDatos.db4o.UsuariosDAO;
import modelo.Mundo;
import modelo.Simulacion;
import modelo.Usuario;
import modelo.Simulacion.EstadoSimulacion;
import util.Fecha;

public class SimulacionesDAO implements OperacionesDAO {


	// Singleton 
	private static SimulacionesDAO instancia = null;

	// Elemento de almacenamiento. Base datos db4o
	private Connection db;

	private java.sql.Statement sentenciaSim;
	private ResultSet rsSimulaciones;
	private DefaultTableModel tmSimulaciones;
	private ArrayList<Object> bufferObjetos;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 *  @author Grupo 1 - Alejandro Motellón 
	 */
	public static SimulacionesDAO getInstancia() throws SQLException, DatosException {
		if (instancia == null) {
			instancia = new SimulacionesDAO();
		}
		return instancia;
	}

	/**
	 * Constructor por defecto de uso interno.
	 * @throws SQLException
	 * @throws DatosException
	 * @author Grupo 1 - Alejandro Motellón 
	 */
	private SimulacionesDAO() throws SQLException, DatosException {
		inicializar();
		if (obtener("III1R")==null) {
			cargarPredeterminados();
		}
	}

	/**
	 *  Método para generar de datos predeterminados.
	 *  @author Grupo 1 - Alejandro Motellón 
	 */
	private void cargarPredeterminados() throws SQLException, DatosException {
		Simulacion simulacionDemo = null;
		try {
			// Obtiene usuario y mundo predeterminados.
			Usuario usrPredeterminado = UsuariosDAO.getInstancia().obtener("III1R");
			Mundo mundoPredeterminado = MundosDAO.getInstancia().obtener("Demo0");
			simulacionDemo = new Simulacion(usrPredeterminado, 
					new Fecha(2005, 05, 05), mundoPredeterminado, 
					EstadoSimulacion.PREPARADA);
			alta(simulacionDemo);
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicializa el DAO, detecta si existen las tablas de datos capturando la 
	 * excepción SQLException.
	 * @throws SQLException
	 * @author Grupo 1 - Alejandro Motellón 
	 */
	private void inicializar()throws SQLException{
		bufferObjetos=new ArrayList<Object>();
		db=Conexion.getDb();
		try {
			obtener("III1R");
			obtener("AAAOT");

		}
		catch(DatosException e) {
			crearTablaSimulaciones();

		}
	}

	/**
	 * Método para crear la tabla SQL
	 * @throws SQLException
	 * @author Grupo 1 - Alejandro Motellón 
	 */
	private void crearTablaSimulaciones() throws SQLException{
		java.sql.Statement sentencia = db.createStatement();

		sentencia.executeUpdate("CREATE TABLE simulaciones("
				+ "idUsr VARCHAR(20) NOT NULL,"
				+ "fecha DATE NOT NULL,"
				+ "mundo VARCHAR (10) NOT NULL"
				+ "estado VARCHAR (10) NOT NULL"
				+ "PRIMARY KEY(idUsr, fecha));");
	}


	@Override
	public Object obtener(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object obtener(Object obj) throws DatosException {
		// TODO Auto-generated method stub
		return null;
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

}
