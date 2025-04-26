package heroes.journey.utils.worldgen;

import heroes.journey.entities.Position;

import java.util.ArrayList;
import java.util.List;

public class Kingdom {
    public String name;
    public Position capital;
    public List<Position> towns = new ArrayList<>();

    public Kingdom(String name, Position capital) {
        this.name = name;
        this.capital = capital;
    }
}
