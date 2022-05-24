package Main;

public class tovar {
    private String name;
    private tsena tsena;

    tovar(){}

    tovar(String name_, tsena tsena_){
        name = name_;
        tsena = tsena_;
    }

    public void editName(String name) {this.name = name;}
    public void editPrice(tsena tsena) {this.tsena = tsena;}
    public String getName(){return name;}
    public tsena getPrice(){return tsena;}
}


