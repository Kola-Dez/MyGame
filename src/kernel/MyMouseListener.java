package kernel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        // Пустая реализация, если не нужно обрабатывать перетаскивание мыши
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Выводим координаты мыши при ее движении
        System.out.println("Mouse moved: X=" + e.getX() + ", Y=" + e.getY());
    }
}
