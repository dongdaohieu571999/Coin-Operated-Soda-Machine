package controller;

import domain.SodaMachine;

import java.util.*;

public class Controller {

    public void processing(){
        Scanner scanner = new Scanner(System.in);
        SodaMachine sodaMachine = new SodaMachine();
        sodaMachine.initialMachine();
        Boolean isKeepBuying;

        String content = "Chào mừng quý khách đến với máy bán soda tự động!\n"
                + "Máy Chỉ có thể nhận được các loại tiền 10.000 VNĐ - 20.000 VNĐ - 50.000 VNĐ - 100.000 VNĐ - 200.000 VNĐ\n"
                + "Quý khách vui lòng chỉ sử dụng những loại tiền như trên.\n";
        System.out.println(content);

        sodaMachine.displayListOfItem();
        do{
            System.out.println("Mời quý khách nhập số tiền vào máy: (Ví dụ 10000,20000...)");
            System.out.print("Số tiền: ");
            Integer amountOfCash = sodaMachine.validateInputCash();

            System.out.print("Quý khách có muốn tiếp tục thực hiện mua hàng hay không ? Y/N: ");
            if(!scanner.nextLine().equalsIgnoreCase("Y")){
                System.out.println("GIAO DỊCH BỊ HỦY \nSố tiền trả lại: " + amountOfCash);
                System.exit(0);
            }

            System.out.println("Mời quý khách nhập mã số của sản phẩm");
            System.out.print("ID: ");
            Integer id = sodaMachine.validateInputId();

            sodaMachine.getItemAndReturnCharge(id, amountOfCash);

            System.out.print("Quý khách có muốn tiếp tục mua hàng hay không Y/N: ");
            isKeepBuying = scanner.nextLine().equalsIgnoreCase("Y");

        }while(isKeepBuying);
    }

}
