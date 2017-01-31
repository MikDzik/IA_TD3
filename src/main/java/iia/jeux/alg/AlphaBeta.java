package iia.jeux.alg;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class AlphaBeta implements AlgoJeu {
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
	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
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
	@Override
	public CoupJeu meilleurCoup(PlateauJeu p) {
		int max = Integer.MIN_VALUE;
		CoupJeu toJoue = null;
		for (CoupJeu coup : p.coupsPossibles(joueurMax)){
			PlateauJeu temp = p.copy();
			temp.joue(joueurMax, coup);

			int tmpScore = alphaBeta(temp, this.profMax - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			if (tmpScore >= max){
				max = tmpScore;
				toJoue = coup;
			}
		}
		return toJoue;
	}


	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "AlphaBeta(ProfMax=" + profMax + ")";
	}

	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------
	private int alphaBeta(PlateauJeu p, int profMax2, int alpha, int beta, boolean maximizingPlayer) {
		if (profMax2 == 0 || p.coupsPossibles(joueurMax).size() == 0){
			return h.eval(p, joueurMax);
		}
		if (maximizingPlayer){
			int v = Integer.MIN_VALUE;
			for (CoupJeu coup : p.coupsPossibles(joueurMax)){
				PlateauJeu temp = p.copy();
				temp.joue(joueurMax, coup);
				v = Math.max(v, alphaBeta(temp, profMax2 - 1, alpha, beta, false));
				alpha = Math.max(alpha, v);
				if (beta <= alpha)
					break;

			}
			return v;
		}
		else{
			int v = Integer.MAX_VALUE;
			for (CoupJeu coup : p.coupsPossibles(joueurMin)){
				PlateauJeu temp = p.copy();
				temp.joue(joueurMin, coup);
				v = Math.min(v, alphaBeta(temp, profMax2 - 1, alpha, beta, true));
				beta = Math.min(beta, v);
				if (beta <= alpha)
					break;

			}
			return v;

		}

	}

}
