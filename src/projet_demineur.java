
// - N'oubliez pas de renseigner vos deux noms
// Pierre-Edouard Hermenier Groupe :4A élève 1/2
// Arthur Comte Groupe :4A élève 2/2
//
// - Pour chaque question, le squelette donne le nom de la fonction à écrire mais *pas* la signature
//   il faut remplir les types d'entrées et de sorties (indiqués par ?) et remplir l'intérieur du code de chaque fonction.
//
// - L'unique fichier de code que vous soumettrez sera ce fichier Java, donc n'hésitez pas à le commenter abondamment.
//   inutile d'exporter votre projet comme archive Zip et de rendre ce zip.
//   Optionnel : vous pouvez aussi joindre un document PDF donnant des explications supplémentaires (si vous utilisez OpenOffice/LibreOffice/Word, exportez le document en PDF), avec éventuellement des captures d'écran montrant des étapes affichées dans la console
//
// - Regardez en ligne sur le Moodle pour le reste des consignes, et dans le fichier PDF du sujet du projet
//   https://foad.univ-rennes1.fr/mod/assign/view.php?id=534254
//
// - A rendre avant le vendredi 04 décembre, maximum 23h59.
//
// - ATTENTION Le projet est assez long, ne commencez pas au dernier moment !
//
// - Enfin, n'hésitez pas à contacter l'équipe pédagogique, en posant une question sur le forum du Moodle, si quelque chose n'est pas clair.
//

// Pour utiliser des scanners pour lire des entrées depuis le clavier
// utilisés en questions 4.d] pour la fonction jeu()
import java.util.Scanner;

// Pour la fonction entierAleatoire(a, b) que l'on vous donne ci-dessous
import java.util.concurrent.ThreadLocalRandom;


// L'unique classe de votre projet
public class projet_demineur {

	// Donné, utile pour la question 1.b]
	public static int entierAleatoire(int a, int b){
		// Renvoie un entier aléatoire uniforme entre a (inclus) et b (inclus).
		return ThreadLocalRandom.current().nextInt(a, b + 1);
	}


	//
	// Exercice 1 : Initialisation des tableaux
	//

	// Question 1.a] déclarez les variables globales T et Tadj ici
	static int[][] T; 
	static int[][] Tadj;
	static int Mines;
	static Scanner sc = new Scanner(System.in); //un seul Scanner pour toutes les commandes.

	// Question 1.b] Fonction init
	public static void init(int h, int l, int n) { 
		T = new int[h][l];
		Tadj = new int[h][l];
		int mines = n;
		int hauteurAlea = 0;
		int largeurAlea = 0;
		
		while(mines>0) {
			hauteurAlea = entierAleatoire(0,h-1);
			largeurAlea = entierAleatoire(0,l-1);
			if(Tadj[hauteurAlea][largeurAlea]!=-1) {
				Tadj[hauteurAlea][largeurAlea]=-1;
				mines--;
			}
		}
	
	}

	// Question 1.c] Fonction caseCorrecte
	public static boolean caseCorrecte(int i, int j) { 
		if(i>=0 && i<Tadj.length && j>=0 && j<Tadj[0].length)
			return true;
		return false;
	}

	// Question 1.d] Fonction calculerAdjacent
	public static void calculerAdjacent() {
		for(int i= 0; i<Tadj.length; i++) {
			for(int j = 0; j<Tadj[0].length; j++) {
				if(Tadj[i][j]!=-1) {
					for(int k1=-1; k1<2;k1++) {
						for(int k2=-1; k2<2;k2++) {
							if(caseCorrecte(i+k1,j+k2) && Tadj[i+k1][j+k2]==-1)
								Tadj[i][j]++;
						}
					}
				}
			}
		}
	}

	//
	// Exercice 2 : Affichage de la grille
	//

