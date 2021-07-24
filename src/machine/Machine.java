package machine;

import domain.CashType;
import domain.Item;
import domain.Storage;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface Machine {
    void initialMachine();
    Integer validateInputId();
    Integer validateInputCash();
    void displayListOfItem();
    void getItemAndReturnCharge(Integer id, Integer amountOfCash);
    List getCharge(Integer balance, Storage<CashType> listCash);
    void updateStorage(Integer balance,Item item);
    void updateStorage(Integer balance,Item item, List<CashType> cashTypeList);
}
