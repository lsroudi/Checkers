package Strategy;
/**
 * Description of Dammier
 *
 * (c) lsroudi  <lsroudi@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
public class Case {
	
	private int _colonne;
	private int _ligne;
	private Piece piece = null;
	
	public Case(int colonne,int ligne){
		this.setColonne(colonne);
		this.setLigne(ligne);
	}
	
	public Piece getPiece(){
		
		return this.piece;
	}
	
	public void setPiece(Piece piece){
		
		this.piece = piece;
		
	}	
	
	public boolean isPieceHere(){
		
		return this.piece != null;
	}

	/**
	 * @return the _colonne
	 */
	public int getColonne() {
		return _colonne;
	}

	/**
	 * @param _colonne the _colonne to set
	 */
	public void setColonne(int _colonne) {
		this._colonne = _colonne;
	}

	/**
	 * @return the _ligne
	 */
	public int getLigne() {
		return _ligne;
	}

	/**
	 * @param _ligne the _ligne to set
	 */
	public void setLigne(int _ligne) {
		this._ligne = _ligne;
	}
	

}
