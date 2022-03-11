package Main;

import java.util.ArrayList;


public class itemList {
    private ArrayList<shopItem> list = new ArrayList<shopItem>();



    public void add(shopItem x){
        list.add(x);
    }

    public void add(String name, int price){
       list.add(new shopItem(name, new Price(price)));
    }
    public void add(String name, Price price){
        list.add(new shopItem(name, price));
    }

    public int priceCount(int code, int count){
        var price=0;
        if(list.size()>=code) price = list.get(code).getPrice().get();
        return price*count;
    }

    public int count(){return list.size();}

    public void delete(int code){
        list.remove(code);
    }

    public String getName(int code){return list.get(code).getName();}
    public Price getPrice(int code){return list.get(code).getPrice();}
    public void editName(int code, String name){list.get(code).editName(name);}
    public void editPrice(int code, Price price){list.get(code).editPrice(price);}

}
