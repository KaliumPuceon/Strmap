package za.co.barclays.strmap;

import java.util.ArrayList;
import java.util.List;

public class Classifier {

    List<String> fingerprint = new ArrayList<>();

    public float scoreMac(int max, int min, int str){

        float avg = (min+max)/2;
        float range = Math.abs(max - min);
        range *= 1.2;

        float score = 0;

        if ((str <= avg-(range/2)) || (str >= avg+(range/2))){

            score = 0;

        }
        else if (str>=min && str<=max && str != avg){

            score = 1-(Math.abs(str-avg)/(range/2));

        }
        else if (str == avg){
            score = 1;
        }

        return score;

    }

    public void generate_fingerprint(String s){

        

    }

}
