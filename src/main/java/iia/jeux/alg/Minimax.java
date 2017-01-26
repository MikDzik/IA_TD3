/**
 *
 */

package iia.jeux.alg;

import java.util.ArrayList;

import com.sun.javafx.TempState;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;
import jeux.dominos.CoupDominos;
import jeux.dominos.HeuristiquesDominos;

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
		int max = -100000;
		int tmpScore = 0;
		PlateauJeu tmpPlat = p.copy();
		/// a changer
		CoupJeu aJoue = null;
		for (CoupJeu coup : tmpPlat.coupsPossibles(joueurMax)){
			tmpPlat.joue(joueurMax, coup);
			tmpScore = maxMin(tmpPlat, PROFMAXDEFAUT - 1);

			if (tmpScore > max){
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
		if ((p.coupsPossibles(joueurMin).size() == 1) || (profondeur == 0)) {
			return HeuristiquesDominos.hblanc.eval(p, joueurMin);
		}
		int max = -1000;
		int tmp = 0;

		PlateauJeu jeu = p.copy();
		for (CoupJeu coup : jeu.coupsPossibles(joueurMin)) {
			jeu.joue(joueurMin, coup);
			tmp = minMax(jeu, profondeur - 1);

			if (tmp > max) {
				max = tmp;
			}
		}

		return max;
	}

	private int minMax(PlateauJeu p, int profondeur) {
		if ((p.coupsPossibles(joueurMax).size() == 1) || (profondeur == 0)) {
			return HeuristiquesDominos.hnoir.eval(p, joueurMax);
		}
		int min = 1000;
		int tmp = 0;

		PlateauJeu jeu = p.copy();
		for (CoupJeu coup : jeu.coupsPossibles(joueurMax)) {
			jeu.joue(joueurMax, coup);
			tmp = maxMin(jeu, profondeur - 1);

			if (tmp < min) {
				min = tmp;
			}
		}

		return min;
	}

}
