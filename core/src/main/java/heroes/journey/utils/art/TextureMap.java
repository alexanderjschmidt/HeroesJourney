package heroes.journey.utils.art;

public record TextureMap(String location, int width, int height) {

    public TextureMap(String location, int width, int height) {
        this.location = location.startsWith("Textures/") ? location : "Textures/" + location;
        this.width = width;
        this.height = height;
        ResourceManager.get().textureRegions.put(this, null);
    }

}
