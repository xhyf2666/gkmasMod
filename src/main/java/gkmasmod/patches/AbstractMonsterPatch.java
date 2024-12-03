package gkmasmod.patches;

public class AbstractMonsterPatch {

//    @SpirePatch(clz = AbstractMonster.class,method = "damage")
//    public static class PostPatchAbstractCreature_damage {
//        @SpireInsertPatch(rloc =38,localvars = {"damageAmount"})
//        public static SpireReturn<Void> Insert(AbstractMonster __instance, DamageInfo info, int damageAmount) {
////            if(__instance.isPlayer){
////                if(info.owner instanceof AbstractCharBoss){
////                    if(AbstractCharBoss.boss.hasPower(SaySomethingToYouPower.POWER_ID)){
////                        SaySomethingToYouPower power = (SaySomethingToYouPower)AbstractCharBoss.boss.getPower(SaySomethingToYouPower.POWER_ID);
////                        if(!power.breakBlock&&__instance.currentBlock>0&&damageAmount>=__instance.currentBlock){
////                            power.onBreakBlock(__instance);
////                            return SpireReturn.Return(null);
////                        }
////                    }
////                }
////            }
////            else{
//                if(AbstractDungeon.player.hasPower(SaySomethingToYouPower.POWER_ID)){
//                    SaySomethingToYouPower power = (SaySomethingToYouPower)AbstractDungeon.player.getPower(SaySomethingToYouPower.POWER_ID);
//                    if(!power.breakBlock&&__instance.currentBlock>0&&damageAmount>=__instance.currentBlock){
//                        power.onBreakBlock(__instance);
//                        return SpireReturn.Return(null);
//                    }
//                }
////            }
//            return SpireReturn.Continue();
//        }
//    }
}
