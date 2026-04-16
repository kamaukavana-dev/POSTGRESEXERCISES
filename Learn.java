//Defensive Copying
import java.util.Arrays;
class arrays{
    private int [] myArr = {1,2,3};
    arrays(int [] myArr){
        this.myArr = Arrays.copyOf(myArr, myArr.length); //copy in
    }
    public int[] getMyArr(){
        return Arrays.copyOf(myArr, myArr.length); //copy out
    }
}
public class Learn {
    public static void main(String[] args) {
        int [] arr = {1,2,4};
        arrays a = new arrays(arr);

}
