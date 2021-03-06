package interfaceDaoLibrairie;

import java.sql.SQLException;
import java.util.Vector;

import entitiesLibrairie.Employe;

public interface IEmployeDAO {

	
	public void ajouterEmploye(String acces, String nom, String prenom, String poste, String log, String mdp) throws SQLException;
	
	public Vector<Vector> afficherEmployes() throws SQLException;
	
	public Employe afficherEmploye(String nom, String prenom) throws SQLException;
	
	public Vector<Vector> rechercherEmploye(String nom) throws SQLException;
	
	public Vector <Vector> rechercherEmployeparDroitAccess(String acces) throws SQLException;
	
	public void modifierEmployer(Employe employe, String log) throws SQLException;
	
	public void supprimerEmploye(String nom, String prenom) throws SQLException;
	
	public Employe authentification(String id, String mdp) throws SQLException;
	
	public Vector<String> recupererDroitsAcces() throws SQLException;
	
	//public Employe employeATraiter(String login, String mdp) throws SQLException;
}
