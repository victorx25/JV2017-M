/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo.
 * Utilizando base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: MundosDAO.java 
 * @version: 2.1 - 2018.05.16
 * @author: ajp
 */

package accesoDatos.db4o;
import modelo.ModeloException;
/** 
 * Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos del almacenamiento del
 *  DTO Mundo utilizando base de datos db4o.
 *  Colabora en el patron Fachada.
 *  @since: prototipo2.2
 *  @source: MundosDAO.java 
 *  @version: 1.1 - 2016/06/02 
 *  @author: ajp
 */
import modelo.Mundo;
import modelo.Patron;
import modelo.Posicion;
import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class MundosDAO implements OperacionesDAO {

	// Singleton.
	private static MundosDAO instancia = null;

	// Elemento de almacenamiento, base datos db4o.
	private ObjectContainer db;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static MundosDAO getInstancia() {
		if (instancia == null) {
			instancia = new MundosDAO();
		}
		return instancia;
	}

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private MundosDAO() {
		db = Conexion.getDB();
		try {
			obtener("Demo0");
		} 
		catch (DatosException e) {
			cargarPredeterminados();
		}
	}

	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		// En este array los 0 indican celdas con célula muerta y los 1 vivas.
		byte[][] espacioDemo =  new byte[][]{ 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0 }, //
			{ 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // Given:
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  // 1x Still Life
		};
		String NombreDemo = Configuracion.get().getProperty("mundo.demo");
		List <Integer> constantesDemo = new ArrayList <Integer>();
		Hashtable <Patron, Posicion> mapaDemo = new Hashtable <Patron,Posicion>();
		Mundo mundoDemo = new Mundo(NombreDemo, constantesDemo, mapaDemo);
		db.store(mundoDemo);
	}

	/**
	 *  Cierra conexión.
	 */
	@Override
	public void cerrar() {
		db.close();
	}

	//OPERACIONES DAO
	/**
	 * Obtiene objeto Mundo dado su nombre.
	 * @param nombreMundo - el nombre de Mundo a buscar.
	 * @return - el Mundo encontrado.
	 * @throws DatosException - si no existe.
	 */	
	@Override
	public Mundo obtener(String nombreMundo) throws DatosException {
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		consulta.descend("nombre").constrain(nombreMundo).equal();
		ObjectSet<Mundo> result = consulta.execute();
		if (result.size() > 0) {
			return result.get(0);
		}
		else {
			throw new DatosException("Obtener: "+ nombreMundo + " no existe.");
		}	
	}

	/**
	 * Búsqueda de Usuario dado un objeto, reenvía al método que utiliza nombre.
	 * @param obj - el Mundo a buscar.
	 * @return - el Mundo encontrado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Mundo obtener(Object obj) throws DatosException  {
		return this.obtener(((Mundo) obj).getNombre());
	}

	/**
	 * Obtiene todos los objetos Mundo almacenados.
	 * @return - la List con todos los mundos.
	 */
	@Override
	public List<Mundo> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		return consulta.execute();
	}

	/**
	 *  Alta de un nuevo Mundo sin repeticiones según el campo nombre. 
	 *  @param obj - Mundo a buscar y obtener.
	 *  @throws DatosException - si ya existe.
	 */	
	@Override
	public void alta(Object obj) throws DatosException {
		assert obj != null;
		Mundo mundo = (Mundo) obj;
		try {
			obtener(mundo.getNombre());
		}
		catch (DatosException e) {
			db.store(mundo);
			return;
		}
		throw new DatosException("Alta: " + mundo.getNombre() + " ya existe.");
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param nombreMundo - el nombre del Mundo a eliminar.
	 * @return - el Mundo eliminado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Mundo baja(String nombreMundo) throws DatosException {
		assert nombreMundo != null;
		assert nombreMundo != "";
		assert nombreMundo != " ";
		Mundo mundo = null;
		try {
			mundo = obtener(nombreMundo);
			db.delete(mundo);
			return mundo;
		}
		catch (DatosException e) {
			throw new DatosException("Baja: " + nombreMundo + " no existe...");
		}
	}

	/**
	 *  Actualiza datos de un Mundo reemplazando el almacenado por el recibido.
	 *	@param obj - Mundo con las modificaciones.
	 *  @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		Mundo mundo = (Mundo) obj;
		Mundo mundoActualizado = null;
		try {
			mundoActualizado = obtener(mundo.getNombre());
			mundoActualizado.setConstantes(mundo.getConstantes());
			mundoActualizado.setDistribucion(mundo.getDistribucion());
			db.store(mundoActualizado);
		} 
		catch (DatosException e) { 	
			throw new DatosException("(Actualizar: " + mundo.getNombre() + " no existe.");
		}
	}

	/**
	 * Obtiene el listado de todos los objetos Mundo almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		ObjectSet<Mundo> result = consulta.execute();
		for (Mundo mundo: result) {
			listado.append("\n" + mundo);
		}
		return listado.toString();
	}

	/**
	 * Obtiene el listado de todos los identificadores de mundos almacenados.
	 * @return el texto con el volcado de datos.
	 */
	public String listarId() {
		StringBuilder listado = new StringBuilder();
		Query consulta = db.query();
		consulta.constrain(Mundo.class);
		ObjectSet<Mundo> result = consulta.execute();
		for (Mundo mundo: result) {
			if (mundo != null) {
				listado.append(mundo.getNombre() + "\n");
			}
		}
		return listado.toString();
	}

	/**
	 * Quita todos los objetos Mundo de la base de datos.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los objetos obtenidos.
		for (Mundo mundo: obtenerTodos()) {
			db.delete(mundo);
		}
		cargarPredeterminados();
	}

} //class
