import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class Main {
    private final static Semaphore simulationSemaphore = new Semaphore(0);

    static class SimulationTimerTask extends TimerTask {
        @Override
        public void run() {
            simulationSemaphore.release();
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        SimulationOptions simulationOptions = gui.getOptionsFromUser();
        Map map = new Map(simulationOptions);
        gui.openMainWindow(simulationOptions.mapSize, map);
        Timer simulationTimer = new Timer();
        simulationTimer.schedule(new SimulationTimerTask(), 0, 100);
        int c = 0;
        while (!map.isSimulationComplete()) {
            map.simulationTick();
            gui.showSimulation();
            System.out.println(c++);
            try {
                simulationSemaphore.acquire();
            } catch (InterruptedException e) {
                System.out.println(
                        "Failed to acquire the semaphore: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