	// Question 2.a]
	public static void afficherGrille(boolean affMines) { 
		int num =0;
		char lettre ='A';
		
		System.out.print("  "+"|");
		for(int i=0; i<T[0].length;i++) {
			if(lettre=='[')
				lettre = 'a';
			System.out.print(lettre+"|");
			lettre++;
		}
		System.out.println();
		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[0].length; j++) {
				if(j==0) { 
					System.out.print(num<10?"0"+num++:num++);
					System.out.print("|");
				}
					if(T[i][j]==0 && !affMines || affMines && T[i][j]==0 && Tadj[i][j]!=-1) {
						System.out.print(" ");
					}
					if(T[i][j]==1 && Tadj[i][j]!=-1) {
						System.out.print(Tadj[i][j]);
					}
					if(T[i][j]==2 && !affMines || affMines && T[i][j]==2 && Tadj[i][j]!=-1) {
						System.out.print("X");
					}
					if(Tadj[i][j]==-1 && affMines) {
						System.out.print("!");
					}
					System.out.print("|");
			}
			System.out.println();
		}
	}


	//
	// Exercice 3 : Révéler une case
	//

	// Question 3.a]
	public static boolean caseAdjacenteZero(int i, int j) { 
		
		for(int k1=-1; k1<2;k1++) {
			for(int k2=-1; k2<2;k2++) {
				if(caseCorrecte(i+k1,j+k2) && Tadj[i+k1][j+k2]==0 && T[i+k1][j+k2]==1)
					return true;
			}
		}			
					return false;
	}

	// Question 3.b]
	public static void revelation(int i, int j) { 
		int nonMinesAdjacente =0;
		T[i][j]=1;
		
		if(Tadj[i][j]==0) {
			nonMinesAdjacente++;
			while(nonMinesAdjacente>0) {
				for(int ii=0; ii<T.length;ii++) {
					for(int jj=0; jj<T[0].length;jj++) {
						if(caseAdjacenteZero(ii,jj) && T[ii][jj]==0) {
							T[ii][jj]=1;
							nonMinesAdjacente++;
						}
					}
				}
				nonMinesAdjacente--;
			}
			
		}

	}


	// Question 3.c] Optionnel
	public static void revelation2(int i, int j) { 
		T[i][j]=1;
		if(Tadj[i][j]==0)
			for(int ii=-1; ii<2;ii++) {
				for(int jj=-1; jj<2;jj++) {
					if(caseCorrecte(i+ii,j+jj) && caseAdjacenteZero(i+ii,j+jj) && T[i+ii][j+jj]==0) {
						T[i+ii][j+jj]=1;
						revelation2(i+ii,j+jj);
					}	
				}
			}
	
	}

	// Question 3.d]
	public static void actionDrapeau(int i, int j) { 
		if(T[i][j]==0)
			T[i][j]=2;
		else if(T[i][j]==2)
			T[i][j]=0;
	}
	
	
	// Question 3.e]
	public static boolean revelerCase(int i, int j) { 
		if(Tadj[i][j]==-1) {
			T[i][j]=1; //utile pour l'affichage
			return false;
		}
		else
			revelation2(i,j);
		return true;
	}


	//
	// Exercice 4 : Boucle de jeu
	//

	// Question 4.a]
	public static boolean aGagne() {
		for(int i= 0; i<Tadj.length; i++) {
			for(int j = 0; j<Tadj[0].length; j++) {
				if(Tadj[i][j]!=-1 && T[i][j] !=1)
					return false;
			}
		}
		return true;
	}

	// Question 4.b]
	public static boolean verifierFormat(String s) { 
		if(s.equalsIgnoreCase("aide"))
			return true;
		
		if(!s.isEmpty()) {
			if(s.length()==4)
				if(s.charAt(0)=='d' || s.charAt(0)=='r')
					if(s.charAt(1)>='0' && s.charAt(1)<='9')
						if(s.charAt(2)>='0' && s.charAt(2)<='9')
							if(s.charAt(3)>='A' && s.charAt(3)<='z')
								return true;
		}
		return false;
		
	}

	// Question 4.c]
	public static int[] conversionCoordonnees(String input) { 
		int[] actionNum = new int[3];
		int numLigne = input.charAt(1)-'0';
		actionNum[0] = numLigne == 0 ? input.charAt(2)-'0' : (input.charAt(1)-'0')*10 + (input.charAt(2)-'0'); 
		actionNum[1] = input.charAt(3)-'A';
		actionNum[2] = input.charAt(0) == 'r' ? 1 : 0; 
		return actionNum;
	}

	// Question 4.d]
	public static void jeu() {
		boolean perdu = false;
        String commande ="";
        
        //on boucle le jeu tant que l'on a ni gagné ni perdu
		while(!aGagne() && !perdu) {
			afficherGrille(perdu);
			System.out.println();
			//on vérifie la commande rentrer par l'utilisateur et on reaffiche la commande à saisir si le format n'est pas correcte
			while(!verifierFormat(commande) || !caseCorrecte(conversionCoordonnees(commande)[0],conversionCoordonnees(commande)[1])) {
				System.out.println("Saisir la commande :");
				commande = sc.next();
				if(commande.equalsIgnoreCase("aide")) {// si une aide est demandé, on lance l'aide
					aide();
					afficherGrille(perdu);
				}
			}
			//conversion des coordonnées pour être utilisable
			if(conversionCoordonnees(commande)[2]==1)
				if(!revelerCase(conversionCoordonnees(commande)[0],conversionCoordonnees(commande)[1]))
					perdu = true;
			if(conversionCoordonnees(commande)[2]==0)
				actionDrapeau(conversionCoordonnees(commande)[0],conversionCoordonnees(commande)[1]);
		 commande = "";
		}
		if(perdu) {
			System.out.println("Perdu !");
			afficherGrille(perdu);
		}else {
			System.out.println("Gagné !");
			afficherGrille(perdu);
		}
		
	}

	// Question 4.e]
	// Votre *unique* méthode main
	public static void main(String[] args) {
		int hauteur = 0;
		int largeur =0;
		int choixMenu=-1;
		int choixMenuIA=-1;
		int choixAffichage=-1;
		boolean quitter= false;
		boolean affichage = false;
		
		//tant que l'on souhaite jouer ou lancer l'IA on affiche le menu
		while(!quitter) {
			while(choixMenu<0 || choixMenu>2) {
				affichageMenu();
				choixMenu = sc.nextInt();
			}
			if(choixMenu==1) {//si on choisi l'IA
				while(choixMenuIA<0 || choixMenuIA>4) {
					affichageMenuIA();
					choixMenuIA = sc.nextInt();
				}
				while(choixAffichage<0 || choixAffichage>1) {
					affichageGrille();
					choixAffichage = sc.nextInt();
					if(choixAffichage==0)//si on choisi d'afficher toutes les grilles jouer par l'IA (à chaque commande)
						affichage=true;
					else
						affichage=false;
				}
				if(choixMenuIA!=4)// si on ne quitte pas alors on lance l'IA
					statistiquesVictoiresIA(choixMenuIA,affichage);
			}
			
			if(choixMenu==0) {// si on souhaite jouer nous même on définit les paramètres du jeu
				while(hauteur<1 || hauteur>99) {
					System.out.println("hauteur ?");
					hauteur = sc.nextInt();
				}
				while(largeur<1 || largeur>52) {
					System.out.println("largeur ?");
					largeur = sc.nextInt();
				}
				while(Mines<1 || Mines>hauteur *largeur-1) {
					System.out.println("nombres de mines ?");
					Mines = sc.nextInt();
				}
				init(hauteur,largeur,Mines);
				calculerAdjacent();
				jeu();//on lance le jeu après avoir initialisé celui-ci avec les paramètres
			}
			if(choixMenu==2)//si on choisi de quitter le Menu
				quitter=true;
				
		    //réinitialisation des valeurs 	
			choixMenu=-1;
			choixMenuIA=-1;
			choixAffichage=-1;
			hauteur=0;
			largeur=0;
			Mines=0;
		}
		sc.close();
	}


	//
	// Exercice 5 bonus challenge : Pour aller plus loin
	//
	
	public static void affichageMenu() {
		System.out.println("***********************");
		System.out.println("*      DEMINEUR       *");
		System.out.println("***********************");
		System.out.println();
		System.out.println("Menu:");
		System.out.println("-Jouer : 0");
		System.out.println("-Lancer IA : 1");
		System.out.println("-Quitter : 2");
		System.out.println();
	}
	
	public static void affichageMenuIA() {
		System.out.println("***********************");
		System.out.println("*      DEMINEUR       *");
		System.out.println("***********************");
		System.out.println();
		System.out.println("IA grilles:");
		System.out.println("-Lancer 1000 aléatoires 8x8 avec 10 mines : 0");
		System.out.println("-Lancer 1000 aléatoires 16*16 avec 40 mines : 1");
		System.out.println("-Lancer 1000 aléatoires 30*16 avec 99 mines : 2");
		System.out.println("-Lancer 1000 aléatoires de chacune des grilles : 3");
		System.out.println("-Retour Menu : 4");
	}
	public static void affichageGrille() {
		System.out.println("***********************");
		System.out.println("*      DEMINEUR       *");
		System.out.println("***********************");
		System.out.println();
		System.out.println("Afficher la grille à chaque commande jouée:");
		System.out.println("-Oui : 0");
		System.out.println("-Non : 1");
	}

	// Question 5.a] Optionnel
	public static void aide() {
		//L'aide affiche sur la grille un drapeau à l'emplacement d'une mine sans consulter les cases non découvertes donc avec une proba = 100%
		int[][] tabProb = new int[T.length][T[0].length];
		int mines=0 , caseI =0, caseJ=0;
		char c='A';
		
		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[i].length;j++) {
				if(T[i][j]==1 && Tadj[i][j]!=0) {
					for(int ki=-1; ki<2; ki++) {
						for(int kj=-1; kj<2; kj++) {
							if(caseCorrecte(i+ki,j+kj) && T[i+ki][j+kj]==0)
								tabProb[i+ki][j+kj]++;
						}
					}
				}
			}
		}
		for(int i=0; i<tabProb.length; i++) {
			for(int j=0; j<tabProb[i].length; j++) {
				if(tabProb[i][j]>mines && Tadj[i][j]==-1) {
					mines=tabProb[i][j];
					caseI=i;
					caseJ=j;
				}
					
			}
		}
		c=(char)(c+caseJ);//conversion pour afficher la lettre correspondante
		if(tabProb[caseI][caseJ]!=0) {
			System.out.println("la case :"+caseI+" "+c+" contient une mine");
			T[caseI][caseJ]=2;
		}
		else
			System.out.println("Pas d'aide disponible à ce niveau de jeu"); //si on ne trouve pas de mine a afficher avec le niveau de jeu actuel
	}

	// Question 5.b] Optionnel{
	public static boolean intelligenceArtificielle(boolean affichage) {
		Probabilite prob = new Probabilite();
		boolean perdu = false;
		while(!aGagne() && !perdu) {
			boolean trouve = false;
			if(affichage) {
				System.out.println();
				afficherGrille(perdu);
			}
			prob.tabProbAjour();// a chaque debut de tour de boucle, on mets à jour le tableau tabProb 
			if(!trouve) {//si on a pas une commande à jouer
				for(int i=0; i<T.length; i++) {
					for(int j=0; j<T[i].length; j++) {
						if(prob.caseSafe(i, j) && T[i][j]==0) {//on regarde d'abord si il on peut jouer une case sans risque
							prob.commande =new int[3];
							prob.commande[0]=i;
							prob.commande[1]=j;
							prob.commande[2]=1;
							trouve=true;
						}
					}
				}
			}
			if(!trouve) { 
				for(int i=0; i<T.length; i++) {
					for(int j=0; j<T[i].length; j++) {
						if(T[i][j]==0 && prob.estUneMine(i, j)) {// après on regarde si on peut poser un drapeau
							prob.commande =new int[3];
							prob.commande[0]=i;
							prob.commande[1]=j;
							prob.commande[2]=0;
							trouve=true;
						}
					}
				}
			}
			if(!trouve) {
					prob.commande =new int[3];
					prob.tabStatAJour(Mines);
				
					double plusSafe = 200;
					for(int i=0; i<T.length; i++) {
						for(int j=0; j<T[i].length; j++) {
							if(prob.tabStat[i][j]<plusSafe && T[i][j]==0) { //sinon on regarde la case qui a le moins de chance de contenir une mine
								prob.commande[0]=i;
								prob.commande[1]=j;
								prob.commande[2]=1;
								plusSafe=prob.tabStat[i][j];
							}
						}
					}
					trouve=true;
			}
				
				if(affichage) {
					System.out.println();
					System.out.println("La commande jouée: " + prob.commande[0]+" , "+prob.commande[1]+" , "+prob.commande[2]);
				}
				//condition qui lance la commande pour retourner une case	
				if(prob.commande[2]==1 && !revelerCase(prob.commande[0],prob.commande[1]))
					perdu = true;
				
				if(prob.commande[2]==0)
					actionDrapeau(prob.commande[0],prob.commande[1]);
	            trouve=false; // on reinitialise la variable trouve après avoir joué
		}
		if(perdu) {
			if(affichage) {
				System.out.println("Perdu !");
				afficherGrille(perdu);
				System.out.println();
			}
			return false;
		}else {
			if(affichage) {
				System.out.println("Gagné !");
				afficherGrille(perdu);
				System.out.println();
			}
			return true;
		}
	}

	// Question 5.c] Optionnel
	public static void statistiquesVictoiresIA(int grille, boolean affichage) {
		int[] hauteur = {8,16,30};
		int[] largeur = {8,16,16};
		int[] mines = {10,40,99};
		int victoires = 0;
		double pourcentageVictoire = 0.0;
		int jeux=0;
		int j=0;
		int mem=0;
		// on récupère le paramètre grille pour connaître le type de grille à lancer
		if(grille==0) {
			jeux=1;
			j=0;
		}
		if(grille==1) {
			jeux=2;
			j=1;
		}
		if(grille==2) {
			jeux=3;
			j=2;
		}
		if(grille==3) {
			jeux=3;
			j=0;
		}
		mem=j;
		// on lance les 1000 tests sur un type de grille 
		for(int i=0; i<1000; i++) {
			for(; j<jeux; j++) {
				Mines = mines[j];
				init(hauteur[j],largeur[j],mines[j]);
				calculerAdjacent();	
				if(intelligenceArtificielle(affichage))
					victoires++;
			}
			j=mem;//on reinitialise j car définit en dehors de la boucle
		}
		pourcentageVictoire = (double)victoires/(1000*(jeux-j))*100;
		System.out.println("Pourcentage de victoire: "+pourcentageVictoire+"%");
	}

}

