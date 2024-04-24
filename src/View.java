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
        setTitle(Definitions.gameName);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - Definitions.widthWindow / 2, dimension.height / 2 - Definitions.heightWindow / 2, Definitions.widthWindow, Definitions.heightWindow);
        MyPanel panel = new MyPanel(c);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ImageIcon icon = new ImageIcon(Definitions.imageDirectory + "IconMy.png");
        setIconImage(icon.getImage());

        setVisible(true);
        setResizable(false);
        addKeyListener(c);

        JMenuBar mainMenu = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem pause = new JMenuItem("Pause/Resume");
        pause.addActionListener(c);
        pause.setAccelerator(KeyStroke.getKeyStroke('p'));

        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.addActionListener(c);
        instructions.setAccelerator(KeyStroke.getKeyStroke('i'));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(c);


        optionsMenu.add(pause);
        optionsMenu.add(instructions);
        optionsMenu.add(exit);
        mainMenu.add(optionsMenu);
        setJMenuBar(mainMenu);
    }

    public void actionPerformed(ActionEvent event) {
        repaint();
    }
}
