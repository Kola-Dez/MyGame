import java.awt.Graphics;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Objects;
import javax.swing.*;

public class Controller implements MouseListener, KeyListener, ActionListener {
    private Model model;
    private final View view;
    private final Timer timer;
    private boolean paused;
    HashMap<String, Integer> resources;


    //  конструктор
    public Controller(Model model) {
        this.model = model;
        paused = false;
        this.resources = model.getResources();
        view = new View(this);
        timer = new Timer(20, view);
        timer.start();
    }

    // Метод для обновления графики
    public void update(Graphics g) {
        if (model != null) {
            if (model.gameIsOver()) {
                timer.stop();
                if (model.playerWon()) {
                    JOptionPane.showMessageDialog(view, Definitions.winningText, Definitions.gameName, JOptionPane.INFORMATION_MESSAGE, Definitions.fairBolIcon);
                    handleWin(); // Обработка победы
                } else {
                    JOptionPane.showMessageDialog(view, Definitions.losingText, Definitions.gameName, JOptionPane.INFORMATION_MESSAGE, Definitions.fairBolIcon);
                    handleLoss(); // Обработка проигрыша
                }
                model = new Model(this.resources);
                this.resources.put("COINS", 0);

                timer.start();
            } else {
                model.update(g);
            }
        }
    }



    public void handleWin() {
        this.resources.put("MANY_COIN", Math.max(model.getManyCoin(), 0));
        this.resources.put("MANY_KILL_SKELETON",Math.max(model.getManyKillSkeleton(), 0));
        this.resources.put("NOW_LEVEL", this.resources.get("NOW_LEVEL") + 1);
        this.resources.put("COINS", this.resources.get("MANY_COIN"));
    }

    public void handleLoss() {
        this.resources.put("MANY_KILL_SKELETON", 0);
        this.resources.put("MANY_COIN", model.getManyCoin() - model.getManyCoinNuw());
        this.resources.put("NOW_LEVEL", (this.resources.get("NOW_LEVEL")) - ((this.resources.get("NOW_LEVEL")) <= 1 ? 0 : 1));
    }


    // Метод для приостановки/возобновления игры
    public void pauseResume() {
        if (!paused) {
            paused = true;
            timer.stop();
        } else {
            paused = false;
            timer.start();
        }
    }


    // Метод для отображения инструкций
    public void displayInstructions() {
        JOptionPane.showMessageDialog(view, Definitions.instructionText, Definitions.gameName, JOptionPane.INFORMATION_MESSAGE, Definitions.fairBolIcon);
    }
    public void keyPressed(KeyEvent e) {
        if (!paused) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_A:
                    model.setPlayerDirection(Definitions.VelocityState.LEFT);
                    break;
                case KeyEvent.VK_D:
                    model.setPlayerDirection(Definitions.VelocityState.RIGHT);
                    break;
                case KeyEvent.VK_SPACE:
                    model.jumpPlayer();
                    break;
                case KeyEvent.VK_ENTER:
                    model.throwFairBol();
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A:
                model.setPlayerDirection(Definitions.VelocityState.STILL, 'A');
                break;
            case KeyEvent.VK_D:
                model.setPlayerDirection(Definitions.VelocityState.STILL, 'D');
                break;
        }
    }

    // Неиспользуемые методы интерфейса KeyListener
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_P) {
            pauseResume();
        }
    }

    // Неиспользуемые методы интерфейса MouseListener
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }

    // Обработчик событий кнопок в пользовательском интерфейсе
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (Objects.equals(evt.getActionCommand(), "Pause/Resume")) {
            pauseResume();
        } else if (evt.getActionCommand().equals("Instructions")) {
            displayInstructions();
        } else if (evt.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }
}
