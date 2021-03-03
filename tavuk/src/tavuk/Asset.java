/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tavuk;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author berat
 */
public class Asset extends Coordinate {

    private BufferedImage img;

    public Asset(int x, int y, String imgLocation) {
        super(x, y);

        OsUtils.fixFilePath(imgLocation);

        try {
            img = ImageIO.read(new File(imgLocation));
        } catch (IOException ex) {
            Logger.getLogger(Asset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getImage() {
        return img;
    }

    public BufferedImage changeImg(String imgLocation) {

        OsUtils.fixFilePath(imgLocation);

        try {
            img = ImageIO.read(new File(imgLocation));
        } catch (IOException ex) {
            Logger.getLogger(Asset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

}
