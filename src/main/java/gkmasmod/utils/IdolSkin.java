package gkmasmod.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IdolSkin {
    public static final Map<String, ArrayList<String>> SKINS = new HashMap<>();

    static {
        // 11 初演换皮
        // 31 常驻换皮
        // 33 泳装换皮
        // 34 浴衣换皮
        //
        SKINS.put(IdolNameString.amao,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolNameString.fktn,new ArrayList(Arrays.asList("skin10","skin11","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolNameString.hrnm,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolNameString.hski,new ArrayList(Arrays.asList("skin10","skin11","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolNameString.hume,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolNameString.kcna,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolNameString.kllj,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolNameString.shro,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolNameString.ssmk,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolNameString.ttmr,new ArrayList(Arrays.asList("skin10","skin11","skin20","skin30","skin31","skin33","skin34")));





        SKINS.put(IdolNameString.jsna,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33")));
        SKINS.put(IdolNameString.hmsz,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33")));
        SKINS.put(IdolNameString.nasr,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33")));

    }

}
