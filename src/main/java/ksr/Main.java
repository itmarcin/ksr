package ksr;

import ksr.logic.Logic;
import ksr.userinterface.CMDInterface;


public class Main {


    public static void main(String[] args) {
        CMDInterface cmdInterface = new CMDInterface(new Logic());
        cmdInterface.startInterface();
    }
}