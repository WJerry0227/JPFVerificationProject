package edu.ut.verify.example;

/**
 * Created by Jerry Wang on 2018/11/26.
 */
public interface VendingMachineService {

    /**
     * Interface for vendingMachine
     * @param order customers' order
     * @return ResultMsg contains the path that this process has been through and the return money it got
     */
    ResultMsg purchasing(Order order);

    void powerOn();

    void powerOff();

    void selectDisplay();

    void showAvailableSoftDrink();

    void selectSoftDrink();

    void insertMoney();

    void verifyAmount();

    int amountCount(Order order);

    void showToSelectPanel();

    void availableDrinkAfterSell();

    void vendingMachineBusy();

    void notEnoughMoney();

    void dispenseSoftdrink();

    void changeDispense(Order order);

    void notEnoughDrink();

    void noChangeDispense();

}
