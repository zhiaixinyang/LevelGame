package com.mdove.levelgame.main.home.model.vm;

/**
 * @author MDove on 2019/1/27
 */
public class TopMainMenuModelVM extends BaseMainMenuVM {
    public MainMenuModelVM one;
    public MainMenuModelVM two;
    public MainMenuModelVM three;

    public TopMainMenuModelVM(MainMenuModelVM one, MainMenuModelVM two, MainMenuModelVM three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }
}
