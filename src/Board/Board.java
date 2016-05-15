package Board;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import Strategy.Case;
import Strategy.Damier;
import Strategy.Deplacement;
import Strategy.Piece;

public class Board extends JComponent
{
   // dimension of checkerboard square (25% bigger than checker)

   private final static int SQUAREDIM = (int) (Checker.getDimension() * 1.25);

   // dimension of checkerboard (width of 8 squares)

   private final int BOARDDIM = 10 * SQUAREDIM;

   // preferred size of Board component

   private Dimension dimPrefSize;

   // dragging flag -- set to true when user presses mouse button over checker
   // and cleared to false when user releases mouse button

   private boolean inDrag = false;

   // displacement between drag start coordinates and checker center coordinates

   private int deltax, deltay;

   // reference to positioned checker at start of drag

   private PosCheck posCheck;

   // center location of checker at start of drag

   private int oldcx, oldcy;

   // list of Checker objects and their initial positions

   private List<PosCheck> posChecks;
   
   private int oldLigne;
   
   private int oldcolonne; 
   
   private int Ligne;
   
   private int colonne;

   private Damier damier;
   Color color;
   private ArrayList<ArrayList<Case>> casesClone = null;
   private Case caseInitial;
   private Case caseFinal;

   public Board(Damier d)
   {
      posChecks = new ArrayList<>();
      dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);
      damier = d;
      Deplacement dep = new Deplacement();

      addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mousePressed(MouseEvent me)
                          {
                             // Obtain mouse coordinates at time of press.

                             int x = me.getX();
                             int y = me.getY();

                             // Locate positioned checker under mouse press.

                             for (PosCheck posCheck: posChecks)
                                if (Checker.contains(x, y, posCheck.cx, 
                                                     posCheck.cy))
                                {
                                   Board.this.posCheck = posCheck;
                                   oldcx = posCheck.cx;
                                   oldcy = posCheck.cy;
                                   deltax = x - posCheck.cx;
                                   deltay = y - posCheck.cy;
                                   inDrag = true;
                                   setOldLigne(getLigne());
                                   setOldcolonne(getColonne());
  								 if(casesClone == null){
									 casesClone = (ArrayList<ArrayList<Case>>) damier.getCases().clone();
								 }
  								 
  								 ArrayList<Case> caseligne = casesClone.get(oldLigne);
  								 caseInitial = caseligne.get(oldcolonne);
  								 dep.setCaseInitial(caseInitial);
  								 
                                   return;
                                }
                          }

                          @Override
                          public void mouseReleased(MouseEvent me)
                          {
                             // When mouse released, clear inDrag (to
                             // indicate no drag in progress) if inDrag is
                             // already set.

                             if (inDrag)
                                inDrag = false;
                             else
                                return;

                             // Snap checker to center of square.

                             int x = me.getX();
                             int y = me.getY();
                             posCheck.cx = (x - deltax) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;
                             posCheck.cy = (y - deltay) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;

                             // Do not move checker onto an occupied square.

                             for (PosCheck posCheck: posChecks)
                                if (posCheck != Board.this.posCheck && 
                                    posCheck.cx == Board.this.posCheck.cx &&
                                    posCheck.cy == Board.this.posCheck.cy)
                                {
                                   Board.this.posCheck.cx = oldcx;
                                   Board.this.posCheck.cy = oldcy;
                                }
                             
                             setLigne(getLigne());
							 setColonne(getColonne());
                             
                             posCheck = null;
                             
                             repaint();

							 ArrayList<Case> casecol = damier.getCases().get(Ligne);
							 caseFinal = casecol.get(colonne);
							 dep.setCaseFinal(caseFinal);	                        

							 
                             System.out.println("ligne : "+caseInitial.getLigne()+ " colonne : "+caseInitial.getColonne());

							 Case ca = dep.jouerCpu(damier, Color.BLUE);
							 
							 System.out.println("ligne : "+ca.getLigne()+ " colonne : "+ca.getColonne());
							 add(new Checker(CheckerType.BLACK_REGULAR), ca.getLigne() , ca.getColonne());
							 
							 casesClone = null;
                          }
                       });

      // Attach a mouse motion listener to the applet. That listener listens
      // for mouse drag events.

      addMouseMotionListener(new MouseMotionAdapter()
                             {
                                @Override
                                public void mouseDragged(MouseEvent me)
                                {
                                   if (inDrag)
                                   {
                                      // Update location of checker center.

                                      posCheck.cx = me.getX() - deltax;
                                      posCheck.cy = me.getY() - deltay;
                                      repaint();
                                   }
                                }
                             });

   }

   public void add(Checker checker, int row, int col)
   {
      if (row < 1 || row > 10)
         throw new IllegalArgumentException("row out of range: " + row);
      if (col < 1 || col > 10)
         throw new IllegalArgumentException("col out of range: " + col);
      PosCheck posCheck = new PosCheck();
      posCheck.checker = checker;
      posCheck.cx = (col - 1) * SQUAREDIM + SQUAREDIM / 2;
      posCheck.cy = (row - 1) * SQUAREDIM + SQUAREDIM / 2;
      for (PosCheck _posCheck: posChecks)
         if (posCheck.cx == _posCheck.cx && posCheck.cy == _posCheck.cy)
            throw new AlreadyOccupiedException("square at (" + row + "," +
                                               col + ") is occupied");
      posChecks.add(posCheck);
   }

   @Override
   public Dimension getPreferredSize()
   {
      return dimPrefSize;
   }

   @Override
   protected void paintComponent(Graphics g)
   {
      paintCheckerBoard(g);
      for (PosCheck posCheck: posChecks)
         if (posCheck != Board.this.posCheck)
            posCheck.checker.draw(g, posCheck.cx, posCheck.cy);

      // Draw dragged checker last so that it appears over any underlying 
      // checker.

      if (posCheck != null)
         posCheck.checker.draw(g, posCheck.cx, posCheck.cy);
   }

   private void paintCheckerBoard(Graphics g)
   {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

      // Paint checkerboard.

      for (int row = 0; row < 10; row++)
      {
         g.setColor(((row & 1) != 0) ? Color.BLACK : Color.WHITE);
         for (int col = 0; col < 10; col++)
         {
            g.fillRect(col * SQUAREDIM, row * SQUAREDIM, SQUAREDIM, SQUAREDIM);
            g.setColor((g.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
         }
      }
   }

   // positioned checker helper class

   private class PosCheck
   {
      public Checker checker;
      public int cx;
      public int cy;
   }
   
	public int getLigne()
	{
		return ((posCheck.cy - SQUAREDIM / 2)/SQUAREDIM)  ;
	}
	
	public int getColonne()
	{
		return ((posCheck.cx - SQUAREDIM / 2)/SQUAREDIM)  ;
	}

	/**
	 * @return the oldLigne
	 */
	public int getOldLigne() {
		return oldLigne;
	}

	/**
	 * @param oldLigne the oldLigne to set
	 */
	public void setOldLigne(int oldLigne) {
		this.oldLigne = oldLigne;
	}

	/**
	 * @return the oldcolonne
	 */
	public int getOldcolonne() {
		return oldcolonne;
	}

	/**
	 * @param oldcolonne the oldcolonne to set
	 */
	public void setOldcolonne(int oldcolonne) {
		this.oldcolonne = oldcolonne;
	}

	/**
	 * @param ligne the ligne to set
	 */
	public void setLigne(int ligne) {
		Ligne = ligne;
	}

	/**
	 * @param colonne the colonne to set
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public void setDammier(Damier damier) {
		this.damier = damier;
		
	}
}