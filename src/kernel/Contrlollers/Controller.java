package kernel.Contrlollers;

import Database.Connect;
import kernel.Converter;
import kernel.Definitions;
import kernel.View;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Controller implements KeyListener, ActionListener {
    private final HashMap<String, Integer> resources;
    private final RequestDBController requestDBController;
    private ArrayList<HashMap<String, String>> result = null;
    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private ModelController modelController;
    private long startTime;
    private final Timer timer;
    private final View view;
    private boolean paused;
    private boolean isConnect = true;
    //  конструктор
    public Controller(ModelController modelController) {
        this.requestDBController = new RequestDBController();
        this.resources = modelController.getResources();
        this.modelController = new ModelController(this.resources);
        view = new View(this);
        timer = new Timer(20, view);
        paused = false;
        timer.start();
        timerStart();
        testConnect();
    }
    // Метод для обновления графики
    public void update(Graphics graphics) {
        if (modelController != null) {
//            view.addMouseMotionListener(new MyMouseListener());
            if (modelController.gameIsOver()) {
                resetKeyStates();
                timer.stop();
                if (modelController.playerWon()) {
                    handleWin();
                } else {
                    handleLoss();
                }
                modelController = new ModelController(this.resources);
                this.resources.put("COINS", 0);
                timer.start();
            } else {
                modelController.update(graphics);
                if (this.resources.get("NOW_LEVEL") >= 4 && this.resources.get("NOW_LEVEL") != 0){
                    if (isConnect){
                        if (result == null){
                            resources.put("TIME_WIN", getTime());
                            requestDBController.setResult(resources, "Me");
                            this.result = requestDBController.getResult();
                        }
                        if(result != null){
                            new TableController(graphics, getSortedResult(), modelController);
                        }
                    }
                    if (modelController.getIsRestart()){
                        timer.stop();
                        handRestart();
                        resetKeyStates();
                        modelController = new ModelController(this.resources);
                        modelController.setIsRestart(false);
                        this.result = null;
                        testConnect();
                        timer.start();
                        timerStart();
                    }
                }
            }
        }
    }
    private int getTime(){
        long endTime = System.nanoTime();
        double durationSeconds = (endTime - startTime) / 1e9;
        return (int)durationSeconds;
    }
    private ArrayList<HashMap<String, String>> getSortedResult() {
        ArrayList<HashMap<String, String>> sortedResult = new ArrayList<>(result);
        Collections.sort(sortedResult, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                int timeWin1 = Integer.parseInt(o1.get("TIME_WIN"));
                int timeWin2 = Integer.parseInt(o2.get("TIME_WIN"));
                return Integer.compare(timeWin1, timeWin2);
            }
        });
        return sortedResult;
    }

    private void drawText(Graphics graphics) {
        // Пример отображения текста "You WIN"
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.BOLD, 24));
        graphics.drawString("Hello", 100, 100); // Позиция и текст
    }
    private void resetKeyStates() {
        // Сбрасываем состояние всех клавиш
        leftPressed = false;
        rightPressed = false;
    }
    public void handleWin() {
        JOptionPane.showMessageDialog(view,
                Definitions.winningText,
                Definitions.gameName,
                JOptionPane.INFORMATION_MESSAGE,
                Definitions.fairBolIcon
        );
        checksWin();
    }

    public void handleLoss() {
        int result = JOptionPane.showOptionDialog(
                null,
                Definitions.losingText,
                Definitions.gameName,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                Definitions.fairBolIcon,
                new Object[]{"Да", "Нет"},
                "Нет"
        );
        if (result == JOptionPane.YES_OPTION) {
            if (this.resources.get("MANY_COIN") >= Definitions.coinDefaultMinus) {
                this.resources.put("MANY_COIN", this.resources.get("MANY_COIN") - Definitions.coinDefaultMinus);
            } else {
                JOptionPane.showMessageDialog(null,
                        Definitions.notHaveCoin,
                        Definitions.gameName,
                        JOptionPane.INFORMATION_MESSAGE,
                        Definitions.fairBolIcon
                );
                checksDefeat();
            }
        } else if (result == JOptionPane.NO_OPTION) {
            checksDefeat();
        }
        this.resources.put("MANY_KILL_SKELETON", 0);
    }

    private void timerStart(){
        this.startTime = System.nanoTime();
    }
    private void testConnect(){
        Connect connect = new Connect();
        if (connect.getConnection() != null){
            connect.closeConnection();
        }else {
            isConnect = false;
        }
    }

    private void handRestart() {

        // Отображаем диалоговое окно с таблицей
        int result = JOptionPane.showOptionDialog(
                null,
                Definitions.restartText,
                Definitions.winningText,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                Definitions.fairBolIcon,
                new Object[]{"Да", "Нет"},
                "Нет"
        );
        if (result == JOptionPane.YES_OPTION) {
            this.resources.put("MANY_COIN", 0);
            this.resources.put("MANY_KILL_SKELETON", 0);
            this.resources.put("NOW_LEVEL", 1);
            this.resources.put("COINS", 0);
        } else if (result == JOptionPane.NO_OPTION) {
            System.exit(0); // Завершение программы
        }
    }

    private void checksWin() {
        this.resources.put("MANY_COIN", Math.max(modelController.getManyCoin(), 0));
        this.resources.put("MANY_KILL_SKELETON", Math.max(modelController.getManyKillSkeleton(), 0));
        this.resources.put("NOW_LEVEL", this.resources.get("NOW_LEVEL") + 1);
        this.resources.put("COINS", this.resources.get("MANY_COIN"));
    }

    private void checksDefeat() {
        this.resources.put("MANY_COIN", modelController.getManyCoin() - modelController.getManyCoinNuw());
        this.resources.put("NOW_LEVEL", Math.max(this.resources.get("NOW_LEVEL") - 1, 1));
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
                    leftPressed = true;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_SPACE:
                    modelController.jumpPlayer();
                    break;
                case KeyEvent.VK_ENTER:
                    modelController.throwFairBol();
                    break;
            }
            updatePlayerDirection();
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
        }
        updatePlayerDirection();
    }

    private void updatePlayerDirection() {
        if (modelController.checksPlayer() != null) {
            if (leftPressed && !rightPressed) {
                modelController.setPlayerDirection(Definitions.VelocityState.LEFT);
            } else if (!leftPressed && rightPressed) {
                modelController.setPlayerDirection(Definitions.VelocityState.RIGHT);
            } else {
                modelController.setPlayerDirection(Definitions.VelocityState.STILL);
            }
        }
    }

    // Неиспользуемые методы интерфейса KeyListener
    public void keyTyped(KeyEvent e) {
    }

    // Неиспользуемые методы интерфейса MouseListener
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

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
