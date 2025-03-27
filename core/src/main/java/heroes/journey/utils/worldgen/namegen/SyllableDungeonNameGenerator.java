package heroes.journey.utils.worldgen.namegen;

import heroes.journey.utils.Random;

public class SyllableDungeonNameGenerator {
    // Define syllable lists for prefixes, middle, and suffixes
    private static final String[] PREFIXES = {
        "Ancient", "Dark", "Blood", "Eternal", "Cursed", "Frost", "Shadow", "Iron", "Black", "Stone", "Crystal", "Hell", "Fire", "Dread", "Twilight", "Vile", "Mourn"
    };

    private static final String[] MIDDLES = {
        "spire", "crypt", "hold", "pit", "cavern", "abyss", "depths", "forge", "warren", "catacombs", "halls", "sanctum", "temple", "realm", "shrine", "chamber", "ruin"
    };

    private static final String[] SUFFIXES = {
        "of Doom", "of Shadows", "of the Damned", "of Fear", "of Wrath", "of Souls", "of the Forgotten", "of Darkness", "of the Fallen", "of Torment", "of Despair", "of the Undying", "of Chaos"
    };

    private static final Random random = Random.get();

    public static String generateName() {
        // Randomly pick a prefix, middle, and suffix
        String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
        String middle = MIDDLES[random.nextInt(MIDDLES.length)];
        String suffix = SUFFIXES[random.nextInt(SUFFIXES.length)];

        // Combine the parts into a single town name
        return capitalize(prefix + " " + middle + " " + suffix);
    }

    private static String capitalize(String name) {
        // Capitalize the first letter of the town name
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
