package jeux.dominos;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesDominos{

	public static  Heuristique hblanc = new Heuristique(){

		public int eval(PlateauJeu p, Joueur j){
			/*A COMPLETER*/
			return p.coupsPossibles(j).size();
		}
	};

	public static  Heuristique hnoir = new Heuristique(){

		public int eval(PlateauJeu p, Joueur j){
			/*A COMPLETER*/
			return p.coupsPossibles(j).size();
		}
	};

}
