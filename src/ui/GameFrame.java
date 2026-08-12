package ui;

import model.Direction;
import model.Game;
import model.GameState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
    private Game game;
    private final GamePanel panel;
    private final Timer timer;

    public GameFrame() {
        this.game = new Game();
        this.panel = new GamePanel(game);

        setTitle("Pac-Man Classic - Kasra Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);


        setupControls();


        timer = new Timer(150, e -> {
            if (game.getState() == GameState.RUNNING) {
                game.update();
                panel.repaint();
            }
        });
        timer.start();
    }

    private void setupControls() {

        InputMap im = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = panel.getActionMap();


        mapAction(im, am, "UP", KeyEvent.VK_UP, Direction.UP);
        mapAction(im, am, "DOWN", KeyEvent.VK_DOWN, Direction.DOWN);
        mapAction(im, am, "LEFT", KeyEvent.VK_LEFT, Direction.LEFT);
        mapAction(im, am, "RIGHT", KeyEvent.VK_RIGHT, Direction.RIGHT);


        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "START");
        am.put("START", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getState() == GameState.READY) {
                    game.start();
                    panel.repaint();
                }
            }
        });


        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "RESTART");
        am.put("RESTART", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
    }

    private void mapAction(InputMap im, ActionMap am, String name, int keyCode, Direction dir) {
        im.put(KeyStroke.getKeyStroke(keyCode, 0), name);
        am.put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.handleInput(dir);
            }
        });
    }

    private void restartGame() {
        this.game = new Game();
        panel.setGame(this.game);
        panel.repaint();
    }
}
