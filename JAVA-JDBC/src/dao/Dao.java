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
	
	public static final String SCHEMA_NAME = "NombreSchemaBaseDeDatos";
	public static final String CONNECTION = 
			"jdbc:mysql://localhost:3306/" + 
			SCHEMA_NAME + 
			"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String USER_CONNECTION = "nombreUsuario";
	public static final String PASS_CONNECTION = "contraseña";
	
	public static final String GET_ALL_PERSONAS = "select * from persona";
	public static final String GET_PERSONA_BY_ID = "select * from persona where id = ?";

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
                	Persona persona = new Persona();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("name"));
                    persona.setApellido(rs.getString("apellido"));
                    personaList.add(persona);
                }
            }
        }
    	return personaList;	
	}

}
