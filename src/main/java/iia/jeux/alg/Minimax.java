/**
 *
 */

package iia.jeux.alg;

import java.util.ArrayList;

import com.sun.javafx.TempState;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class Minimax implements AlgoJeu {

	/**
	 * La profondeur de recherche par défaut
	 */
	private final static int PROFMAXDEFAUT = 4;

	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**
	 * La profondeur de recherche utilisée pour l'algorithme
	 */
	private int profMax = PROFMAXDEFAUT;

	/**
	 * L'heuristique utilisée par l'algorithme
	 */
	private Heuristique h;

	/**
	 * Le joueur Min (l'adversaire)
	 */
	private Joueur joueurMin;

	/**
	 * Le joueur Max (celui dont l'algorithme de recherche adopte le point de
	 * vue)
	 */
	private Joueur joueurMax;

	/**
	 * Le nombre de noeuds développé par l'algorithme (intéressant pour se faire
	 * une idée du nombre de noeuds développés)
	 */
	private int nbnoeuds;

	/**
	 * Le nombre de feuilles évaluées par l'algorithme
	 */
	private int nbfeuilles;

	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public int getNbfeuilles(){
		return nbfeuilles;
	}

	public int getNbnoeuds(){
		return nbnoeuds;
	}



	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		profMax = profMaxi;
		// System.out.println("Initialisation d'un MiniMax de profondeur " +
		// profMax);
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public CoupJeu meilleurCoup(PlateauJeu p) {
		/* A vous de compléter le corps de ce fichier */
		int max = Integer.MIN_VALUE;
		int tmpScore = Integer.MIN_VALUE;
		///PlateauJeu tmpPlat = p.copy();
		/// a changer
		CoupJeu aJoue = null;
		for (CoupJeu coup : p.coupsPossibles(joueurMax)) {
			PlateauJeu tmpPlat = p.copy();
			tmpPlat.joue(joueurMax, coup);
			tmpScore = minMax(tmpPlat, profMax - 1);

			if (tmpScore > max) {
				max = tmpScore;
				aJoue = coup;
			}

		}
		return aJoue;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "MiniMax(ProfMax=" + profMax + ")";
	}

	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	private int maxMin(PlateauJeu p, int profondeur) {
		nbnoeuds++;
		if ((p.coupsPossibles(joueurMax).size() == 0) || (profondeur == 0)) {
			nbfeuilles++;
			return h.eval(p, joueurMax);
		}
		int max = Integer.MIN_VALUE;
		int tmp = Integer.MIN_VALUE;


		for (CoupJeu coup : p.coupsPossibles(joueurMax)) {
			PlateauJeu jeu = p.copy();
			jeu.joue(joueurMax, coup);
			tmp = minMax(jeu, profondeur - 1);

			if (tmp > max) {
				max = tmp;
			}
		}

		return max;
	}

	private int minMax(PlateauJeu p, int profondeur) {
		nbnoeuds++;
		if ((p.coupsPossibles(joueurMin).size() == 0) || (profondeur == 0)) {
			nbfeuilles++;
			return h.eval(p, joueurMin);
		}
		int min = Integer.MAX_VALUE;
		int tmp = Integer.MAX_VALUE;


		for (CoupJeu coup : p.coupsPossibles(joueurMin)) {
			PlateauJeu jeu = p.copy();
			jeu.joue(joueurMin, coup);
			tmp = maxMin(jeu, profondeur - 1);

			if (tmp < min) {
				min = tmp;
			}
		}

		return min;
	}

}
