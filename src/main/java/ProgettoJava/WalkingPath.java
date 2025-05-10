package ProgettoJava;

public final class WalkingPath extends Placed {

    public WalkingPath(int x, int y) {
        super(x, y, false);
        this.isWalkable = true;
        setImage(randomizeImage());
    }

    private String randomizeImage() {
        int rand = (int) (Math.random() * 4);
        return switch (rand) {
            case 0 -> "src/main/resources/grass.png";
            case 1 -> "src/main/resources/stone.png";
            case 2 -> "src/main/resources/sand.png";
            case 3 -> "src/main/resources/dirt.png";
            default -> "src/main/resources/grass.png";
        };
    }
}