package main;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.Dao;
import model.Persona;

public class JdbcCall {

	public static void main(String[] args) {
		Dao dao = new Dao();
		try {
			//conectamos
			dao.connectar();
			
			//recuperamos un elemento
			Persona persona = dao.getPersonaById(1);
			System.out.println(persona.toString());
			
			//recuperamos una lista de elementos
			ArrayList<Persona> personas = dao.getAll();
			for (Persona p : personas) {
				System.out.println(p.toString());
			}
			dao.desconectar();
		} catch (SQLException e) {
			System.out.println("Error en base de datos");
		} finally {
			try {
				//desconectamos
				dao.desconectar();
			} catch (SQLException e) {
				System.out.println("Error desconectando");
			}
		}
	}

}
