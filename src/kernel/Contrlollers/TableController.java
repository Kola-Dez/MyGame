package kernel.Contrlollers;

import Objects.Table;
import kernel.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TableController {
    public TableController(Graphics graphics, ArrayList<HashMap<String, String>> result, ModelController modelController) {
        if(result == null) return;
        Model model = modelController.getModel();
        Table table = model.getTable();
        int x = (int) table.getX(); // Начальная координата X для таблицы
        int y = (int) table.getY(); // Начальная координата Y для таблицы
        int numCols = 3; // Количество столбцов
        int numRows = 10; // Количество строк
        int cellWidth = table.getWidth() / numCols; // Ширина ячейки
        int cellHeight = table.getHeight() / numRows; // Высота ячейки

        // Отрисовка рамки таблицы
        graphics.drawRect(x, y, numCols * cellWidth, numRows * cellHeight);

        // Отрисовка вертикальных линий для разделения столбцов
        for (int i = 1; i < numCols; i++) {
            graphics.drawLine(x + i * cellWidth, y, x + i * cellWidth, y + numRows * cellHeight);
        }

        // Отрисовка горизонтальных линий для разделения строк
        for (int i = 1; i < numRows; i++) {
            graphics.drawLine(x, y + i * cellHeight, x + numCols * cellWidth, y + i * cellHeight);
        }

        // Отображение текста в ячейках таблицы
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int textX = 0; // Координата X для текста
                int textY = 0; // Координата Y для текста
                String text = " ";
                if (row == 0) {
                    int padding = 10;
                    if (col == 0) {
                        text = "Name";
                        padding = 7;
                    } else if (col == 1) {
                        text = "skeletonKill";
                        padding = 23;
                    } else {
                        text = "Time";
                    }
                    textX = x + col * cellWidth + cellWidth / 2 - padding; // Координата X для текста
                    textY = y + row * cellHeight + cellHeight / 2; // Координата Y для текста
                } else {
                    if (row - 1 < result.size()) {
                        if (col == 0) {
                            text = result.get(row - 1).get("NAME");
                        } else if (col == 1) {
                            text = result.get(row - 1).get("MANY_KILL_SKELETON");
                        } else {
                            text = result.get(row - 1).get("TIME_WIN");
                        }
                    }
                    textX = x + col * cellWidth + cellWidth / 2; // Координата X для текста
                    textY = y + row * cellHeight + cellHeight / 2; // Координата Y для текста
                }


                // Назначение цвета текста в зависимости от колонки
                switch (col) {
                    case 0:
                        graphics.setColor(Color.RED);
                        break;
                    case 1:
                        graphics.setColor(Color.GREEN);
                        break;
                    case 2:
                        graphics.setColor(Color.YELLOW);
                        break;
                    default:
                        graphics.setColor(Color.BLACK);
                }

                graphics.drawString(text, textX, textY);
            }
        }
    }
}

