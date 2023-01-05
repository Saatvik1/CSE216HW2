package arithmetic;
public class PlusOrMinusOne {
    private int value;
    private final static PlusOrMinusOne[] values = {new PlusOrMinusOne(1), new PlusOrMinusOne(-1)};
    public PlusOrMinusOne(int n){
        this.value = n;
    }
    public static PlusOrMinusOne[] values(){return values;}
    @Override
    public String toString(){return "" + this.value;}
}