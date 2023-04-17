import com.ibm.icu.text.Transliterator;

import java.util.Scanner;

public class Storage {
    public String storage() {
        Scanner sr = new Scanner(System.in);
        String incoming = sr.nextLine();
        System.out.println(incoming);
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        String out = toLatinTrans.transliterate(incoming);
        System.out.println(out);
        return incoming;
    }



}