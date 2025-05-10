package ProgettoJava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public final class Player {
    ArrayList<Object> moves;
    int x, y;
    char direction;
    char crn = '0';

    Player(int x, int y) {
        this.x = x;
        this.y = y;
        moves = new ArrayList<>();
        addMoves(x, y, 'W');
        //image = "src/main/resources/player.png";
        //loadImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getDirection() {
        return direction;
    }

    public void addMoves(int x, int y, char direction) {
        moves.add(x);
        moves.add(y);
        moves.add(direction);
    }

    public void removeMoves() {
        if (moves.size() >= 3) {
            moves.remove(moves.size() - 1);
            moves.remove(moves.size() - 1);
            moves.remove(moves.size() - 1);
        }
    }

    public void undoMove() {
        if (moves.size() >= 3) {
            this.x = (int) moves.get(moves.size() - 3);
            this.y = (int) moves.get(moves.size() - 2);
            this.direction = (char) moves.get(moves.size() - 1);
            setX(x);
            setY(y);
            removeMoves();
        }
    }

    public void move(char direction) {
        this.direction = direction;
        direction = Character.toUpperCase(direction);
        switch (direction) {
            case 'W' -> setY(getY() - 1);
            case 'A' -> setX(getX() - 1);
            case 'S' -> setY(getY() + 1);
            case 'D' -> setX(getX() + 1);
            default -> {
                System.out.println("Invalid direction");
                return;
            }
        }
        addMoves(x, y, direction);
    }

    public void updateMapFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                System.out.println("Map file does not exist.");
                return;
            }

            StringBuilder updatedContent = new StringBuilder();
            for (String line : Files.readAllLines(path)) {
                updatedContent.append(line).append(System.lineSeparator());
            }

            String[] lines = updatedContent.toString().split(System.lineSeparator());
            if (y >= 0 && y < lines.length && x >= 0 && x < lines[y].length()) {
                char[] row = lines[y].toCharArray();
                row[x] = crn;
                lines[y] = new String(row);

                crn = row[x];
                row[x] = 'P';
                lines[y] = new String(row);
            }

            Files.write(path, String.join(System.lineSeparator(), lines).getBytes());
            System.out.println("Map file updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while updating the map file: " + e.getMessage());
        }
    }

    public void moveAndUpdateMap(char direction, String filePath) {
        move(direction);
        updateMapFile(filePath);
    }
    
    public boolean canMoveTo(int[][] map, int newX, int newY) {
        return map[newX][newY] == 0; // Example: Check if the target cell is walkable
    }

    public void interactWithObject(Placed object, int[][] map) {
        if (object instanceof Movable movable) {
            int newX = x, newY = y;
            switch (direction) {
                case 'W' -> newY -= 1;
                case 'A' -> newX -= 1;
                case 'S' -> newY += 1;
                case 'D' -> newX += 1;
                default -> {
                    System.out.println("Invalid direction for moving.");
                    return;
                }
            }

            if (canMoveTo(map, newX, newY)) {
                movable.setPosition(newX, newY);
                System.out.println("Moved the object!");
            } else {
                System.out.println("Cannot move the object to the desired location.");
            }
        } else {
            System.out.println("This object cannot be moved.");
        }
    }
}
