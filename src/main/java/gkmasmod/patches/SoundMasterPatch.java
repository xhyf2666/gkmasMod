//package gkmasmod.patches;
//
//import com.badlogic.gdx.audio.Music;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.audio.MainMusic;
//import com.megacrit.cardcrawl.audio.Sfx;
//import com.megacrit.cardcrawl.audio.SoundMaster;
//import com.megacrit.cardcrawl.audio.TempMusic;
//import com.megacrit.cardcrawl.screens.CharSelectInfo;
//import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
//import gkmasmod.characters.IdolCharacter;
//import gkmasmod.screen.SkinSelectScreen;
//
//public class SoundMasterPatch {
//
//    @SpirePatch(clz = SoundMaster.class, method = "load",paramtypez = {String.class, boolean.class})
//    public static class SoundMasterPatch_load{
//        @SpirePrefixPatch
//        public static SpireReturn<Sfx> Prefix(SoundMaster __instance,String filename, boolean preload){
//            if (filename.startsWith("gkmasModResource")) {
//                return SpireReturn.Return(new Sfx(filename, preload));
//            }
//            return SpireReturn.Continue();
//        }
//    }
//}
