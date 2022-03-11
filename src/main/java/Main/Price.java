package Main;

public class Price {
    private int rub, kop;

    public Price(int n){
        rub = n/100;
        kop = n%100;
    }

    public Price(int rub_, int kop_) {
        rub = rub_;
        kop = kop_;
    }

    public int get(){
        return rub*100 + kop;
    }

    public int getKop(){return kop;}
    public int getRub(){return rub;}

    public Price set(int n) {
        rub = n/100;
        kop = n%100;
        return this;
    }
    public Price set(int rub_, int kop_) {
        rub = rub_;
        kop = kop_;
        return this;
    }
}
