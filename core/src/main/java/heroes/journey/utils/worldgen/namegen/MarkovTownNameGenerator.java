package heroes.journey.utils.worldgen.namegen;

import heroes.journey.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkovTownNameGenerator {
    public static final String[] TOWN_NAMES = {
        "Aldermere", "Amberfall", "Ashbourne", "Ashford", "Balewick",
        "Barrowmere", "Blackpool", "Blackthorn", "Bramblewood", "Bradford",
        "Brighthollow", "Brookmere", "Caerwyn", "Cambridge", "Canterbury",
        "Carlisle", "Chesterford", "Coldsborough", "Coldspring", "Cumberland",
        "Darkmere", "Doverwick", "Dunwich", "Durham", "Easthaven", "Eldermere",
        "Eldenford", "Eldenwold", "Falkirk", "Fallowmere", "Farndale",
        "Frostwick", "Gainsborough", "Glenhaven", "Gloucester", "Goldshire",
        "Grimsby", "Harrowgate", "Harthford", "Haverford", "Hawthorne",
        "Hearthglen", "Helmsford", "Hollowford", "Hollowmere", "Ironhaven",
        "Ironridge", "Kingsbridge", "Kirkstead", "Lancaster", "Lindleigh",
        "Lockmere", "Loxley", "Marbury", "Middleshire", "Millstone",
        "Mourndale", "Newbrook", "Norbury", "Norwich", "Northwick",
        "Nottingham", "Oakhollow", "Oakenshire", "Oxford", "Pendleford",
        "Ravenmere", "Ravenshire", "Redbrook", "Redwater", "Ridgewell",
        "Rochdale", "Rosewick", "Rotherhithe", "Salisbury", "Saltmarsh",
        "Shadowfen", "Shadewood", "Silvershire", "Silvermere", "Southmere",
        "Southwark", "Stonebridge", "Stonehaven", "Stormbrook", "Stormhold",
        "Stratford", "Sundermere", "Thornbury", "Thornfield", "Thundertree",
        "Warwick", "Westmarch", "Westwood", "Whitby", "Whisperwind",
        "Whiteharbor", "Winchester", "Windmere", "Wolfden", "Wynchester",
        "York"
    };

    private static final int ORDER = 2; // Markov chain order
    private final Map<String, List<Character>> markovChain = new HashMap<>();
    private final Random random;
    private int minimumLength = Integer.MAX_VALUE, maximumLength = Integer.MIN_VALUE;

    private static MarkovTownNameGenerator markovTownNameGenerator;

    public static MarkovTownNameGenerator get() {
        if (markovTownNameGenerator == null)
            markovTownNameGenerator = new MarkovTownNameGenerator();
        return markovTownNameGenerator;
    }

    private MarkovTownNameGenerator() {
        random = Random.get();
        buildMarkovChain();
    }

    private void buildMarkovChain() {
        for (String name : TOWN_NAMES) {
            if (minimumLength > name.length())
                minimumLength = name.length();
            if (maximumLength < name.length())
                maximumLength = name.length();
            String lowerName = name.toLowerCase();
            for (int i = 0; i < lowerName.length() - ORDER; i++) {
                String key = lowerName.substring(i, i + ORDER);
                char nextChar = lowerName.charAt(i + ORDER);

                markovChain.computeIfAbsent(key, k -> new ArrayList<>()).add(nextChar);
            }
        }
    }

    public String generateTownName() {
        int randomLength = random.nextInt(maximumLength - minimumLength + 1) + minimumLength;
        return generateTownName(randomLength);
    }

    public String generateTownName(int length) {
        List<String> keys = new ArrayList<>(markovChain.keySet());
        String key = keys.get(random.nextInt(keys.size())); // Start with a random key
        StringBuilder result = new StringBuilder(key);

        while (result.length() < length) {
            List<Character> nextChars = markovChain.get(result.substring(result.length() - ORDER));
            if (nextChars == null || nextChars.isEmpty()) break;
            result.append(nextChars.get(random.nextInt(nextChars.size())));
        }

        return capitalize(result.toString());
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
