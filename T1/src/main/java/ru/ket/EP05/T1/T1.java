package ru.ket.EP05.T1;

import ru.regiuss.EP05.core.module.SimpleModule;

public class T1 extends SimpleModule<MainController> {

    public T1() {
        super(MainController.class);
    }

    @Override
    public String getTitle() {
        return "УП05 задание 1";
    }
}