class Probabilite extends projet_demineur {
	
	public int[] commande;
	public int[][] tabProb;
	public double[][] tabStat;
	
//	public static Probabilite(){
//	
//	}
	
	public void tabProbAjour() {
		tabProb = new int[T.length][T[0].length];
		//on récupère toute les valeurs que l'on connait (T[i][j]==1)
		for(int i=0; i<tabProb.length; i++) {
			for(int j=0; j<tabProb[i].length; j++) {
				if(T[i][j]==1) {
					tabProb[i][j]=Tadj[i][j];
				}
				else
					tabProb[i][j]=-2;//sinon la cause à une valeur par default de -2
			}
		}
		//si un drapeau est adjacent à une case découverte alors on descent sa valeur de 1 pour avoir une valeur correspondante au nombre de mines encore à chercher
		for(int i=0; i<tabProb.length; i++) {
			for(int j=0; j<tabProb[i].length; j++) {
				if(T[i][j]==2) {
					for(int ii=-1; ii<2; ii++) {
						for(int jj=-1; jj<2; jj++) {
							if(caseCorrecte(ii+i,jj+j) && tabProb[ii+i][jj+j]>0 && ii+jj!=0)
								tabProb[ii+i][jj+j]--;
						}
					}
				}
			}
		}
	}
	
