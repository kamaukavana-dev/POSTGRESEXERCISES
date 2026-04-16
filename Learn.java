//Defensive Copying
import java.util.Arrays;
class arrays{
    private int [] myArr = {1,2,3};
    arrays(int [] myArr){
        this.myArr = Arrays.copyOf(myArr, myArr.length); //copy in
    }

}

