public class Building {
    public enum buildingType {
        HOUSE,
        //STORAGE,
        BAKERY,
        FORGE //forge, carpentry etc. in one building
    }

    public void simulationTick() {

        /*
        Sprawdza, czy ilosc domow oraz budynkow specjalnych jest wystarczajaca, 
        jezeli jest, rozmnazanie bedzie mozliwe, jezeli nie, to rozmnazanie bedzie powstrzymane do czasu zaspokojenia wymogu

        */ 
    }
    /*
    public void createStorage(Inventory neededItems){ // NOTE: add to docs

    }
    */

    public void createBuilding(buildingType building, Inventory neededItems){ // NOTE: add to docs

    }

    public void createFood(Inventory neededItems){ // NOTE: add to docs

    }

    public void createWeapon(){ // NOTE: add to docs

    }

    public void createArmor(){ // NOTE: add to docs

    }
}
