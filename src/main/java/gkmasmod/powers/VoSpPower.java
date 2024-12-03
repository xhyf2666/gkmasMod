package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;
import gkmasmod.utils.ThreeSizeHelper;
import gkmasmod.vfx.effect.GainThreeSizeSpEffect;

import java.util.Random;

public class VoSpPower extends AbstractPower {
    private static final String CLASSNAME = VoSpPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public VoSpPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner,1), 1));
        addToBot(new ApplyPowerAction(owner, owner, new RitualPower(owner,1,false), 1));

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }


    public void onDeath() {
        if(!this.owner.isPlayer && AbstractDungeon.player.hasRelic(PocketBook.ID)){
            int score = (int) (ThreeSizeHelper.getThreeSizeAppend(1.0f,((AbstractMonster)this.owner).type));
            int change = (int) (score*ThreeSizeHelper.spRate);
            int change_ = change + (int)((ThreeSizeHelper.getVoRate()*change)+0.5F);
            AbstractDungeon.effectList.add(new GainThreeSizeSpEffect(new Random().nextInt(123456789),0,ThreeSizeHelper.getVo(),ThreeSizeHelper.getVo()+change_,this.owner.hb.cX, this.owner.hb.cY));
            ThreeSizeHelper.changeVo(change);
            if(AbstractDungeon.getMonsters().areMonstersDead())
                return;
            if(AbstractDungeon.player instanceof IdolCharacter){
                IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
                int num = idol.idolData.getSpVoiceNum(0);
                java.util.Random random = new java.util.Random();
                int index = random.nextInt(num)+1;
                SoundHelper.playSound(String.format("gkmasModResource/audio/voice/sp/%s_produce_lesson_vo_%02d.ogg",idol.idolData.idolName,index));
            }
        }
    }
}