	//tabStat est un tableau de statistique qui indique, pour chaque case nonDecouverte le pourcentage de chance de contenir une mine 
	public void tabStatAJour(int mines) {
		tabStat = new double[T.length][T[0].length];
		
		
		// on se positionne sur une case decouverte > 0
		for(int i=0; i<tabProb.length; i++) {
			for(int j=0; j<tabProb[i].length; j++) {
				if(T[i][j]==1 && Tadj[i][j]>0) {
					// on regarde uniquement les cases adjacentes si elles existent
					for(int ii=-1; ii<2; ii++) {
						for(int jj=-1; jj<2; jj++) {
							//on calcul sa probabilite 
							if(caseCorrecte(ii+i,jj+j) && T[ii+i][jj+j]==0 && ii+jj!=0 && (double)tabProb[i][j]/(double)nombreCaseAdjacentsNonDecouvertes(i,j)*100 > tabStat[ii+i][jj+j]) {
								tabStat[ii+i][jj+j]= (double)tabProb[i][j]/(double)nombreCaseAdjacentsNonDecouvertes(i,j)*100;
							}
						}
					}
				}
			}
		}
		
		// pour les autres cases la probabilité est calculée en fonction du nombre de mines restantes non connues(les drapeaux sont des mines connues) par rapport au nombre
		// de cases non découvertes
		for(int i=0; i<tabStat.length; i++) {
			for(int j=0; j<tabStat[i].length; j++) {
				if(tabStat[i][j]<(double)(mines-nombreDrapeauPose())/(double)nombreCaseNonDecouvertes()*100) {
					tabStat[i][j]= (double)(mines-nombreDrapeauPose())/(double)nombreCaseNonDecouvertes()*100;
				}
			}
		}
	}
	
