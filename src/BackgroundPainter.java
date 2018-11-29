import jbotsim.Topology;

import java.awt.*;

public class BackgroundPainter
        implements jbotsimx.ui.painting.BackgroundPainter {
    @Override
    public void paintBackground(Graphics2D g, Topology tp) {
        // Paints a background image
/*        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.getImage(getClass().getResource("route.png"));
        g.drawImage(image, 0, 0, null);*/


        // Draws a grid (line by line)
        g.setColor(Color.gray);
        g.drawLine(0, 125, 1500, 125);
        g.drawLine(0, 75, 1500, 75);
        g.drawLine(0, 175, 1500, 175);



    }
}