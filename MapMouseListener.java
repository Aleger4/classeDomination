package net.yura.domination.engine.guishared;

import net.yura.domination.engine.Risk;
import net.yura.domination.engine.core.RiskGame;

/**
 * @author Yura Mamyrin
 */
public class MapMouseListener {
	/**
	 * this is a Costant NO COUNTRY
	 */
    public final static int NO_COUNTRY = 255;

    private Risk myrisk;
    private MapPanel pp;

    public MapMouseListener(Risk risk,MapPanel pp) {
        this.pp = pp;
        myrisk = risk;
    }

    public int[] mouseReleased(int x, int y,int gameState) {

        int pixColor = pp.getCountryNumber(x,y);

        if (pixColor == NO_COUNTRY ) {

        }
        else if (gameState == RiskGame.STATE_PLACE_ARMIES) {
                if ( myrisk.isOwnedCurrentPlayerInt(pixColor) && (myrisk.getGame().NoEmptyCountries() || myrisk.hasArmiesInt(pixColor) == 0) ) {
                        return new int[] {pixColor};
                }
        }
        else if (gameState == RiskGame.STATE_ATTACKING) {

                while( pixColor == pp.getC1() ) {
                        pp.setC1(NO_COUNTRY);
                        pp.setC2(NO_COUNTRY);
                        pp.repaint();
                        return new int[0];
                }
                while( myrisk.isOwnedCurrentPlayerInt(pixColor) && ( myrisk.hasArmiesInt(pixColor) > 1) ) {
                        pp.setC1(pixColor);
                        pp.setC2(NO_COUNTRY);
                        pp.repaint();
                        return new int[] {pixColor};
                }
                while( pp.getC1() != NO_COUNTRY && !(myrisk.isOwnedCurrentPlayerInt(pixColor)) && myrisk.canAttack( pp.getC1() , pixColor) ) {
                        pp.setC2(pixColor);
                        pp.repaint();
                        return new int[] {pp.getC1(),pixColor};
                }

        }
        else if (gameState == RiskGame.STATE_FORTIFYING) {

                while ( pp.getC1()!=NO_COUNTRY && pp.getC2()==NO_COUNTRY && pixColor == pp.getC1() ) {
                        pp.setC1(NO_COUNTRY);
                        pp.setC2(NO_COUNTRY);
                        pp.repaint();
                        return new int[0];
                }
                while( myrisk.isOwnedCurrentPlayerInt(pixColor) && ( myrisk.hasArmiesInt(pixColor) > 1) && pp.getC1() == NO_COUNTRY) {
                        pp.setC1(pixColor);
                        pp.setC2(NO_COUNTRY);
                        pp.repaint();
                        return new int[] { pixColor };
                }
                while ( pp.getC1() != NO_COUNTRY && myrisk.isOwnedCurrentPlayerInt(pixColor) && pp.getC2() != pixColor && myrisk.canAttack( pp.getC1() , pixColor) ) {
                        pp.setC2(pixColor);
                        pp.repaint();
                        return new int[] { pp.getC1(),pixColor };
                }
                while( myrisk.isOwnedCurrentPlayerInt(pixColor) && pp.getC1()!=NO_COUNTRY && pp.getC2()!=NO_COUNTRY  && ( myrisk.hasArmiesInt(pixColor) > 1) ) {
                        pp.setC1( pixColor );
                        pp.setC2(NO_COUNTRY);
                        pp.repaint();
                        return new int[] { pixColor };
                }

        }
        else if (gameState == RiskGame.STATE_SELECT_CAPITAL) {

                while( myrisk.isOwnedCurrentPlayerInt(pixColor) ) {
                        pp.setC1(pixColor);
                        pp.repaint();
                        return new int[] { pixColor };
                }
        }
        return null;
    }

    public void mouseExited() {
        if (pp.getHighLight() != NO_COUNTRY) {
                pp.setHighLight(NO_COUNTRY);
                pp.repaint();
        }
    }

    public void mouseMoved(int x,int y,int gameState) {

        int pixColor = pp.getCountryNumber(x,y);
        int cc = NO_COUNTRY;

        if (pixColor == NO_COUNTRY ) {

        }
        switch(RiskGame.STATE_PLACE_ARMIES == gameState) {
                case 1:( myrisk.isOwnedCurrentPlayerInt(pixColor) && (myrisk.getGame().NoEmptyCountries() || myrisk.hasArmiesInt(pixColor) == 0) ) ;
                        cc = pixColor;
                }
        
        switch(RiskGame.STATE_ATTACKING == gameState){
                case 1:( myrisk.isOwnedCurrentPlayerInt(pixColor) && (myrisk.hasArmiesInt(pixColor) > 1) ) {
                        cc = pixColor;
                        break;
                }
                case 2:( !(myrisk.isOwnedCurrentPlayerInt(pixColor)) && pp.getC1() != NO_COUNTRY && myrisk.canAttack( pp.getC1() , pixColor) ) {
                        cc = pixColor;
                        break;
                }
        }
        switch( RiskGame.STATE_FORTIFYING == gameState) {
            case 1:( myrisk.isOwnedCurrentPlayerInt(pixColor) && (myrisk.hasArmiesInt(pixColor) > 1 && pp.getC1() == NO_COUNTRY ) ) {
                        cc = pixColor;
                        break;
                }
            case 2:( myrisk.isOwnedCurrentPlayerInt(pixColor) && pp.getC1() != NO_COUNTRY && myrisk.canAttack( pp.getC1() , pixColor) ) {
                        cc = pixColor;
                        break;
                }
            case 3:( myrisk.isOwnedCurrentPlayerInt(pixColor) && pp.getC1() != NO_COUNTRY && pp.getC2() != NO_COUNTRY && (myrisk.hasArmiesInt(pixColor) > 1) ) {
			cc = pixColor;
			break;
		}
        }
        switch (RiskGame.STATE_SELECT_CAPITAL == gameState) {
            case 1:( myrisk.isOwnedCurrentPlayerInt(pixColor) ) {
                        cc = pixColor;
                        break;
                }
        }

        if (pp.getHighLight() != cc) {
                pp.setHighLight(cc);
                pp.repaint();
        }

    }

}
