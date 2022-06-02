import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataCollector {
    private final Map map;
    private final FileOutputStream outfile;

    public DataCollector(Map map) throws FileNotFoundException {
        this.map = map;
        outfile = new FileOutputStream("output.csv");
    }

    public void printHeaderLine() throws IOException {
        String headerLine = "";
        headerLine += "tick,";
        for (int teamID = 0; teamID < map.getTeamCount(); teamID++)
            headerLine += "team" + teamID + "Population,";
        headerLine += "\n";
        outfile.write(headerLine.getBytes(StandardCharsets.UTF_8));
    }

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

    public void closeOutputFile() throws IOException {
        outfile.close();
    }
}
