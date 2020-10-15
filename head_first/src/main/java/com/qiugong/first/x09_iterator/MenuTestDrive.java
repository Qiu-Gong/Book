package com.qiugong.first.x09_iterator;

import com.qiugong.first.x09_iterator.menu.CafeMenu;
import com.qiugong.first.x09_iterator.menu.DinerMenu;
import com.qiugong.first.x09_iterator.menu.PancakeHouseMenu;

public class MenuTestDrive {
    public static void main(String args[]) {
        PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        DinerMenu dinerMenu = new DinerMenu();
        CafeMenu cafeMenu = new CafeMenu();

        Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu, cafeMenu);
        waitress.printMenu();
    }
}
