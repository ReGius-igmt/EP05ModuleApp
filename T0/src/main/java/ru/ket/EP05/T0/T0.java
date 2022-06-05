package ru.ket.EP05.T0;

import ru.regiuss.EP05.core.module.SimpleModule;
import ru.regiuss.EP05.core.selectable.SingleSelectable;

public class T0 extends SimpleModule {

    public T0() {
        selectable = new SingleSelectable(getClass().getResource("/view/main.fxml"), new MainController());
    }

    @Override
    public String getTitle() {
        return "УП05 задание 0";
    }
}
