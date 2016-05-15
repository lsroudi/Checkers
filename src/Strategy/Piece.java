package Strategy;
import java.awt.Color;

/**
 * Description of Piece
 *
 * (c) lsroudi  <lsroudi@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
public class Piece {

	private Joueur _joueur;
	private Case _case;
	private Color color;
	
	public Piece(){

	}
	
	public boolean isWinner(){
		
		return true;
	}
	
	public boolean isMove(){
		
		return true;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
