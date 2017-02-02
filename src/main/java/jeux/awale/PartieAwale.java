package jeux.awale;

import java.util.ArrayList;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.alg.Minimax;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;
import jeux.awale.HeuristiqueAwale;
import jeux.awale.PlateauAwale;;

public class PartieAwale {
	public static void main(String[] args) {

		Joueur jUn = new Joueur("Un");
		Joueur jDeux = new Joueur("Deux");

		Joueur[] lesJoueurs = new Joueur[2];

		lesJoueurs[0] = jUn;
		lesJoueurs[1] = jDeux;

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
		AlgoJoueur[0] = new AlphaBeta(HeuristiqueAwale.hUn, jUn, jDeux, 3);
		AlgoJoueur[1] = new AlphaBeta(HeuristiqueAwale.hDeux, jDeux, jUn,6);

		System.out.println("TD IIA n.3 - Algorithmes pour les Jeux");
		System.out.println("Etat Initial du plateau de jeu:");

		boolean jeufini = false;
		CoupJeu meilleurCoup = null;
		int jnum;

		PlateauAwale plateauCourant = new PlateauAwale();
		PlateauAwale.setJoueurs(jUn, jDeux);
		// Pour savoir qui joue "deux" et qui joue "un"

		// A chaque itération de la boucle, on fait jouer un des deux joueurs
		// tour a tour
		jnum = 0; // On commence par le joueur Un (arbitraire)

		while (!jeufini) {
			System.out.println("" + plateauCourant);

			// Vérifie qu'il y a bien des coups possibles
			// Ce n'est pas tres efficace, mais c'est plus rapide... a écrire...
			ArrayList<CoupJeu> lesCoupsPossibles = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
			if ((lesCoupsPossibles.size()) > 0 && (!(plateauCourant.finDePartie()))) {

				System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");
				System.out.println("Coups possibles pour " + lesJoueurs[jnum] + " : " + lesCoupsPossibles);

				// On écrit le plateau

				// Lancement de l'algo de recherche du meilleur coup
				System.out.println("Recherche du meilleur coup avec l'algo " + AlgoJoueur[jnum]);
				meilleurCoup = AlgoJoueur[jnum].meilleurCoup(plateauCourant);
				System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + lesJoueurs[jnum]);

				plateauCourant.joue(lesJoueurs[jnum], meilleurCoup);
				// Le coup est effectivement joué
				jnum = 1 - jnum;

			} else {
				System.out.println(plateauCourant.printWinner());
				System.out.println("");
				jeufini = true;

			}
		}
	}

}
