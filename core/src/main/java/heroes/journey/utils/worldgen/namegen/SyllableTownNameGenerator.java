package heroes.journey.utils.worldgen.namegen;

import heroes.journey.utils.Random;

public class SyllableTownNameGenerator {
    // Define syllable lists for prefixes, middle, and suffixes
    private static final String[] PREFIXES = {
        "Al", "Ba", "Br", "Ca", "Ch", "Da", "Eld", "Fa", "Gar", "Hal", "Har", "Ith", "Kin", "Lun", "Mor", "Nor", "Or", "Rad", "Ser", "Thal", "Varn", "Wyn"
    };

    private static final String[] MIDDLES = {
        "der", "fell", "shire", "brook", "thor", "mere", "wick", "dale", "ford", "stone", "helm", "holt", "mere", "well", "grave", "glen", "thorn", "wick"
    };

    private static final String[] SUFFIXES = {
        "ton", "ton", "borough", "gate", "hurst", "den", "stead", "holm", "hold", "shire", "cross", "mere", "wood", "wick", "vale"
    };

    private static final Random random = Random.get();

    public static String generateName() {
        // Randomly pick a prefix, middle, and suffix
        String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
        String middle = MIDDLES[random.nextInt(MIDDLES.length)];
        String suffix = SUFFIXES[random.nextInt(SUFFIXES.length)];

        // Combine the parts into a single town name
        return capitalize(prefix + middle + suffix);
    }

    private static String capitalize(String name) {
        // Capitalize the first letter of the town name
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
