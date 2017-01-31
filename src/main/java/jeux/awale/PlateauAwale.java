package jeux.awale;

import java.io.PrintStream;
import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;
import jeux.dominos.CoupDominos;
import jeux.dominos.PlateauDominos;

public class PlateauAwale implements PlateauJeu {

	/* *********** constantes *********** */

	/** Longeur de la plateau */
	public final static int TAILLE = 6;

	/* *********** Paramètres de classe *********** */
	private final static int VIDE = 0;
	private final static int INIT = 4;
	private final static int UN = 1;
	private final static int DEUX = 2;

	/** Le joueur que joue "Un" */
	private static Joueur joueurUn;

	/** Le joueur que joue "deux" */
	private static Joueur joueurDeux;

	/* *********** Attributs *********** */

	/** le damier */
	private int damier[][];

	/** les scores */
	private int scoreUn = 0;
	private int scoreDeux = 0;

	/************* Constructeurs ****************/

	public PlateauAwale() {
		damier = new int[2][TAILLE];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < TAILLE; j++)
				damier[i][j] = INIT;
	}

	public PlateauAwale(int depuis[][]) {
		damier = new int[2][TAILLE];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < TAILLE; j++)
				damier[i][j] = depuis[i][j];
	}

	/************* Gestion des paramètres de classe** ****************/

	public static void setJoueurs(Joueur jb, Joueur jn) {
		joueurUn = jb;
		joueurDeux = jn;
	}

	public boolean isJoueurUn(Joueur jb) {
		return joueurUn.equals(jb);
	}

	public boolean isJoueurDeux(Joueur jn) {
		return joueurDeux.equals(jn);
	}

	/************* Méthodes de l'interface PlateauJeu ****************/

	public PlateauJeu copy() {
		return new PlateauAwale(this.damier);
	}

	public boolean coupValide(Joueur joueur, CoupJeu c) {
		CoupAwale cd = (CoupAwale) c;
		int ligne = cd.getLigne();
		int colonne = cd.getColonne();
		return true;
		// return coupValide(joueur, ligne, colonne);
	}

	public ArrayList<CoupJeu> coupsPossibles(Joueur joueur) {
		ArrayList<CoupJeu> lesCoupsPossibles = new ArrayList<CoupJeu>();
		if (joueur.equals(joueurUn)) {
			if (affamer(joueurDeux)) {
				for (int i = 0; i < TAILLE; i++) {
					if (damier[0][i] > i) {
						lesCoupsPossibles.add(new CoupAwale(0, i));
					}
				}
			} else {
				for (int j = 0; j < TAILLE; j++) { // regarde sur rangee
													// joueur un
					if (damier[0][j] != VIDE) {
						CoupAwale coup = new CoupAwale(0, j);
						PlateauAwale p2 = (PlateauAwale) this.copy();
						p2.joue(joueurUn, coup);
						if (!p2.affamer(joueurDeux)) {
							lesCoupsPossibles.add(coup);
						}

					}

				}
			}
		} else { // joueur deux
			if (affamer(joueurUn)) {
				for (int i = 0; i < TAILLE; i++) {
					if (damier[1][i] >= TAILLE - i) {
						lesCoupsPossibles.add(new CoupAwale(1, i));
					}
				}
			} else {
				for (int j = 0; j < TAILLE; j++) { // regarde sur rangee joueur
													// deux
					if (damier[1][j] != VIDE) {
						CoupAwale coup = new CoupAwale(1, j);
						PlateauAwale p2 = (PlateauAwale) this.copy();
						p2.joue(joueurDeux, coup);
						if (!p2.affamer(joueurUn)) {
							lesCoupsPossibles.add(coup);
						}

					}
				}
			}
		}
		return lesCoupsPossibles;
	}

	public boolean finDePartie() {
		if ((scoreUn >= 25) || (scoreDeux >= 25)) {
			return true;
		}
		if (scoreUn + scoreDeux >= 42) {
			return true;
		}

		int nbCoupsUn = this.coupsPossibles(joueurUn).size();
		int nbCoupsDeux = this.coupsPossibles(joueurDeux).size();
		return (nbCoupsUn == 0 && nbCoupsDeux == 0);

	}

	public void joue(Joueur joueur, CoupJeu c) {
		CoupAwale cd = (CoupAwale) c;
		int ligne = cd.getLigne();
		int colonne = cd.getColonne();
		int graines = damier[ligne][colonne];
		damier[ligne][colonne] = VIDE;

		if (ligne == 0) {
			for (int i = colonne - 1; i >= 0; i--) {
				if (i != colonne && graines > 0) {
					damier[0][i]++;
					graines--;
				}
			}
			while (graines > 0) {

				for (int i = 0; i < TAILLE; i++) {
					if (graines > 0) {
						damier[1][i]++;
						graines--;
						if (graines == 0) {
							while (i < TAILLE) {
								if (damier[1][i] == 2 || damier[1][i] == 3) {
									scoreUn += damier[1][i];
									damier[1][i] = VIDE;
									i++;
								} else {
									break;
								}
							}
						}
					}
				}
				for (int i = TAILLE - 1; i >= 0; i--) {
					if (i != colonne && graines > 0) {
						damier[0][i]++;
						graines--;
					}
				}
			}
		} else {
			for (int i = colonne + 1; i < TAILLE; i++) {
				if (i != colonne && graines > 0) {
					damier[ligne][i]++;
					graines--;
				}
			}
			while (graines > 0) {
				for (int i = TAILLE - 1; i >= 0; i--) {
					if (graines > 0) {
						damier[0][i]++;
						graines--;
						if (graines == 0) {
							while (i >= 0) {
								if (damier[0][i] == 2 || damier[0][i] == 3) {
									scoreDeux += damier[0][i];
									damier[0][i] = VIDE;
								} else {
									break;
								}
							}
						}
					}
				}
				for (int i = 0; i < TAILLE; i++) {
					if (i != colonne && graines > 0) {
						damier[ligne][i]++;
						graines--;
					}
				}
			}

		}
	}

	/* ********************* Autres méthodes ***************** */

	/*
	 * pas necessaire pour moment private boolean coupValide(Joueur joueur,int
	 * l, int c) {
	 *
	 * if (joueur.equals(joueurUn)) return (damier[l][c] != VIDE &&
	 * damier[l][c+1] == VIDE); else return (damier[l][c] == VIDE &&
	 * damier[l+1][c] == VIDE); }
	 */

	private boolean affamer(Joueur j) {
		if (j == joueurUn) {
			for (int i : damier[0]) {
				if (i != VIDE) {
					return false;
				}
			}
			return true;
		} else {
			for (int i : damier[1]) {
				if (i != VIDE) {
					return false;
				}
			}
			return true;
		}
	}

	public String toString() {
		String retstr = new String("");
		retstr += "joueur un: " + scoreUn + "\n";
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < TAILLE; j++) {
				retstr += "|" + damier[i][j];
			}
			retstr += "|\n";
		}
		retstr += "joueurDeux" + scoreDeux + "\n";
		return retstr;
	}

	public void printPlateau(PrintStream out) {
		out.println(this.toString());
	}

}
