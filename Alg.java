import java.sql.Array;
import java.util.*;
import java.util.Random;
public class Alg {
    public static String toBinary(char c){
        /*
        &
        abcde
        fghij
        klmno
        pqrst
        uvwxy
        z.,!?
        _
        */
        String binary = "";
        String alph = "&abcdefghijklmnopqrstuvwxyz.,!?_";
        int index = alph.indexOf(c);
        if(index >= 16){
            index -= 16;
            binary += "1";
        }else{
            binary += "0";
        }
        if(index >= 8){
            index -= 8;
            binary += "1";
        }else{
            binary += "0";
        }
        if(index >= 4){
            index -= 4;
            binary += "1";
        }else{
            binary += "0";
        }
        if(index >= 2){
            index -= 2;
            binary += "1";
        }else{
            binary += "0";
        }
        if(index >= 1){
            index -= 1;
            binary += "1";
        }else{
            binary += "0";
        }
        return binary;
    }

    public static String stringToBinary(String s){
        String cipher = "";
        for(int i = 0; i < s.length(); i++){
            cipher += toBinary(s.charAt(i));
            cipher += "-";
        }
        return cipher;
    }

    public static String unBinary(String s){
        String alph = "&abcdefghijklmnopqrstuvwxyz.,!?_";
        String c = "";
        for(int i = 0; i < s.length() / 6; i++){
            String b = s.substring(i * 6, (i * 6) + 6);
            int index = 0;
            if(b.charAt(0) == '1'){
                index += 16;
            }
            if(b.charAt(1) == '1'){
                index += 8;
            }
            if(b.charAt(2) == '1'){
                index += 4;
            }
            if(b.charAt(3) == '1'){
                index += 2;
            }
            if(b.charAt(4) == '1'){
                index += 1;
            }
            c += alph.substring(index, index + 1);
            //System.out.println(c);
        }
        return c;
    }

    //s is string post-binaryifying w/ hyphens

    public static String streamEncode(String s, String pass){
        ArrayList<Integer> arr = new ArrayList<>();
        String cipher = "";
        for(int i = 0; i < pass.length(); i++){
            Random r = new Random(Integer.parseInt(toBinary(pass.charAt(i))));
            for(int j = 0; j < s.length()/pass.length()+1; j++){
                arr.add(r.nextInt(0,2));
            }
        }
        for(int i = 0; i < s.length(); i++){
            char p = s.charAt(i);
            String c = String.valueOf(arr.get(i));
            if(p != '-'){
                if(p == '1'){
                    if(c.equals("1")){
                        cipher += "0";
                    }else{
                        cipher += "1";
                    }
                }else{
                    if(c.equals("1")){
                        cipher += "1";
                    }else{
                        cipher += "0";
                    }
                }
            }else{
                cipher += "-";
            }
        }
        return cipher;
    }

    public static String theWholeBinaryShebang(String s, String pass){
        String c = s;
        c = stringToBinary(c);
        c = streamEncode(c, pass);
        c = unBinary(c);
        return c;
    }

    // s is unbinary, pass is ponce, pass2 is pass used in binarying to make pass gibberish

    public static String vigenere(String s, String pass, String pass2){
        String alph = "&abcdefghijklmnopqrstuvwxyz.,!?_&abcdefghijklmnopqrstuvwxyz.,!?_";
        String p = theWholeBinaryShebang(pass, pass2);
        while(p.length() < s.length()){
            p += p;
        }
        String c = "";
        for(int i = 0; i < s.length(); i++){
            int is = alph.indexOf(s.substring(i, i + 1));
            int ip = alph.indexOf(p.substring(i, i + 1));
            c += alph.substring(is + ip, is + ip + 1);
        }
        return c;
    }

    public static String theGreatFiltrationDevice(String s){
        String alph = "&abcdefghijklmnopqrstuvwxyz.,!?_";
        String filtered = s.toLowerCase();
        for(int i = 0; i < filtered.length(); i++){
            if(filtered.charAt(i)==' '){
                filtered = filtered.substring(0, i) + "_" + filtered.substring(i + 1);
            }
            if(!alph.contains(filtered.substring(i, i + 1))){
                System.out.println("Hey!!!! that's a bad character!!!\nRemoving " + filtered.substring(i, i + 1) + " from text.");
                filtered = filtered.substring(0, i) + filtered.substring(i + 1);
                i--;
            }
        }
        return s;
    }

    public static String doTheWholeThingamabob(String s, String pass, String pass2){
        String str = theGreatFiltrationDevice(s);
        String password = theGreatFiltrationDevice(pass);
        String password2 = theGreatFiltrationDevice(pass2);
        String c = theWholeBinaryShebang(str, password);
        c = vigenere(c, password, password2);
        return c;
    }

    public static String backwardsVigenere(String plain, String pass, String pass2){
        String alph = "&abcdefghijklmnopqrstuvwxyz.,!?_&abcdefghijklmnopqrstuvwxyz.,!?_&abcdefghijklmnopqrstuvwxyz.,!?_";
        String p = theWholeBinaryShebang(pass, pass2);
        while(p.length() < plain.length()){
            p += p;
        }
        String c = "";
        for(int i = 0; i < plain.length(); i++){
            int iplain = alph.indexOf(plain.charAt(i));
            int ipass = alph.indexOf(p.charAt(i));
            c += alph.charAt(32 + (iplain - ipass));
        }
        return c;
    }


    public static String unDoTheWholeThingamabob(String p, String pass, String pass2){
        String str = theGreatFiltrationDevice(p);
        String password = theGreatFiltrationDevice(pass);
        String password2 = theGreatFiltrationDevice(pass2);
        String c = backwardsVigenere(str, password, password2);
        c = theWholeBinaryShebang(c, password);
        return c;
    }
}
