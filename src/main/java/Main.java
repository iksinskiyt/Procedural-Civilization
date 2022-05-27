public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        SimulationOptions simulationOptions = gui.getOptionsFromUser();
        Map map = new Map(simulationOptions);
        gui.openMainWindow(simulationOptions.mapSize, map);
        while (!map.isSimulationComplete()) {
            map.simulationTick();
            gui.showSimulation();
        }
    }
}