	public int nombreCaseAdjacentsNonDecouvertes(int i, int j) {
		int compt=0;
		
		for(int ii=-1; ii<2; ii++) {
			for(int jj=-1; jj<2; jj++) {
				if(caseCorrecte(ii+i,jj+j) && T[ii+i][jj+j]==0 && ii+jj!=0)
					compt++;
			}
		}
		return compt;
	}
	
	public int nombreCaseNonDecouvertes() {
		int compt=0;
		
		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[i].length; j++) {
				if(T[i][j]==0)
					compt++;
			}
		}
		return compt;
	}
	
	public int nombreDrapeauPose() {
		int compt=0;
		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[i].length; j++) {
				if(T[i][j]==2)
					compt++;
			}
		}
		return compt;
	}
	
	public boolean caseSafe(int i, int j) {
		for(int ii=-1; ii<2; ii++) {
			for(int jj=-1; jj<2; jj++) {
				if(!estUneMine(i,j) && caseCorrecte(ii+i,jj+j)) {
					if(Tadj[ii+i][jj+j]>0 && drapeauAdjacent(ii+i,jj+j,Tadj[ii+i][jj+j]) && ii+jj!=0 || tabProb[ii+i][jj+j]==0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean drapeauAdjacent(int i, int j, int nombreDeMines) {
		int nombreDrapeau=0;
		for(int ii=-1; ii<2; ii++) {
			for(int jj=-1; jj<2; jj++) {
				if(caseCorrecte(ii+i,jj+j) && T[i+ii][j+jj]==2)
					nombreDrapeau++;
			}
		}
		if(nombreDrapeau==nombreDeMines)
			return true;
		else
			return false;
	}
	
	public boolean estUneMine(int i, int j) {
		for(int ii=-1; ii<2; ii++) {
			for(int jj=-1; jj<2;jj++) {
				if(caseCorrecte(ii+i,jj+j) && T[ii+i][jj+j]==1 && verificationMine(i+ii,j+jj) || T[i][j]==2) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean verificationMine(int i, int j) {
		int nombreNonDecouvert=0;
		int nombreDrapeau =0;
		
		for(int ii=-1; ii<2; ii++) {
			for(int jj=-1; jj<2;jj++) {
				if(caseCorrecte(ii+i,jj+j)) {
					if(T[ii+i][j+jj]==0)
						nombreNonDecouvert++;
					if(T[ii+i][j+jj]==2)
						nombreDrapeau++;
				}
			}
		}
		if(nombreNonDecouvert==Tadj[i][j]-nombreDrapeau)
			return true;
		
		return false;
	}
}