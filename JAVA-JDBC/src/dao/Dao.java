package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Persona;

public class Dao {
	
	private Connection conexion;	
	
	//Constantes. Deber�an estar en una clase de constanes en el pck utils
	public static final String SCHEMA_NAME = "NombreSchemaBaseDeDatos";
	public static final String CONNECTION = 
			"jdbc:mysql://localhost:3306/" + 
			SCHEMA_NAME + 
			"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
	public static final String USER_CONNECTION = "nombreUsuario";
	public static final String PASS_CONNECTION = "contrase�a";
	
	public static final String GET_ALL_PERSONAS = "select * from persona";
	public static final String GET_PERSONA_BY_ID = "select * from persona where id = ?";
	public static final String UPDATE_PERSONA = "uptate persona set nombre = ?, apellido = ? where id = ?";
	public static final String INSERT_PERSONA = "insert into persona (nombre, apellido) values (?,?)";

	public void connectar() throws SQLException {
		String url = CONNECTION;
        String user = USER_CONNECTION;
        String pass = PASS_CONNECTION;
        conexion = DriverManager.getConnection(url, user, pass);
	}

	public void desconectar() throws SQLException {
		if (conexion != null) {
            conexion.close();
        }
	}	
	
	public Persona getPersonaById(int id) throws SQLException {
    	String select = GET_PERSONA_BY_ID;
    	Persona producto = null;   	
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setInt(1,id);
    	  	System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		producto = new Persona();
            		producto.setId(rs.getInt("id"));
	                producto.setNombre(rs.getString("name"));
	                producto.setApellido(rs.getString("apellido"));
            	}
            }
        }
    	return producto;
		
	}
	
	public ArrayList<Persona> getAll() throws SQLException {
    	ArrayList<Persona> personaList = new ArrayList<>();
    	
    	try (Statement st = conexion.createStatement()) { 
            try (ResultSet rs = st.executeQuery(GET_ALL_PERSONAS)) {
                while(rs.next()){
                	Persona persona = new Persona(rs.getInt(1), rs.getString(2), rs.getString(3));
                    personaList.add(persona);
                }
            }
        }
    	return personaList;	
	}

	public void insertNewPersona(Persona persona) throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(INSERT_PERSONA)) { 
			ps.setString(1, persona.getNombre());
			ps.setString(2, persona.getApellido());
            ps.executeUpdate();
        }
	}

	public void updatePersona(Persona persona) throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(UPDATE_PERSONA)) { 
			ps.setString(1, persona.getNombre());
			ps.setString(2, persona.getApellido());
			ps.setInt(3, persona.getId());
            ps.execute();
        }
	}

}
