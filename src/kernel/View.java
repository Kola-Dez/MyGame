package kernel;

import kernel.Contrlollers.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private static class MyPanel extends JPanel {
        private final Controller controller;

        MyPanel(Controller c) {
            controller = c;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            controller.update(graphics);
            revalidate();
        }
    }

    public View(Controller c) {
        // Установка заголовка окна
        setTitle(Definitions.gameName);

        // Получение размера экрана и расчет координат для размещения окна по центру
        Toolkit toolkit = Toolkit.getDefaultToolkit(); // Получаем Toolkit для работы с графическими ресурсами
        Dimension dimension = toolkit.getScreenSize(); // Получаем размер экрана
        setBounds(dimension.width / 2 - Definitions.widthWindow / 2, dimension.height / 2 - Definitions.heightWindow / 2, Definitions.widthWindow, Definitions.heightWindow); // Устанавливаем координаты и размеры окна

        // Создание панели и добавление ее в контейнер окна
        MyPanel panel = new MyPanel(c); // Создаем новую панель, передавая ей контроллер
        getContentPane().add(panel); // Добавляем панель в контейнер окна

        // Установка операции закрытия окна по умолчанию
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Устанавливаем действие по закрытию окна

        // Установка иконки приложения
        ImageIcon icon = new ImageIcon(Definitions.imageDirectory + "IconMy.png"); // Создаем новый ImageIcon, используя путь к иконке
        setIconImage(icon.getImage()); // Устанавливаем иконку для окна

        // Установка видимости окна и его неразвертываемости
        setVisible(true); // Делаем окно видимым на экране
        setResizable(false); // Запрещаем изменение размеров окна пользователем

        // Добавление слушателя клавиатуры
        addKeyListener(c); // Добавляем контроллер в качестве слушателя событий клавиатуры

        // Создание и настройка основного меню
        JMenuBar mainMenu = new JMenuBar(); // Создаем новое главное меню
        JMenu optionsMenu = new JMenu("Options"); // Создаем новый раздел меню с названием "Options"

        JMenuItem pause = new JMenuItem("Pause/Resume"); // Создаем новый пункт меню "Pause/Resume"
        pause.addActionListener(c); // Добавляем контроллер как слушателя событий для этого пункта
        pause.setAccelerator(KeyStroke.getKeyStroke('p')); // Устанавливаем клавишу быстрого доступа к этому пункту

        JMenuItem instructions = new JMenuItem("Instructions"); // Создаем новый пункт меню "Instructions"
        instructions.addActionListener(c); // Добавляем контроллер как слушателя событий для этого пункта
        instructions.setAccelerator(KeyStroke.getKeyStroke('i')); // Устанавливаем клавишу быстрого доступа к этому пункту

        JMenuItem exit = new JMenuItem("Exit"); // Создаем новый пункт меню "Exit"
        exit.addActionListener(c); // Добавляем контроллер как слушателя событий для этого пункта

        optionsMenu.add(pause); // Добавляем пункт "Pause/Resume" в раздел "Options"
        optionsMenu.add(instructions); // Добавляем пункт "Instructions" в раздел "Options"
        optionsMenu.add(exit); // Добавляем пункт "Exit" в раздел "Options"
        mainMenu.add(optionsMenu); // Добавляем раздел "Options" в главное меню

        // Установка главного меню окна
        setJMenuBar(mainMenu); // Устанавливаем главное меню для окна
    }

    public void actionPerformed(ActionEvent event) {
        repaint();
    }
}
