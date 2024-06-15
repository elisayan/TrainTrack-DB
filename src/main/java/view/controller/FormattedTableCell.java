package view.controller;

import javafx.scene.control.TableCell;

public class FormattedTableCell<T> extends TableCell<T, Float> {
    @Override
    protected void updateItem(Float item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(String.format("%.2f min", item)); // Formattazione con due decimali e aggiunta di 'min'
        }
    }
}
