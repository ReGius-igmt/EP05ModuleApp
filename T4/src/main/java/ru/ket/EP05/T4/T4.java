package ru.ket.EP05.T4;

import ru.regiuss.EP05.core.module.SimpleModule;
import ru.regiuss.EP05.core.selectable.SingleSelectable;

public class T4 extends SimpleModule {

    public T4() {
        selectable = new SingleSelectable(getClass().getResource("/view/main.fxml"), new MainController());
    }

    @Override
    public String getTitle() {
        return "УП05 задание 4";
    }
}
