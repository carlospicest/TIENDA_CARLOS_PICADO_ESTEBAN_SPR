package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.CategoriaDAO;
import curso.java.tienda.dao.RolDAO;
import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.pojo.Rol;

@Service
public class RolService {

	@Autowired
	private RolDAO rolDao;


	public ArrayList<Rol> getRoles() {
		return (ArrayList<Rol>) rolDao.findAll();
	}


	public Rol getRol(int id) {
		return rolDao.findById(id);
	}

	public boolean addRol(Rol rol) {
		try {
			rolDao.save(rol);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
	}


	public boolean deleteRol(int id) {
		try {
			rolDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
		
	}
	
}
