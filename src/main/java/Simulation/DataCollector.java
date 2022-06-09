package Simulation;

import Creatures.Human;
import Terrain.Map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Collect simulation data and save it to a CSV-formatted file
 */
public class DataCollector {
    /**
     * Map to collect data from
     */
    private final Map map;

    /**
     * File to output data to
     */
    private final FileOutputStream outfile;

    /**
     * Construct a new data collector
     *
     * @param map A map to collect data from
     * @throws FileNotFoundException When the output file's parent directory
     *                               does not exist
     */
    public DataCollector(Map map) throws FileNotFoundException {
        this.map = map;
        outfile = new FileOutputStream("output.csv");
    }

    /**
     * Output a header line
     *
     * @throws IOException When the data cannot be written
     */
    public void printHeaderLine() throws IOException {
        String headerLine = "";
        headerLine += "tick,";
        for (int teamID = 0; teamID < map.getTeamCount(); teamID++)
            headerLine += "team" + teamID + "Population,";
        headerLine += "\n";
        outfile.write(headerLine.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Collect data from the map and output it as a single line to the output
     * file
     *
     * @throws IOException When the data cannot be written
     */
    public void collectData() throws IOException {
        String dataLine = "";
        dataLine += map.getTickCounter() + ",";
        List<Human> humans = new ArrayList<>();
        for (Village village : map.getVillageList())
            humans.addAll(village.getVillagers());

        for (int teamID = 0; teamID < map.getTeamCount(); teamID++) {
            int teamPopulation = 0;
            for (Human human : humans) {
                if (human.getTeamID() == teamID)
                    teamPopulation++;
            }
            dataLine += teamPopulation + ",";
        }

        dataLine += "\n";
        outfile.write(dataLine.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Close the output file
     *
     * @throws IOException When the output file cannot be closed
     */
    public void closeOutputFile() throws IOException {
        outfile.close();
    }
}
