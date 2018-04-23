package util;

import javafx.scene.control.Alert;

public class AlertUtils {
    public static void showNotEqualPropsAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Совершенные сочетания не будут найдены, так как доли имеют разную мощность");
        alert.showAndWait();
    }
    public static void showHasRemainingVerts(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Есть вершины без доли");
        alert.showAndWait();
    }
    public static void showEdgeHasMoreVertsThenProp(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Количество вершин в ребре больше чем долей");
        alert.showAndWait();
    }
    public static void showEdgeIsInProp(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Ребро лежит в доле");
        alert.showAndWait();
    }
    public static void showEdgeExists(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Ребро уже существует");
        alert.showAndWait();
    }
    public static void showNoWeight(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Введите количество весов");
        alert.showAndWait();
    }
    public static void showHasMoreThanFiveWeight(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Количество весов не должно быть больше 5");
        alert.showAndWait();
    }
    public static void showEdgeIsEmpty(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Пустое ребро");
        alert.showAndWait();
    }
}
