package projeto.les.doevida.doevida.Utils;

/**
 * Created by Marcos Nascimento on 4/2/2016.
 */
public class Compatible {

    //Doner
    private String[] Onegative;
    private String[] Opositive;
    private String[] Anegative;
    private String[] Apositive;
    private String[] Bnegative;
    private String[] Bpositive;
    private String[] ABnegative;
    private String[] ABpositive;

    public Compatible(){
        Onegative = new String[]{"O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"};
        Opositive = new String[]{"O+", "A+", "B+", "AB+"};
        Anegative = new String[]{"A-", "A+", "AB-", "AB+"};
        Apositive = new String[]{"A+", "AB+"};
        Bnegative = new String[]{"B-", "B+", "AB-", "AB+"};
        Bpositive = new String[]{"B+", "AB+"};
        ABnegative = new String[]{"AB-", "AB+"};
        ABpositive = new String[]{"AB+"};
    }

    public String[] getCompatibleOnegative(){
        return Onegative;
    }

    public String[] getCompatibleOpositive(){
        return Opositive;
    }

    public String[] getCompatibleAnegative(){
        return Anegative;
    }

    public String[] getCompatibleApositive(){
        return Apositive;
    }

    public String[] getCompatibleBnegative(){
        return Bnegative;
    }

    public String[] getCompatibleBpositive(){
        return Bpositive;
    }

    public String[] getCompatibleABnegative(){
        return ABnegative;
    }

    public String[] getCompatibleABpositive(){
        return ABpositive;
    }
}