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
        SKINS.put(IdolData.amao,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolData.fktn,new ArrayList(Arrays.asList("skin10","skin11","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolData.hrnm,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolData.hski,new ArrayList(Arrays.asList("skin10","skin11","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolData.hume,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolData.kcna,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolData.kllj,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolData.shro,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33","skin34")));
        SKINS.put(IdolData.ssmk,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin33","skin34")));
        SKINS.put(IdolData.ttmr,new ArrayList(Arrays.asList("skin10","skin11","skin20","skin30","skin31","skin33","skin34")));





        SKINS.put(IdolData.jsna,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33")));
        SKINS.put(IdolData.hmsz,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33")));
        SKINS.put(IdolData.nasr,new ArrayList(Arrays.asList("skin10","skin20","skin30","skin31","skin33")));

    }

}
