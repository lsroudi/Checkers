package Strategy;
import java.awt.Color;
import java.util.ArrayList;

import Board.Board;
import Board.Checker;
import Board.CheckerType;
import Board.Checkers;

/**
 * Description of Dammier
 *
 * (c) lsroudi  <lsroudi@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
public class Damier extends Checkers{

    
    public static final int ligne = 10;
    public static final int colonne = 10;
    private static ArrayList<ArrayList<Case>> cases;
    private int nbPieceBlue = 20;
    private int nbPieceRouge = 20;
    private Board board;
    
    
    public Damier(){
    	super();
    	strDamier();
    }


	private void strDamier() {
		
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	    this.board = new Board(this);
	      	      		
		cases = new ArrayList<ArrayList<Case>>();
		
		int pas = 0;
		for(int _ligne = 0;_ligne<Damier.ligne;_ligne++){
			
			ArrayList<Case> l = new ArrayList<>();
			
			for(int _colonne = 0;_colonne < Damier.colonne;_colonne++){
				
				Case c = new Case(_colonne, _ligne);
				
				if(_ligne < 4 && ((_colonne + pas) % 2) == 0){
					

						Piece p = new Piece();
						p.setColor(Color.BLUE);
						c.setPiece(p);
						board.add(new Checker(CheckerType.BLACK_REGULAR), _ligne+1 , _colonne+1);
						l.add(c);
				}
				
				else if(_ligne < Damier.ligne && _ligne >= (Damier.ligne - 4) && ((_colonne + pas) % 2) == 0 ){
						
						Piece p = new Piece();
						p.setColor(Color.RED);
						board.add(new Checker(CheckerType.RED_REGULAR),  _ligne+1 , _colonne+1);
						c.setPiece(p);
						
						l.add(c);
					
				}else{
					
					l.add(c);
				}
				
				
			}
			pas ++;
			cases.add(l);
		}		
		
	      setContentPane(board);
	      pack();
	      setVisible(true);
	}
	
	private void readDamier() {
		
		for(int _ligne=0; _ligne<cases.size(); _ligne++){
			
			System.out.println("ligne numéro : "+ _ligne );
			
			for(int _colonne=0;_colonne<cases.get(_ligne).size(); _colonne++){
				
				if(cases.get(_ligne).get(_colonne).isPieceHere()){
					Piece piece = cases.get(_ligne).get(_colonne).getPiece();
					System.out.print("colonne numéro : "+ _colonne +" contient une piece de couleur "+ piece.getColor()  );
				}else{
					System.out.print("colonne numéro : "+ _colonne +" contient aucune piece pour le moment ");
				}
				System.out.println(" ");
				

				
			}
		}
	}
	
	public ArrayList<ArrayList<Case>> getCases(){
		
		return this.cases;
	}
	
	public static void main(String[] args) {
		
		Damier damier = new Damier();
		
		
		Deplacement d = new Deplacement();
		
		cases = damier.getCases();
		
		ArrayList<Case> caseligne = cases.get(3);
		Case caseInitial = caseligne.get(3);
		d.setCaseInitial(caseInitial);
		
		ArrayList<Case> casecol = cases.get(4);
		Case caseFinal = casecol.get(4);
		d.setCaseFinal(caseFinal);		
		
		Piece p = new Piece();
		p.setColor(Color.RED);
				
		ArrayList<Case> ligneModifier = cases.get(5);
		Case caseModifier = ligneModifier.get(5);
		caseModifier.setPiece(p);
		
		
	}


	/**
	 * @return the nbPieceBlue
	 */
	public int getNbPieceBlue() {
		return nbPieceBlue;
	}


	/**
	 * @param nbPieceBlue the nbPieceBlue to set
	 */
	public void setNbPieceBlue(int nbPieceBlue) {
		this.nbPieceBlue = nbPieceBlue;
	}


	/**
	 * @return the nbPieceRouge
	 */
	public int getNbPieceRouge() {
		return nbPieceRouge;
	}


	/**
	 * @param nbPieceRouge the nbPieceRouge to set
	 */
	public void setNbPieceRouge(int nbPieceRouge) {
		this.nbPieceRouge = nbPieceRouge;
	}
	
	public Board getBoard(){
		
		return this.board;
	}

    
}
