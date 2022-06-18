package ru.ket.EP05.T7;

import ru.regiuss.EP05.core.module.SimpleModule;
import ru.regiuss.EP05.core.selectable.SingleSelectable;

public class T7 extends SimpleModule {

    public T7() {
        selectable = new SingleSelectable(getClass().getResource("/view/main.fxml"), new MainController());
    }

    @Override
    public String getTitle() {
        return "УП05 задание 7";
    }
}
