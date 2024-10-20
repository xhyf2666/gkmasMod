package gkmasmod.utils;

import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.characters.IdolCharacter;

public class ConditionHelper {

    public static Condition Condition_shro;

    public static Condition Condition_kllj;

    public static Condition Condition_kcna;

    public static Condition Condition_hume;

    public static Condition Condition_hski;

    public static Condition Condition_hrnm;

    public static Condition Condition_fktn;

    public static Condition Condition_amao;

    public static Condition Condition_ssmk;

    public static Condition Condition_ttmr;

    public static Condition Condition_never;

    public ConditionHelper() {
    }

    static {
        Condition_shro = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.shro);
            }
        };

        Condition_kllj = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.kllj);
            }
        };

        Condition_kcna = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.kcna);
            }
        };

        Condition_hume = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.hume);
            }
        };

        Condition_hski = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.hski);
            }
        };

        Condition_hrnm = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.hrnm);
            }
        };

        Condition_fktn = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.fktn);
            }
        };

        Condition_amao = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.amao);
            }
        };

        Condition_ssmk = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.ssmk);
            }
        };

        Condition_ttmr = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.ttmr);
            }
        };

        Condition_ttmr = new Condition() {
            public boolean test() {
                return AbstractDungeon.player instanceof IdolCharacter
                        && ((IdolCharacter)AbstractDungeon.player).getIdolName().equals(IdolData.ttmr);
            }
        };

        Condition_never = new Condition() {
            public boolean test() {
                return false;
            }
        };
    }
}
