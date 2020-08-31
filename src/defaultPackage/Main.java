package defaultPackage;

import controller.CtrlFrameMenu;
import view.FrameMenu;

public class Main {

    public static void main(String[] args) {

        double totalPeso = Double.parseDouble(args[0]);

        CtrlFrameMenu ctrlFrameMenu = new CtrlFrameMenu(totalPeso);
        new FrameMenu(ctrlFrameMenu);
    }
}
