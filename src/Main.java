import jbotsim.Node;
import jbotsim.Topology;
import jbotsimx.ui.CommandListener;
import jbotsimx.ui.JTopology;
import jbotsimx.ui.JViewer;

public class Main {
	enum Case {
		SIMPLE, MANHATTAN;
	}

	public static void main(String[] args) {
		// Cas simple ou manhattan
		Case c = Case.MANHATTAN;

		Topology tp = new Topology();

		if (c == Case.SIMPLE) {
			tp.setDimensions(1500, 400);
			tp.setDefaultNodeModel(CarNodeSimple.class);
			for (int i = 0; i < 10; i++)
				tp.addNode(new CarNodeSimple());
		} else {
			tp.setDimensions(1000, 1000);
			tp.setDefaultNodeModel(CarNodeManhattan.class);
			for (int i = 0; i < 30; i++)
				tp.addNode(new CarNodeManhattan());

		}

		JViewer jv = new JViewer(tp);

		JTopology jtp = jv.getJTopology();
		jtp.addCommand("Breakdown");
		jtp.addCommandListener(new CommandListener() {
			@Override
			public void onCommand(String command) {
				if (command.equals("Breakdown")) {
					Node node = tp.getNodes().get((int) (Math.random() * tp.getNodes().size()));
					if (c == Case.SIMPLE)
						((CarNodeSimple) node).setSpeed(0);
					else
						((CarNodeManhattan) node).setSpeed(0);
				}
			}
		});

		if (c == Case.SIMPLE)
			jv.getJTopology().addBackgroundPainter(new BackgroundPainterSimple());
		else
			jv.getJTopology().addBackgroundPainter(new BackgroundPainterManhattan());

		tp.start();
	}
}
