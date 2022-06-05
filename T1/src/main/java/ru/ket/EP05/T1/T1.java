package ru.ket.EP05.T1;

import ru.regiuss.EP05.core.module.SimpleModule;
import ru.regiuss.EP05.core.selectable.SingleSelectable;

public class T1 extends SimpleModule {

    public T1() {
        selectable = new SingleSelectable(getClass().getResource("/view/main.fxml"), new MainController());
    }

    @Override
    public String getTitle() {
        return "УП05 задание 1";
    }
}
