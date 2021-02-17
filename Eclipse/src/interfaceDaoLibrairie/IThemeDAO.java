package interfaceDaoLibrairie;

import java.sql.SQLException;
import java.util.Vector;

import entitiesLibrairie.Theme;

public interface IThemeDAO {

	
	public void ajouterTheme(String nom) throws SQLException;
	
	public Vector<Vector> afficherTheme() throws SQLException;
	
	public Vector<Vector> rechercherTheme(String string) throws SQLException;
	
	public void modifierTheme(Theme theme, String nom) throws SQLException;
	
	public void supprimerTheme(String nom) throws SQLException;


	
	
}