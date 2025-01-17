package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameStates.GameState;
import gameStates.Playing;
import main.Game;
import utils.LoadSave;

import static utils.Constants.UI.URMButtons.*;

public class LevelCompleteOverlay {

    private Playing playing;
    private URMButtons menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;

    public LevelCompleteOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);

        next = new URMButtons(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new URMButtons(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtLas(LoadSave.LEVEL_COMPLETE);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = (int) (Game.GAME_WIDTH/2 - bgW/2);
        bgY = (int) (75*Game.SCALE);
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    public void update() {
        next.update();
        menu.update();
    }

    private boolean isIn(URMButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseHover(false);
        menu.setMouseHover(false);
        if (isIn(menu, e)) {
            menu.setMouseHover(true);
        }
        if (isIn(next, e)) {
            next.setMouseHover(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAllPlaying();
                GameState.state = GameState.MENU;
            }
        }
        if (isIn(next, e)) {
            if (next.isMousePressed()) {
                playing.loadNextLevel();
            }
        }

        menu.resetBooleans();
        next.resetBooleans();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        }
        if (isIn(next, e)) {
            next.setMousePressed(true);
        }
    }
}
