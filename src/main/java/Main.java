public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.getOptionsFromUser();
        gui.startSimulation();
        while(!gui.isSimulationComplete())
        {
            gui.simulationTick();
            gui.showSimulation();
        }
    }
}
