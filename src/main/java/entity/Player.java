package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;
import object.SuperObject;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public static int level = 1;
    public int standCounter = 0;
    boolean moving = false;

    public final int screenX;
    public final int screenY;

    private int targetX, targetY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        int offset = gp.tileSize / 8;
        solidArea = new Rectangle(offset, offset, gp.tileSize - 2 * offset, gp.tileSize - 2 * offset);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();

        targetX = worldX;
        targetY = worldY;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 7;
        worldY = gp.tileSize * 7;
        speed = 4;
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            leftStill = ImageIO.read(getClass().getResourceAsStream("/res/player/cat/CatStill.png"));
            // ...other image loading...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (!moving) {
            int nextX = worldX;
            int nextY = worldY;

            if (keyH.upPressed) {
                direction = "up";
                nextY -= gp.tileSize;
                gp.playSE(3);
            } else if (keyH.downPressed) {
                direction = "down";
                nextY += gp.tileSize;
                gp.playSE(3);
            } else if (keyH.leftPressed) {
                direction = "left";
                nextX -= gp.tileSize;
                gp.playSE(3);
            } else if (keyH.rightPressed) {
                direction = "right";
                nextX += gp.tileSize;
                gp.playSE(3);
            }

            if (nextX != worldX || nextY != worldY) {
                collisionOn = false;
                int oldX = worldX, oldY = worldY;
                worldX = nextX;
                worldY = nextY;
                gp.cChecker.checkTile(this);
                worldX = oldX;
                worldY = oldY;

                int objIndex = -1;
                for (int i = 0; i < gp.obj.length; i++) {
                    SuperObject obj = gp.obj[i];
                    if (obj != null && obj.worldX == nextX && obj.worldY == nextY) {
                        objIndex = i;
                        break;
                    }
                }

                boolean canMovePlayer = false;

                if (!collisionOn && canMoveTo(nextX, nextY) && (objIndex == -1 || gp.obj[objIndex] == null)) {
                    canMovePlayer = true;
                } else if (objIndex != -1 && gp.obj[objIndex] != null) {
                    boolean pushed = tryPushObject(objIndex);
                    if (pushed) {
                        canMovePlayer = true;
                    }
                }

                if (canMovePlayer) {
                    targetX = nextX;
                    targetY = nextY;
                    moving = true;
                }

                keyH.upPressed = keyH.downPressed = keyH.leftPressed = keyH.rightPressed = false;
            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }

        if (moving) {
            if (worldX < targetX) {
                worldX += speed;
                if (worldX > targetX)
                    worldX = targetX;
            }
            if (worldX > targetX) {
                worldX -= speed;
                if (worldX < targetX)
                    worldX = targetX;
            }
            if (worldY < targetY) {
                worldY += speed;
                if (worldY > targetY)
                    worldY = targetY;
            }
            if (worldY > targetY) {
                worldY -= speed;
                if (worldY < targetY)
                    worldY = targetY;
            }
            if (worldX == targetX && worldY == targetY) {
                moving = false;
            }
        }
    }

    private boolean canMoveTo(int nextX, int nextY) {
        if (nextX < 0 || nextY < 0 ||
                nextX > gp.screenWidth - gp.tileSize ||
                nextY > gp.screenHeight - gp.tileSize) {
            return false;
        }
        return true;
    }

    private boolean tryPushObject(int i) {
        if (gp.obj[i] == null)
            return false;

        String objectName = gp.obj[i].name;

        switch (objectName) {
            case "Key" -> {
                int keyX = gp.obj[i].worldX;
                int keyY = gp.obj[i].worldY;
                switch (direction) {
                    case "up" -> keyY -= gp.tileSize;
                    case "down" -> keyY += gp.tileSize;
                    case "left" -> keyX -= gp.tileSize;
                    case "right" -> keyX += gp.tileSize;
                }

                int tileCol = keyX / gp.tileSize;
                int tileRow = keyY / gp.tileSize;

                if (tileCol < 0 || tileRow < 0 ||
                        tileCol >= gp.tileM.mapTileNum.length ||
                        tileRow >= gp.tileM.mapTileNum[0].length) {
                    return false;
                }

                boolean canMove = !gp.tileM.tile[gp.tileM.mapTileNum[tileCol][tileRow]].collision;

                boolean occupied = false;
                for (int j = 0; j < gp.obj.length; j++) {
                    SuperObject obj = gp.obj[j];
                    if (obj != null && j != i && obj.worldX == keyX && obj.worldY == keyY && !"Door".equals(obj.name)) {
                        occupied = true;
                        break;
                    }
                }

                if (canMove && !occupied) {
                    gp.obj[i].worldX = keyX;
                    gp.obj[i].worldY = keyY;
                    for (int j = 0; j < gp.obj.length; j++) {
                        SuperObject obj = gp.obj[j];
                        if (obj != null && "Door".equals(obj.name) && obj.worldX == keyX && obj.worldY == keyY) {
                            gp.playSE(2);
                            gp.obj[j] = null;
                            gp.obj[i] = null;
                            break;
                        }
                    }
                    return true;
                }
                return false;
            }
            case "Flag" -> {
                gp.playSE(1);
                level++;
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.exit(0);
                return false;
            }
            default -> {
                return false;
            }
        }
    }

    public void draw(java.awt.Graphics2D g2) {
        BufferedImage image = leftStill;
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
