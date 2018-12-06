import jbotsim.Node;
import jbotsim.Topology;
import jbotsimx.ui.CommandListener;
import jbotsimx.ui.JTopology;
import jbotsimx.ui.JViewer;

/**
 * Created by jmassonneau on 29/11/18.
 */
public class Main {
        public static void main(String[] args){
            Topology tp = new Topology();
            tp.setDimensions(1500,400);
            tp.setDefaultNodeModel(CarNode.class);
            for(int i = 0; i < 10; i++)
                tp.addNode(new CarNode());



            JViewer jv = new JViewer(tp);

            JTopology jtp = jv.getJTopology();
            jtp.addCommand("Breakdown");
            jtp.addCommandListener(new CommandListener() {
                @Override
                public void onCommand(String command) {
                    if (command.equals("Breakdown")) {
                        Node node = tp.getNodes().get((int) Math.random() * tp.getNodes().size());
                        ((CarNode) node).setSpeed(0);
                    }
                }
            });

            jv.getJTopology().addBackgroundPainter(new BackgroundPainter());
            tp.start();
    }
}
