package jeux.awale;

import iia.jeux.modele.CoupJeu;

public class CoupAwale implements CoupJeu {

	/****** Attributs *******/

	private int ligne;

	private int colonne;


	/****** Constructeur *******/

	public CoupAwale(int l, int c) {
		ligne = l;
		colonne = c;
	}

	/****** Accesseurs *******/

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	/****** Accesseurs *******/

	public String toString() {
		return "("+ligne+","+colonne+")";
	}


}
