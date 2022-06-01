import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static GUI gui;
    private static Map map;

    public static void main(String[] args) {
        gui = new GUI();
        SimulationOptions simulationOptions = gui.getOptionsFromUser();
        map = new Map(simulationOptions);
        gui.openMainWindow(map);
        Timer simulationTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                map.simulationTick();
                gui.showSimulation();
                if(map.isSimulationComplete()) {
                    gui.showMessage("Simulation complete");
                    System.exit(0);
                }
                if(gui.getExitRequested()) {
                    System.exit(0);
                }
            }
        });
        simulationTimer.start();
    }
}
