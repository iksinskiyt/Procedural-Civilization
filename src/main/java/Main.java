import javax.swing.Timer;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static GUI gui;
    private static Map map;
    private static DataCollector dataCollector;

    public static void main(String[] args) {
        gui = new GUI();
        SimulationOptions simulationOptions = gui.getOptionsFromUser();
        map = new Map(simulationOptions);
        gui.openMainWindow(map);

        try {
            dataCollector = new DataCollector(map);
            dataCollector.printHeaderLine();
        } catch (FileNotFoundException e) {
            gui.showMessage(
                    "Unable to open the data output file: " + e.getMessage(),
                    true);
        } catch (IOException e) {
            gui.showMessage(
                    "Unable to write to the output file: " + e.getMessage(),
                    true);
        }

        Timer simulationTimer =
                new Timer(simulationOptions.simulationSpeed, actionEvent -> {
                    try {
                        dataCollector.collectData();
                    } catch (IOException e) {
                        gui.showMessage("Unable to write to the output file: " +
                                e.getMessage(), true);
                    }
                    if (map.isSimulationComplete()) {
                        gui.showMessage("Simulation complete", false);
                        exit();
                    }
                    if (gui.getExitRequested())
                        exit();
                    map.simulationTick();
                    gui.showSimulation();
                });
        simulationTimer.start();
    }

    public static void exit() {
        try {
            dataCollector.closeOutputFile();
        } catch (IOException e) {
            gui.showMessage(
                    "Unable to close the output file: " + e.getMessage(), true);
        }
        gui.closeMainWindow();
        System.exit(0);
    }
}
