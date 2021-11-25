package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.Dao;
import model.Persona;

public class Service {
	Dao dao;
	
	public Service() {
		Dao dao = new Dao();
	}

	public void init() {
		try {
			getPersonaById();
			getAllPersonas();
			insertNewpersona(new Persona(0, "Maria", "Sanchez"));
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
	
	private void insertNewpersona(Persona persona) throws SQLException {
		
		//conectamos
		dao.connectar();
		
		//recuperamos un elemento
		dao.insertNewPersona(persona);
		
		//desconectamos
		dao.desconectar();
		
	}
	
	private void updatePersona(Persona persona) throws SQLException {

		//conectamos
		dao.connectar();
		
		//recuperamos un elemento
		dao.updatePersona(persona);
		
		//desconectamos
		dao.desconectar();
	
	}

	public void getPersonaById() throws SQLException {

		//conectamos
		dao.connectar();
		
		//recuperamos un elemento
		Persona persona = dao.getPersonaById(1);
		System.out.println(persona.toString());
		
		//desconectamos
		dao.desconectar();
	}
	
	public void getAllPersonas() throws SQLException {

		//conectamos
		dao.connectar();
		
		//recuperamos una lista de elementos
		ArrayList<Persona> personas = dao.getAll();
		for (Persona p : personas) {
			System.out.println(p.toString());
		}
		
		//desconectamos
		dao.desconectar();
	}

}
