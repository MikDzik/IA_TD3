package jeux.awale;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;
import jeux.dominos.PlateauDominos;

public class HeuristiqueAwale {

	public static  Heuristique hUn = new Heuristique(){

		public int eval(PlateauJeu p, Joueur j){
			return 0;
		}
	};

	public static  Heuristique hDeux = new Heuristique(){

		public int eval(PlateauJeu p, Joueur j){
			return 0;
		}
	};


}
