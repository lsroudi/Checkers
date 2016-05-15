package Strategy;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Description of Deplacement
 *
 * (c) lsroudi  <lsroudi@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
public class Deplacement {
	
	private Case _initial;
	private Case _final;
	private LinkedList<Deplacement> listDeplacement = null;
	
	
	
	
	public Case deplacer_piece(Deplacement coup,Damier damier){
		
		// traiter la case initial et la rendre vide
		ArrayList<ArrayList<Case>> cases = damier.getCases();
		
		ArrayList<Case> caseLigneInitial = cases.get(coup.getCaseInitial().getLigne());
		caseLigneInitial.set(coup.getCaseInitial().getColonne(), new Case(coup.getCaseInitial().getColonne(), coup.getCaseInitial().getLigne()));
		cases.set(this.getCaseInitial().getLigne(), caseLigneInitial);
		
		// 
		int ligne = (coup.getCaseFinal().getLigne()+coup.getCaseInitial().getLigne())/2;
		int col = (coup.getCaseFinal().getColonne()+coup.getCaseInitial().getColonne())/2;
		
		ArrayList<Case> ligneCase = cases.get(ligne);
		Case ca = ligneCase.get(col);
		// capture
		if(ca.isPieceHere()){
			if(ca.getPiece().getColor() == Color.BLUE){
				damier.setNbPieceBlue(damier.getNbPieceRouge()-1);
			}else{
				damier.setNbPieceRouge(damier.getNbPieceBlue()-1);
			}

			ligneCase.set(col, new Case(col, ligne));
			cases.set(ligne, ligneCase);
		}
		
		ArrayList<Case> caseLigneFinal = cases.get(coup.getCaseFinal().getLigne());
		caseLigneFinal.set(coup.getCaseFinal().getColonne(), coup.getCaseInitial());
		cases.set(coup.getCaseInitial().getLigne(), caseLigneInitial);
		
		return caseLigneFinal.get(coup.getCaseFinal().getColonne());
	}



	public Boolean checkDeplacement(Color pionCouleur,Damier damier){
		
		    ArrayList<ArrayList<Case>> cases = damier.getCases();
			//La piece sélectionnéé n’est pas celle du joueur
			ArrayList<Case> caseLigneInitial = cases.get(this.getCaseInitial().getLigne());
			Case caseInitial = caseLigneInitial.get(this.getCaseInitial().getColonne());
			if(caseInitial.getPiece().getColor() != pionCouleur)
				return false;
			//Case occupée
			ArrayList<Case> caseLigneFinal = cases.get(this.getCaseFinal().getLigne());
			Case caseFinal = caseLigneInitial.get(this.getCaseFinal().getColonne());
			
			if(!caseFinal.isPieceHere())
				return false ;
			//Mauvaise Ligne
			
			//Mauvaise colonne

			
		return true;
	}
	
	public Deplacement testDeplacement(Damier damier,int i, int j, Color pionCouleur){
		
		ArrayList<ArrayList<Case>> cases = damier.getCases();

		if(i >= 1 && i <= 8 && j >= 1 && j<= 8  ){
			
			Case c = cases.get(i).get(j);
			
			this.setCaseInitial(c);
			//Case caseFinal = new Case(j+1, i+1);
			
			Case ca1 = cases.get(i+1).get(j+1);
			if(ca1.isPieceHere() && ca1.getPiece().getColor() == pionCouleur ){
				return null;
				
			}else if(ca1.isPieceHere() && ca1.getPiece().getColor() != pionCouleur ){
				
				ca1 = cases.get(i+2).get(j+2);
				if(ca1.isPieceHere()){
					return null;
				}else{
					this.setCaseFinal(ca1);
				}
				
			}else if(!ca1.isPieceHere()){
				this.setCaseFinal(ca1);
			}
			
			Case ca2 = cases.get(i+1).get(j-1);
			if(ca2.isPieceHere() && ca1.getPiece().getColor() == pionCouleur ){
				return null;
			}else if(ca2.isPieceHere() && ca2.getPiece().getColor() != pionCouleur ){
				ca2 = cases.get(i+2).get(j-2);
				if(ca1.isPieceHere()){
					return null;
				}else{
					this.setCaseFinal(ca2);
				}
			}else if(!ca2.isPieceHere()){
				this.setCaseFinal(ca2);
			}
			
			return this;
			
		}
		
		return null;
		
	}
	
	public LinkedList<Deplacement> trouverUnCoup(Damier d,Color pionCouleur){
		
		int i = 0;
		int nbPion = 0;
		int max;
		listDeplacement = this.createListDeplacement();
		
		if(pionCouleur == Color.BLUE) {
			
			max = d.getNbPieceBlue();		
		}
		else{
			
			max = d.getNbPieceRouge();
		}
		
		ArrayList<ArrayList<Case>> cases = d.getCases();
		
		while(nbPion<max) {
			
			for(int j=0 ;j<10 ;j++) {
				
				Case c = cases.get(i).get(j);
				
				if(c.isPieceHere() && c.getPiece().getColor() == pionCouleur){
					
					nbPion++;
					
					Deplacement deplacement;
					if((deplacement = testDeplacement(d, i, j,pionCouleur)) != null){
						
						listDeplacement.add(deplacement);
						
						
					}
//						else {
//						if(deplacement = testDeplacementDame(d, i, j)) != null){
//							
//						}
							
					
				}
			}
			
			i++;
		}
		
		return listDeplacement;
		
	}
	
	public Case jouerCpu(Damier jeu,Color couleur) {
		
		Deplacement deplacement = null;
		Case c = null;
		
		LinkedList<Deplacement> coups = trouverUnCoup(jeu, couleur);
		
		if(coups.size() > 0){
			
			deplacement = coups.get(0);
			
			c = deplacer_piece(deplacement, jeu);
		}



		return c ;
				
	}
		
	/**
	 * @return the _initial
	 */
	public Case getCaseInitial() {
		return _initial;
	}





	/**
	 * @param _initial the _initial to set
	 */
	public void setCaseInitial(Case _initial) {
		this._initial = _initial;
	}





	/**
	 * @return the _final
	 */
	public Case getCaseFinal() {
		return _final;
	}





	/**
	 * @param _final the _final to set
	 */
	public void setCaseFinal(Case _final) {
		this._final = _final;
	}



	/**
	 * @return the listDeplacement
	 */
	public LinkedList<Deplacement> getListDeplacement() {
		return listDeplacement;
	}



	/**
	 * @param listDeplacement the listDeplacement to set
	 */
	public void addListDeplacement(Deplacement d) {
		
		this.listDeplacement.add(d);
	}
	
	/**
	 * @param listDeplacement the listDeplacement to set
	 * @return 
	 */
	public LinkedList<Deplacement> createListDeplacement() {
		
		return new LinkedList<Deplacement>();
		
	}	
	
	
	
}
