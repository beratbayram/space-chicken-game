/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tavuk;

/**
 *
 * @author berat
 */
public class Basket extends Asset {

    int point;

    public Basket(int x, int y) {

        super(x, y, "pics/error.png");
        this.resetBasket();
    }

    public int getPoint() {
        return point;
    }

    public void resetBasket() {
        int max = 5;
        int min = 1;
        point = (int) (Math.random() * ((max - min) + 1)) + min;
        super.changeImg(OsUtils.fixFilePath("pics/basket" + point + ".png"));
    }
}
