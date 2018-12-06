import jbotsim.Node;
import jbotsim.Topology;
import jbotsimx.ui.CommandListener;
import jbotsimx.ui.JTopology;
import jbotsimx.ui.JViewer;

public class Main {
        public static void main(String[] args){
            Topology tp = new Topology();

            //tp.setDimensions(1500,400);
            tp.setDimensions(1000,1000);

            //tp.setDefaultNodeModel(CarNodeSimple.class);
            tp.setDefaultNodeModel(CarNodeManhattan.class);

            for(int i = 0; i < 50; i++)
                //tp.addNode(new CarNodeSimple());
                tp.addNode(new CarNodeManhattan());


            JViewer jv = new JViewer(tp);

            JTopology jtp = jv.getJTopology();
            jtp.addCommand("Breakdown");
            jtp.addCommandListener(new CommandListener() {
                @Override
                public void onCommand(String command) {
                    if (command.equals("Breakdown")) {
                        Node node = tp.getNodes().get((int) Math.random() * tp.getNodes().size());
                        ((CarNodeSimple) node).setSpeed(0);
                    }
                }
            });

            //jv.getJTopology().addBackgroundPainter(new BackgroundPainterSimple());
            jv.getJTopology().addBackgroundPainter(new BackgroundPainterManhattan());

            tp.start();
    }
}
