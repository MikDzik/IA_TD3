package jeux.awale;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class HeuristiqueAwale {

	public static Heuristique hUn = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			if (p.getClass().equals(PlateauAwale.class)) {
				PlateauAwale p1 = (PlateauAwale) p;
				int h = p1.gagnantj2();
				return h;
			}
			return -100;
		}
	};

	public static Heuristique hDeux = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			if (p.getClass().equals(PlateauAwale.class)) {
				PlateauAwale p1 = (PlateauAwale) p;
				int h = p1.gagnantj1();
				return h;

			}
			return -100;

		}
	};

}
