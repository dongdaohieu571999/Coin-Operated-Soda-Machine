package domain;

import domain.CashType;
import domain.Item;
import domain.Storage;
import exeptionhandler.NotEnoughChangeExeption;
import exeptionhandler.SoldOutExeption;
import machine.Machine;

import java.util.*;
import java.util.stream.Collectors;

public class SodaMachine implements Machine {
    private Storage<Item> listItem;
    private Storage<CashType> listCash;
    private Scanner scanner;

    @Override
    public void initialMachine() {
        scanner = new Scanner(System.in);
        listItem = new Storage<>();
        listCash = new Storage<>();

        for(Item item : Item.values()){
            listItem.put(item, 5);
        }

        for(CashType cash : CashType.values()){
            listCash.put(cash, 5);
        }
    }

    @Override
    public Integer validateInputId() {
        String value = scanner.nextLine();
        List<String> listID = new ArrayList<>();
        for (Item item : Item.values()){
            listID.add(String.valueOf(item.getId()));
        }
        while(!(listID.contains(value))){
            System.out.println("Vui lòng nhập lại ID");
            System.out.print("ID: ");
            value = scanner.nextLine();
        }
        return Integer.valueOf(value);
    }

    @Override
    public Integer validateInputCash() {
        String value = scanner.nextLine();
        while(!(value.equals("10000") || value.equals("20000") || value.equals("50000") || value.equals("100000") || value.equals("200000"))){
            System.out.println("Vui lòng nhập lại số tiền");
            System.out.print("Số tiền: ");
            value = scanner.nextLine();
        }
        return Integer.valueOf(value);
    }

    @Override
    public void displayListOfItem() {
        Map<Item, Integer> mapItems = listItem.getStorage();
        System.out.println("Danh sách sản phẩm");
        System.out.println("ID \t\t Nhãn hiệu \t\t Giá tiền");
        mapItems.entrySet().stream().sorted(Map.Entry.comparingByKey()).
                forEach(item -> System.out.println(item.getKey().getId() + " \t\t " + item.getKey().getName() + " \t\t\t " + item.getKey().getPrice() + "VNĐ"));
    }

    @Override
    public void getItemAndReturnCharge(Integer id, Integer amountOfCash) {
        Integer balance = amountOfCash;
        Integer amountOfCharge;

        Item item = Arrays.stream(Item.values()).filter(t -> t.getId() == id).findFirst().get();

        if(balance >= item.getPrice()){
            if(listItem.isRemainItem(item)){
                if(balance.equals(item.getPrice())){
                    this.updateStorage(balance,item);
                    System.out.println("NHẬN HÀNG THÀNH CÔNG! \n" + "Loại mặt hàng \t\t Tiền trả lại\n" + item.getName() + "\t\t\t\t\t" + " 0 VNĐ");
//                      mute 2 comment to see full detail of storage
//                      this.displayItemRemain();
//                      this.displayCashTypeRemain();
                } else {
                    List<CashType> chargeList = getCharge(balance - item.getPrice(), listCash);
                    if(chargeList != null && chargeList.size() != 0){
                        this.updateStorage(balance,item,chargeList);
//                      mute 2 comment to see full detail of storage
//                      this.displayItemRemain();
//                      this.displayCashTypeRemain();
                        amountOfCharge = chargeList.stream().mapToInt(t -> t.getValue()).sum();
                        System.out.println("NHẬN HÀNG THÀNH CÔNG! \n" + "Loại mặt hàng \t\t Tiền trả lại\n" + item.getName() + "\t\t\t\t\t" + amountOfCharge + " VNĐ");

                    }else {
                        System.out.println("Xin lỗi máy không đủ để trả tiền thừa");
                        System.out.println("GIAO DỊCH KHÔNG THÀNH CÔNG \n Số tiền trả lại khách hàng: "+ balance + "VNĐ");
                    }
                }

            } else {
                System.out.println(new SoldOutExeption("Mặt hàng này đã hết").getMessage());
                System.out.println("GIAO DỊCH KHÔNG THÀNH CÔNG \n Số tiền trả lại khách hàng: "+ balance + "VNĐ");
            }
        } else {
            System.out.println(new NotEnoughChangeExeption("Số tiền không đủ để mua hàng").getMessage());
            System.out.println("GIAO DỊCH KHÔNG THÀNH CÔNG \nSố tiền trả lại khách hàng: "+ balance + "VNĐ");
        }
    }

    @Override
    public List<CashType> getCharge(Integer balance, Storage<CashType> listCash) {
        List<CashType> cashTypeList = listCash.getStorage().keySet().stream().sorted((t1,t2) -> t2.getValue() - t1.getValue()).collect(Collectors.toList());
        Storage<CashType> listCashClone= listCash.cloneStorage();
        List<CashType> charge = new ArrayList<>();
        outer:for(CashType cashType : cashTypeList){
            while(balance > 0){
                if(balance >= cashType.getValue() && listCashClone.isRemainItem(cashType)){
                    balance -= cashType.getValue();
                    charge.add(cashType);
                    listCashClone.deduct(cashType);
                } else {
                    continue outer;
                }
            }
        }

        if(balance.equals(0)){
            return charge;
        }else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void updateStorage(Integer balance,Item item) {
        listItem.deduct(item);
        listCash.add(Arrays.stream(CashType.values()).filter(t -> t.getValue().equals(balance)).findFirst().get());
    }

    @Override
    public void updateStorage(Integer balance, Item item, List<CashType> chargeList) {
        updateStorage(balance, item);
        chargeList.forEach(t -> listCash.deduct(t));
    }

    public void displayCashTypeRemain(){
        Map<CashType, Integer> mapItems = listCash.getStorage();
        System.out.println("Danh sách loại tiền còn lại trong máy");
        System.out.println("Giá tiền \t\t số lượng");
        mapItems.entrySet().forEach(item -> System.out.println(item.getKey().getValue() + " \t\t " + item.getValue()));
    }

    public void displayItemRemain(){
        Map<Item, Integer> mapItems = listItem.getStorage();
        System.out.println("Danh sách sản phẩm còn lại trong máy");
        System.out.println("Giá tiền \t\t số lượng");
        mapItems.entrySet().forEach(item -> System.out.println(item.getKey().getName() + " \t\t " + item.getValue()));
    }
}
