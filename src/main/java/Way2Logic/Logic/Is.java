package Way2Logic.Logic;

public class Is extends Block {
    private String[][] matrix;
    private String[] blc = new String[3];

    public Is() {
        super("Is");
        matrix = new String[][] { { "", "", "" }, { "", "Is", "" }, { "", "", "" } };
        image = setImage(name);
    }

    public void updateMatrix(String[][] map, int x, int y) {
        matrix[0][1] = getTouchedBlockName(map, x, y, "up");
        matrix[2][1] = getTouchedBlockName(map, x, y, "down");
        matrix[1][0] = getTouchedBlockName(map, x, y, "left");
        matrix[1][2] = getTouchedBlockName(map, x, y, "right");
    }

    public String getTouchedBlockName(String[][] map, int x, int y, String direction) {
        int newX = x, newY = y;
        switch (direction.toLowerCase()) {
            case "up" -> newY = y - 1;
            case "down" -> newY = y + 1;
            case "left" -> newX = x - 1;
            case "right" -> newX = x + 1;
            default -> {
                return "";
            }
        }
        if (newY < 0 || newY >= map.length || newX < 0 || newX >= map[0].length) {
            return "";
        }
        String blockName = map[newY][newX];
        return (blockName == null || blockName.isEmpty()) ? "" : blockName;
    }

    public void updateLoc(String[][] map, int x, int y) {
        updateMatrix(map, x, y);
    }

    public void ChangeProperties() {
        System.arraycopy(matrix[1], 0, blc, 0, 3);
        switch (blc[0]) {
            case "Wall" -> {
                switch (blc[2]) {
                    case "stop" -> Wall.collision = true;
                    case "push" -> Wall.push = true;
                    case "win" -> Wall.win = true;
                    case "you" -> Wall.you = true;
                    case "sink" -> Wall.sink = true;
                    case "lock" -> Wall.lock = true;
                    case "ulock" -> Wall.ulock = true;
                }
            }
            case "Name" -> {
                switch (blc[2]) {
                    case "stop" -> Name.collision = true;
                    case "push" -> Name.push = true;
                    case "win" -> Name.win = true;
                    case "you" -> Name.you = true;
                    case "sink" -> Name.sink = true;
                    case "lock" -> Name.lock = true;
                    case "ulock" -> Name.ulock = true;
                }
            }
            case "Door" -> {
                switch (blc[2]) {
                    case "stop" -> Door.collision = true;
                    case "push" -> Door.push = true;
                    case "win" -> Door.win = true;
                    case "you" -> Door.you = true;
                    case "sink" -> Door.sink = true;
                    case "lock" -> Door.lock = true;
                    case "ulock" -> Door.ulock = true;
                }
            }
            case "Flag" -> {
                switch (blc[2]) {
                    case "stop" -> Flag.collision = true;
                    case "push" -> Flag.push = true;
                    case "win" -> Flag.win = true;
                    case "you" -> Flag.you = true;
                    case "sink" -> Flag.sink = true;
                    case "lock" -> Flag.lock = true;
                    case "ulock" -> Flag.ulock = true;
                }
            }
            case "Water" -> {
                switch (blc[2]) {
                    case "stop" -> Water.collision = true;
                    case "push" -> Water.push = true;
                    case "win" -> Water.win = true;
                    case "you" -> Water.you = true;
                    case "sink" -> Water.sink = true;
                    case "lock" -> Water.lock = true;
                    case "ulock" -> Water.ulock = true;
                }
            }
            case "Key" -> {
                switch (blc[2]) {
                    case "stop" -> Key.collision = true;
                    case "push" -> Key.push = true;
                    case "win" -> Key.win = true;
                    case "you" -> Key.you = true;
                    case "sink" -> Key.sink = true;
                    case "lock" -> Key.lock = true;
                    case "ulock" -> Key.ulock = true;
                }
            }
        }
        blc[0] = matrix[0][1];
        blc[1] = matrix[1][1];
        blc[2] = matrix[2][1];
        switch (blc[0]) {
            case "Wall" -> {
                switch (blc[2]) {
                    case "stop" -> Wall.collision = true;
                    case "push" -> Wall.push = true;
                    case "win" -> Wall.win = true;
                    case "you" -> Wall.you = true;
                    case "sink" -> Wall.sink = true;
                    case "lock" -> Wall.lock = true;
                    case "ulock" -> Wall.ulock = true;
                }
            }
            case "Name" -> {
                switch (blc[2]) {
                    case "stop" -> Name.collision = true;
                    case "push" -> Name.push = true;
                    case "win" -> Name.win = true;
                    case "you" -> Name.you = true;
                    case "sink" -> Name.sink = true;
                    case "lock" -> Name.lock = true;
                    case "ulock" -> Name.ulock = true;
                }
            }
            case "Door" -> {
                switch (blc[2]) {
                    case "stop" -> Door.collision = true;
                    case "push" -> Door.push = true;
                    case "win" -> Door.win = true;
                    case "you" -> Door.you = true;
                    case "sink" -> Door.sink = true;
                    case "lock" -> Door.lock = true;
                    case "ulock" -> Door.ulock = true;
                }
            }
            case "Flag" -> {
                switch (blc[2]) {
                    case "stop" -> Flag.collision = true;
                    case "push" -> Flag.push = true;
                    case "win" -> Flag.win = true;
                    case "you" -> Flag.you = true;
                    case "sink" -> Flag.sink = true;
                    case "lock" -> Flag.lock = true;
                    case "ulock" -> Flag.ulock = true;
                }
            }
            case "Water" -> {
                switch (blc[2]) {
                    case "stop" -> Water.collision = true;
                    case "push" -> Water.push = true;
                    case "win" -> Water.win = true;
                    case "you" -> Water.you = true;
                    case "sink" -> Water.sink = true;
                    case "lock" -> Water.lock = true;
                    case "ulock" -> Water.ulock = true;
                }
            }
            case "Key" -> {
                switch (blc[2]) {
                    case "stop" -> Key.collision = true;
                    case "push" -> Key.push = true;
                    case "win" -> Key.win = true;
                    case "you" -> Key.you = true;
                    case "sink" -> Key.sink = true;
                    case "lock" -> Key.lock = true;
                    case "ulock" -> Key.ulock = true;
                }
            }
        }
    }
}
