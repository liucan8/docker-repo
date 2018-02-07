package com.lc.utils.weiChat;

import java.io.Serializable;

/**
 * Created by dell on 2017/2/20.
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    private Button[] button;
    public Button[] getButton() {
        return button;
    }
    public void setButton(Button[] button) {
        this.button = button;
    }
}
