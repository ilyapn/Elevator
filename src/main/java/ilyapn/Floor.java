package ilyapn;

/**
 * Created by ilyaP on 23.05.2019.
 */
public class Floor {
    int number;
    boolean call;

    public Floor(int number) {
        this.number = number;
        call = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isCall() {
        return call;
    }

    public void doCall() {
        this.call = true;
    }

    public void cancelCall(){
        this.call = false;
    }
}
