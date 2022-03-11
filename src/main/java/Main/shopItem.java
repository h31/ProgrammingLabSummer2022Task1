package Main;

public class shopItem {
    private String name;
    private Price price;

    shopItem(){}

    shopItem(String name_, Price price_){
        name = name_;
        price = price_;
    }

    public void editName(String name) {this.name = name;}
    public void editPrice(Price price) {this.price = price;}
    public String getName(){return name;}
    public Price getPrice(){return price;}
}


