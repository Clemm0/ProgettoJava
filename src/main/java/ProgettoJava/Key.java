package ProgettoJava;

public class Key extends Placed {

    public Key(int x, int y) {
        super(x, y);
        this.isMovable = true;
        //image = "src/main/resources/key.png";
        //loadImage();
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void handleCollision(int[][] map, int newX, int newY) {
        switch (map[newX][newY]) {
            case 2:
                map[this.x][this.y] = 0;
                break;
            case 3:
                map[this.x][this.y] = 0;
                map[newX][newY] = 0;
                break;
            default:
                map[this.x][this.y] = 0;
                this.x = newX;
                this.y = newY;
                map[newX][newY] = 4;
                break;
        }
    }
}
