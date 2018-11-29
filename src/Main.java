import jbotsim.Topology;
import jbotsimx.ui.JViewer;

/**
 * Created by jmassonneau on 29/11/18.
 */
public class Main {
        public static void main(String[] args){
        Topology tp = new Topology();
        tp.setDefaultNodeModel(CarNode.class);
        for(int i = 0; i < 10; i++)
            tp.addNode(new CarNode());
        JViewer jv = new JViewer(tp);
        jv.getJTopology().addBackgroundPainter(new BackgroundPainter());
        tp.start();
    }
}
