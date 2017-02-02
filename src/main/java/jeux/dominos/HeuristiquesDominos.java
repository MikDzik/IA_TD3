package jeux.dominos;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class HeuristiquesDominos {

	public static Heuristique hblanc = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			if (p.getClass().equals(PlateauDominos.class)) {
				PlateauDominos p2 = (PlateauDominos) p;
				if (p2.nbCoupsBlanc() == 0) {
					return 99;
				}
				return p2.nbCoupsNoir() - p2.nbCoupsBlanc();
			} else
				return -100;
		}
	};

	public static Heuristique hnoir = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			if (p.getClass().equals(PlateauDominos.class)) {
				PlateauDominos p2 = (PlateauDominos) p;
				if (p2.nbCoupsNoir() == 0) {
					return 99;
				}
				return p2.nbCoupsBlanc() - p2.nbCoupsNoir();
			}

			else {
				return -100;

			}

		}
	};

}
