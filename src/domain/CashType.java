package domain;

public enum CashType {
    TWO_HUNDRED_THOUSAND(200000),ONE_HUNDRED_THOUSAND(100000),FIFTY_THOUSAND(50000),
    TWENTY_THOUSAND(20000),TEN_THOUSAND(10000);

    private Integer value;

    CashType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
