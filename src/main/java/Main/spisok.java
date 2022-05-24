package Main;

import java.util.ArrayList;


public class spisok {
    private ArrayList<tovar> list = new ArrayList<tovar>();

    public void add(String name, tsena tsena){
        list.add(new tovar(name, tsena));
    }

    public int priceCount(int code, int count){
        var price=0;
        if(list.size()>=code) price = list.get(code).getPrice().get();
        return price*count;
    }

    public int count(){return list.size();}

    public void remove(int code){
        list.remove(code);
    }

    public String getName(int code){return list.get(code).getName();}
    public tsena getPrice(int code){return list.get(code).getPrice();}
    public void editName(int code, String name){list.get(code).editName(name);}
    public void editPrice(int code, tsena tsena){list.get(code).editPrice(tsena);}

}
