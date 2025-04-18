package heroes.journey.entities.tagging;

import java.util.HashMap;

public class ConversionSet extends HashMap<Tag,Tag> {

    public ConversionSet add(Tag tagToConvert, Tag convertedTag) {
        this.put(tagToConvert, convertedTag);
        return this;
    }

}
