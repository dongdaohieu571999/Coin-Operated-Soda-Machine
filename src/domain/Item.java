package domain;

public enum Item {
    COKE(1,"Coke",10000), PEPSI(2,"Pepsi",10000), SODA(3,"Soda",20000);

    private Integer id;
    private String name;
    private Integer price;

    Item(Integer id,String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }
}
