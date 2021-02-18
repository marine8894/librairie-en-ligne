package daoLibrairie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import connexionLibrairie.Connexion;
import entitiesLibrairie.Auteur;
import entitiesLibrairie.Editeur;
import entitiesLibrairie.Livre;
import entitiesLibrairie.Theme;
import interfaceDaoLibrairie.ILivreDAO;

public class LivreDAO implements ILivreDAO{

	
	private Connection myConnection;
	private Statement stmt;
	private PreparedStatement ptsmt;
	private Livre livre = new Livre();
	
	public LivreDAO() {
		myConnection = Connexion.getInstance();
	}
	
	
	@Override
	public Vector<String> recupererTVA() throws SQLException {
		Vector<String> tva = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT TVATAUX FROM TVA ;";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			tva.add(result.getString("TVATAUX"));
		}
		return tva;
		
		// TODO Auto-generated method stub
		
	}


	@Override
	public String recupererISBN(String titre) throws SQLException {
		// TODO Auto-generated method stub
		String isbn = "";
		stmt = myConnection.createStatement();
		String query = "SELECT LIVREISBN FROM LIVRE WHERE LIVRETITRE = \"" + titre+ "\";";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			isbn = result.getString("LIVREISBN");
		}
		return isbn;
	}
	
	
	
	@Override
	public Livre ajouterLivre(String isbn, String titre, String sousTitre, float prixHT, String tvaID, String dateEdition, String image, String resume, float nbrePages, String stock, String comment, String statut) throws SQLException {
		// TODO Auto-generated method stub
		String tva="";
		stmt = myConnection.createStatement();
		String queryy = "SELECT TVAID FROM TVA WHERE TVATAUX = "+tvaID+";";
		ResultSet result = stmt.executeQuery(queryy);
		while (result.next()) {
			tva=result.getString("TVAID");
		}
		
		
		String query = "INSERT INTO LIVRE (LIVREISBN, LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, "
				+ "TVAID, LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, LIVRESTOCK,"
				+ "LIVRECOMMENT, LIVRESTATUT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
		ptsmt = myConnection.prepareStatement(query);
		ptsmt.setString(1, isbn);
		ptsmt.setString(2, titre);
		ptsmt.setString(3, sousTitre);
		ptsmt.setFloat(4, prixHT);
		ptsmt.setString(5, tva);
		ptsmt.setString(6, dateEdition);
		ptsmt.setString(7, image);
		ptsmt.setString(8, resume);
		ptsmt.setFloat(9, nbrePages);
		ptsmt.setString(10, stock);
		ptsmt.setString(11, comment);
		ptsmt.setString(12, statut);
		livre = new Livre (isbn, titre, sousTitre, prixHT, tvaID, dateEdition, image, resume, nbrePages, stock, comment, statut);
		int resultat = ptsmt.executeUpdate();
		
		return livre;
	}
	

	@Override
	public void lierLivreAuteur(String auteur, String isbn) throws SQLException {
		// TODO Auto-generated method stub
		String auteurALier ="";
		stmt = myConnection.createStatement();
		String query = "SELECT AUTEURID FROM AUTEUR WHERE AUTEURNOM = '"+auteur+"';";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			auteurALier = result.getString("AUTEURID");
		}
		
		
		String insert = "INSERT INTO ECRIRE (LIVREISBN, AUTEURID) VALUES (?, ?);";
		ptsmt = myConnection.prepareStatement(insert);
		ptsmt.setString(1, isbn);
		ptsmt.setString(2, auteurALier);
		int resultat = ptsmt.executeUpdate();
		System.out.println(insert);
	}


	@Override
	public void lierLivreEditeur(String editeur, String isbn) throws SQLException {
		// TODO Auto-generated method stub
		String editeurALier ="";
		stmt = myConnection.createStatement();
		String query = "SELECT EDITEURID FROM EDITEUR WHERE EDITEURNOM = '"+editeur+"';";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			editeurALier = result.getString("EDITEURID");
		}
		
		
		String insert = "INSERT INTO EDITER (LIVREISBN, EDITEURID) VALUES (?, ?);";
		ptsmt = myConnection.prepareStatement(insert);
		ptsmt.setString(1, isbn);
		ptsmt.setString(2, editeurALier);
		int resultat = ptsmt.executeUpdate();
		System.out.println(insert);
	}


	@Override
	public void lierLivreTheme(String theme, String isbn) throws SQLException {
		// TODO Auto-generated method stub
		String themeALier ="";
		stmt = myConnection.createStatement();
		String query = "SELECT THEMEID FROM THEME WHERE THEMENOM = '"+theme+"';";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			themeALier = result.getString("THEMEID");
		}
	
		
		String insert = "INSERT INTO AVOIR (LIVREISBN, THEMEID) VALUES (?, ?);";
		ptsmt = myConnection.prepareStatement(insert);
		ptsmt.setString(1, isbn);
		ptsmt.setString(2, themeALier);
		int resultat = ptsmt.executeUpdate();
		System.out.println(insert);
	}


	@Override
	public void lierLivreMotCle(String motCle, String isbn) throws SQLException {
		// TODO Auto-generated method stub
		
		String motCleALier ="";
		stmt = myConnection.createStatement();
		String query = "SELECT MOTCLEID FROM MOT_CLE WHERE MOTCLELIBELLE = '"+motCle+"';";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			motCleALier = result.getString("MOTCLEID");
		}
		
		String insert = "INSERT INTO ATTRIBUER1 (MOTCLEID, LIVREISBN) VALUES (?, ?);";
		ptsmt = myConnection.prepareStatement(insert);
		ptsmt.setString(1, motCleALier);
		ptsmt.setString(2, isbn);
		int resultat = ptsmt.executeUpdate();
		System.out.println(insert);
	}
	

	    
    @Override
    public Vector findAll () throws SQLException{
    	
    	Statement ste;
        Vector livres = new Vector();  
        ste = myConnection.createStatement();
        String requeteaexecuter = "SELECT LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
        		+ "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
        		+ "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT FROM LIVRE L INNER JOIN TVA T ON T.TVAID = L.TVAID;";


        ResultSet res = ste.executeQuery(requeteaexecuter);
        while (res.next()) {
            Vector livre = new Vector();
            livre.add(res.getString("LIVREISBN"));
            livre.add(res.getString("LIVRETITRE"));
            livre.add(res.getString("LIVRESOUSTITRE"));
            livre.add(res.getFloat("LIVREPRIXHT"));
            livre.add(res.getString("TVATAUX"));
            //livre.setLivrePrixTTC(res.getFloat("TVATAUX")*1.055);
            livre.add(res.getString("LIVREDATEEDITION"));
            livre.add(res.getString("LIVREIMAGE"));
            livre.add(res.getString("LIVRERESUME"));
            livre.add(res.getFloat("LIVRENBREPAGE"));
            livre.add(res.getString("LIVRESTOCK"));
            livre.add(res.getString("LIVRECOMMENT"));
            livre.add(res.getString("LIVRESTATUT"));
           // Auteur auteur = new Auteur(res.getString("AUTEURID"), res.getString("AUTEURNOM"), res.getString("AUTEURPRENOM"), res.getString("AUTEURPSEUDO"));
          //  livre.setAuteur(auteur);
           // livre.setEditeur(new Editeur(res.getString("EDITEURID"), res.getString("EDITEURNOM")));
            livres.add(livre);
            
            
        }
        return livres;

    }
	    
    
	  
  public String addBook(Livre livre, String idAuteur, String idEditeur){
        String tvaID=null;
      
        try {
            String requeteIdTVA= "SELECT TVAID FROM TVA WHERE TVATAUX = ?";
            PreparedStatement pstmt1 = myConnection.prepareStatement(requeteIdTVA);
            pstmt1.setString(1, livre.getTvaId());
            ResultSet res = pstmt1.executeQuery();
            while(res.next()){
                tvaID= res.getString("TVAID");
            }
              
            
            String requeteaexecuter = "INSERT INTO LIVRE VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pstmt = myConnection.prepareStatement(requeteaexecuter);
            pstmt.setString(1, livre.getLivreISBN());
            pstmt.setString(2, livre.getLivreTitre());
            pstmt.setString(3, livre.getSousTitre());
            pstmt.setFloat(4, livre.getLivrePrixHT());
            pstmt.setString(5, tvaID);
            pstmt.setString(6, livre.getLivreDateEdition());
            pstmt.setString(7, livre.getLivreImage());
            pstmt.setString(8, livre.getLivreResume());
            pstmt.setFloat(9, livre.getLivreNbrePage());
            pstmt.setString(10, livre.getLivreStock());
            pstmt.setString(11, livre.getLivreComment());
            pstmt.setString(12, livre.getLivreStatut());
            int result = pstmt.executeUpdate();
        //    rattacherAuteur( idAuteur,  livre.getLivreISBN() );
        //    rattacherEditeur(idEditeur, livre.getLivreISBN() );
            if(result > 0) {
                return "Le livre a été crée avec succès";
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Le livre n'a pas été crée";
        }
        
           return null; 
       
  }  

	   
  
 /* public void rattacherAuteur( String idAuteur, String ISBN ){
            try {
                String requeteaexecuter2= "INSERT INTO ECRIRE VALUES (?,?)";
                PreparedStatement pstmt = myConnection.prepareStatement(requeteaexecuter2);
                pstmt.setString(1, ISBN);
                pstmt.setString(2, idAuteur); 
                int result1 = pstmt.executeUpdate();
                System.out.println("baba2");
        } catch (SQLException ex) {
            Logger.getLogger(LivreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }*/
  
  /*public void rattacherEditeur(String idEditeur, String ISBN){
      
       try {
                String requeteaexecuter3= "INSERT INTO EDITER VALUES (?,?)";
                PreparedStatement pstmt = myConnection.prepareStatement(requeteaexecuter3);
                pstmt.setString(1, ISBN);
                pstmt.setString(2, idEditeur); 
                int result3 = pstmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(LivreDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
  }*/
  
  public String modifyBook(Livre livre){
      String tvaID=null;
      
        try {
            String requeteIdTVA= "SELECT TVAID FROM TVA WHERE TVATAUX = ?";
            PreparedStatement pstmt1 = myConnection.prepareStatement(requeteIdTVA);
            pstmt1.setString(1, livre.getTvaId());
            ResultSet res = pstmt1.executeQuery();
            while(res.next()){
                tvaID= res.getString("TVAID");
            }
            String requeteAExectuer= "UPDATE LIVRE SET LIVRETITRE=?, LIVRESOUSTITRE= ?, LIVREPRIXHT= ?, TVAID= ?, LivreDateEdition =?, LivreImage= ?,"
                    + " LivreNbrePage=?, LivreStock=?, LivreStatut= ?, LivreComment= ?, LivreResume= ? where LIVREISBN=? ";
            PreparedStatement pstmt = myConnection.prepareStatement(requeteAExectuer);
            pstmt.setString(1,  livre.getLivreTitre());
            pstmt.setString(2, livre.getSousTitre());
            pstmt.setFloat(3, livre.getLivrePrixHT());
            pstmt.setString(4, tvaID); 
            pstmt.setString(5, livre.getLivreDateEdition());
            pstmt.setString(6, livre.getLivreImage());
            pstmt.setFloat(7, livre.getLivreNbrePage());
            pstmt.setString(8, livre.getLivreStock());
            pstmt.setString(9, livre.getLivreStatut());
            pstmt.setString(10, livre.getLivreComment());
            pstmt.setString(11, livre.getLivreResume());
            pstmt.setString(12, livre.getLivreISBN());
            
            int result = pstmt.executeUpdate();
            
            if(result > 0) {
                return "Le livre a été modifié avec succès";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LivreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Echec de l'update";
        }
      return null;
  }
	  

  


	@Override
	public Vector findByParameter(String champs, String valeurChamps) throws SQLException {
		Vector livres = new Vector<>(); 
	    
	    if(null != champs)switch (champs) {  // pour convertir le parametre rentré dans la combobox en l'expression ecrite dans la requete. ex: on converti "AUTEUR" entré dans la cmbx en a.AUTEURNOM entré dans la requete 
	       case "AUTEUR":// de la combobox
	           champs= "a.AUTEURNOM like"; // de la requete
	           break;
	       case "TITRE":
	           champs= "l.LIVRETITRE like";
	           break;
	       case "ISBN":
	           champs= "l.LIVREISBN like";
	           break;
	       case "EDITEUR":
	           champs= "edr.EDITEURNOM like";
	           break;  
	       case "STOCK":
	           champs= "l.LIVRESTOCK <=";
	           break;    
	       case "PRIX HT":
	           champs= "l.LIVREPRIXHT <=";
	           break; 
	       default:
	           break;
	   }
	   
	   String requeteaexecuter = "SELECT LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, " +
	                                   " LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, " +
	                                   " LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT, " +
	                                   " a.AUTEURID, a.AUTEURNOM, a.AUTEURPRENOM, a.AUTEURPSEUDO, t.TVATAUX,\n" +
	                                   " edr.EDITEURID, edr.EDITEURNOM\n" +
	                                   " FROM LIVRE L " +
	                                   " join ecrire e on l.LIVREISBN = e.LIVREISBN\n" +
	                                   " join AUTEUR a on a.AUTEURID = e.AUTEURID\n" +
	                                   " JOIN TVA T ON T.TVAID= L.TVAID" +
	                                   " join EDITER ed on ed.LIVREISBN = l.LIVREISBN\n" +
	                                   " join EDITEUR edr on edr.EDITEURID = ed.EDITEURID\n" +
	                                   " WHERE "+champs+" ?";
	   
	   
	   PreparedStatement pstmt = myConnection.prepareStatement(requeteaexecuter);
	   if(champs.contains("STOCK")) {
	       pstmt.setInt(1,Integer.valueOf(valeurChamps) ); 
	   } else if(champs.contains("PRIXHT")) {
	       pstmt.setFloat(1,Float.valueOf(valeurChamps) ); 
	   } else {
	     pstmt.setString(1,"%"+valeurChamps.toUpperCase()+"%" );  
	   }
	   
	   ResultSet res = pstmt.executeQuery();
	   while (res.next()) {
	       Vector livre = new Vector();
	       livre.add(res.getString("LIVREISBN"));
	       livre.add(res.getString("LIVRETITRE"));
	       livre.add(res.getString("LIVRESOUSTITRE"));
	       livre.add(res.getFloat("LIVREPRIXHT"));
	       livre.add(res.getString("TVATAUX"));
	       livre.add(res.getString("LIVREDATEEDITION"));
	       livre.add(res.getString("LIVREIMAGE"));
	       livre.add(res.getString("LIVRERESUME"));
	       livre.add(res.getFloat("LIVRENBREPAGE"));
	       livre.add(res.getString("LIVRESTOCK"));
	       livre.add(res.getString("LIVRECOMMENT"));
	       livre.add(res.getString("LIVRESTATUT"));
	       //Auteur auteur = new Auteur(res.getString("AUTEURID"), res.getString("AUTEURNOM"), res.getString("AUTEURPRENOM"), res.getString("AUTEURPSEUDO"));
	      // livre.setAuteur(auteur);
	       //livre.setEditeur(new Editeur(res.getString("EDITEURID"), res.getString("EDITEURNOM")));
	       livres.add(livre);
	       
	   }
	   return livres;
	}


	@Override
	public Vector<Vector> rechercheLivreparAuteur(String auteur) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT L.LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "INNER JOIN ECRIRE E ON E.LIVREISBN = L.LIVREISBN "
	            + "INNER JOIN AUTEUR A ON A.AUTEURID = E.AUTEURID "
	            + "WHERE AUTEURNOM LIKE '%"+auteur+"%';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}
	
	
	@Override
	public Vector rechercheLivreparISBN(String isbn) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "WHERE LIVREISBN LIKE '%"+isbn+"%';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}
	
	
	@Override
	public Vector rechercheLivreparTitre(String titre) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE  L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "WHERE LIVRETITRE LIKE '%"+titre+"%';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		   System.out.println(query);
		return livres;
		
	}
	
	
	@Override
	public Vector rechercheLivreparPrixHT(String prixHT) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "WHERE LIVREPRIXHT <= "+prixHT+";";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}
	
	
	@Override
	public Vector rechercheLivreparPrixTTC(float prixTTC) throws SQLException {
		// TODO Auto-generated method stub
		float prixHT = 00.00f;
		Statement stmtt = myConnection.createStatement();
		String queryy = "SELECT TVATAUX FROM TVA;";
		ResultSet result = stmtt.executeQuery(queryy);
		while (result.next()) {
			float tva = result.getFloat("TVATAUX");
			float tauxTVA = 1+(tva/100);
			prixHT = prixTTC / tauxTVA;
		}
		
		
		
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "WHERE LIVREPRIXHT <= '"+prixHT+"';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}
	
	
	@Override
	public Vector rechercheLivreparTheme(String theme) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT L.LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "INNER JOIN AVOIR A ON A.LIVREISBN = L.LIVREISBN "
	            + "INNER JOIN THEME TH ON TH.THEMEID = A.THEMEID "
	            + "WHERE THEMENOM = '"+theme+"';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}
	
	
	@Override
	public Vector rechercheLivreparGenre(String genre) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT L.LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "INNER JOIN AVOIR A ON A.LIVREISBN = L.LIVREISBN "
	            + "INNER JOIN THEME TH ON TH.THEMEID = A.THEMEID "
	            + "INNER JOIN POSSEDER P ON P.THEMEID = TH.THEMEID "
	            + "INNER JOIN GENRE G ON G.GENREID = P.GENREID "
	            + "WHERE GENRENOM = '"+genre+"';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}
	
	
	@Override
	public Vector rechercheLivreparMotCle(String motCle) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT L.LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "INNER JOIN ATTRIBUER1 AT ON AT.LIVREISBN = L.LIVREISBN "
	            + "INNER JOIN MOT_CLE M ON AT.MOTCLEID = M.MOTCLEID "
	            + "WHERE MOTCLELIBELLE = '"+motCle+"';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}
	
	
	@Override
	public Vector rechercheLivreparStock(String stock) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "WHERE LIVRESTOCK >= "+stock+";";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}


	@Override
	public Vector rechercheLivreparEditeur(String editeur) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT L.LIVREISBN ,LIVRETITRE, LIVRESOUSTITRE, LIVREPRIXHT, TVATAUX, "
	            + "LIVREDATEEDITION, LIVREIMAGE, LIVRERESUME, LIVRENBREPAGE, "
	            + "LIVRESTOCK, LIVRECOMMENT, LIVRESTATUT "
	            + "FROM LIVRE L "
	            + "INNER JOIN TVA T ON T.TVAID= L.TVAID "
	            + "INNER JOIN EDITER E ON E.LIVREISBN = L.LIVREISBN "
	            + "INNER JOIN EDITEUR EDIT ON EDIT.EDITEURID = E.EDITEURID "
	            + "WHERE EDITEURNOM = '"+editeur+"';";
		ResultSet res = stmt.executeQuery(query);
		   while (res.next()) {
		       Vector livre = new Vector();
		       livre.add(res.getString("LIVREISBN"));
		       livre.add(res.getString("LIVRETITRE"));
		       livre.add(res.getString("LIVRESOUSTITRE"));
		       livre.add(res.getFloat("LIVREPRIXHT"));
		       livre.add(res.getString("TVATAUX"));
		       livre.add(res.getString("LIVREDATEEDITION"));
		       livre.add(res.getString("LIVREIMAGE"));
		       livre.add(res.getString("LIVRERESUME"));
		       livre.add(res.getFloat("LIVRENBREPAGE"));
		       livre.add(res.getString("LIVRESTOCK"));
		       livre.add(res.getString("LIVRECOMMENT"));
		       livre.add(res.getString("LIVRESTATUT"));
		       livres.add(livre);
		   }
		return livres;
	}


	@Override
	public Vector recupererAuteur(String isbn) throws SQLException {
		// TODO Auto-generated method stub
		
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT AUTEURNOM, AUTEURPRENOM, AUTEURPSEUDO "
				+ "FROM AUTEUR A "
	            + "INNER JOIN ECRIRE E ON A.AUTEURID = E.AUTEURID  "
	            + "WHERE LIVREISBN = '"+isbn+"';";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			Vector <String> auteur = new Vector();
			auteur.add(result.getString("AUTEURNOM"));
			auteur.add(result.getString("AUTEURPRENOM"));
			auteur.add(result.getString("AUTEURPSEUDO"));
			livres.add(auteur);
		}
		return livres;
	}


	@Override
	public String recupererEditeur(String isbn) throws SQLException {
		// TODO Auto-generated method stub
		String editeur = "";
		stmt = myConnection.createStatement();
		String query = "SELECT EDITEURNOM FROM EDITEUR ED "
	            + "INNER JOIN EDITER E ON ED.EDITEURID = E.EDITEURID "
	            + "WHERE LIVREISBN = '"+isbn+"';";
		ResultSet resultat = stmt.executeQuery(query);
		while (resultat.next()) {
			editeur = resultat.getString("EDITEURNOM");
		}
		return editeur;
	}


	@Override
	public Vector recupererGenre(String isbn) throws SQLException {
		// TODO Auto-generated method stub
		Vector <String> livres = new Vector();
		return livres;
	}


	@Override
	public Vector recupererMotCle(String isbn) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT MOTCLELIBELLE "
				+ "FROM MOT_CLE M "
	            + "INNER JOIN ATTRIBUER1 A ON A.MOTCLEID = M.MOTCLEID  "
	            + "WHERE LIVREISBN = '"+isbn+"';";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			Vector<String> motcle = new Vector();
			motcle.add(result.getString("MOTCLELIBELLE"));
			livres.add(motcle);
		}
		return livres;
	}


	@Override
	public Vector recupererTheme(String isbn) throws SQLException {
		// TODO Auto-generated method stub
		Vector <Vector> livres = new Vector();
		stmt = myConnection.createStatement();
		String query = "SELECT THEMENOM "
				+ "FROM THEME T "
	            + "INNER JOIN AVOIR A ON A.THEMEID = T.THEMEID  "
	            + "WHERE LIVREISBN = '"+isbn+"';";
		ResultSet result = stmt.executeQuery(query);
		while (result.next()) {
			Vector <String> theme = new Vector();
			theme.add(result.getString("THEMENOM"));
			livres.add(theme);
		}
		return livres;
	}






}
