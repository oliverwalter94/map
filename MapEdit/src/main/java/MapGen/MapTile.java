package MapGen;

public class MapTile {
    public Tile ground;
    public Biome biome;
    public Tile plant;
    public Tile building;

    public MapTile(Biome b) {
        this.biome = b;
    }

    public MapTile(Biome biome, Tile field, Tile plant, Tile building) {
        this.biome = biome;
        this.ground = field;
        this.plant = plant;
        this.building = building;
    }
}
