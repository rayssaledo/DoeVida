package projeto.les.doevida.doevida.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Marcos Nascimento on 4/2/2016.
 */
public class Compatible {

    private Map<Character, List<Character>> compatible;

    public Compatible(){
        Character[] types = {'A', 'B', 'O'};
        for(Character type: types){
            compatible.put(type,new ArrayList<Character>());
        }
        //TODO
    }
}
