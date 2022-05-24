package Main;

public class tsena {
    private int r, k;

    public tsena(int n){
        r = n/100;
        k = n%100;
    }

    public tsena(int rub, int kop) {
        r = rub;
        k = kop;
    }

    public int get(){
        return r*100 + k;
    }

    public tsena set(int x) {
        r = x/100;
        k = x%100;
        return this;
    }

}
