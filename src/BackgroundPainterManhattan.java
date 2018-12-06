import jbotsim.Topology;

import java.awt.*;

/**
 * Created by jmassonneau on 06/12/18.
 */
public class BackgroundPainterManhattan implements jbotsimx.ui.painting.BackgroundPainter {
    @Override
    public void paintBackground(Graphics2D g, Topology tp) {
        // Paints a background image
/*        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.getImage(getClass().getResource("route.png"));
        g.drawImage(image, 0, 0, null);*/


        // Draws a grid (line by line)
        g.setColor(Color.gray);
        for(int i=100; i<1000; i+=200){
            g.setStroke(new BasicStroke(5));

            g.drawLine(100, i, 900, i);
            //g.drawLine(0, i - 50, 1500, i - 50);
            //g.drawLine(0, i + 50, 1500, i + 50);

            g.drawLine(i, 100, i, 900);
            //g.drawLine(i - 50, 0, i - 50, 1500);
            //g.drawLine(i + 50, 0, i + 50, 1500);

        }


    }
}
