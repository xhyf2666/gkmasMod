package gkmasmod.music;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gkmasmod.music.card.amao.*;
import gkmasmod.music.card.fktn.*;
import gkmasmod.music.card.hmsz.*;
import gkmasmod.music.card.hrnm.*;
import gkmasmod.music.card.hski.*;
import gkmasmod.music.card.hume.*;
import gkmasmod.music.card.jsna.*;
import gkmasmod.music.card.kcna.*;
import gkmasmod.music.card.kllj.*;
import gkmasmod.music.card.nasr.*;
import gkmasmod.music.card.shro.*;
import gkmasmod.music.card.ssmk.*;
import gkmasmod.music.card.ttmr.*;
import gkmasmod.music.card.unit.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MusicGroup {
    public ArrayList<AbstractMusicCard> group = new ArrayList();

    public MusicGroup(){
        // 扫描 gkmasmod.music.card 包下所有 AbstractMusicCard 的子类
        group.add(new amao001());
        group.add(new amao002());
        group.add(new amao003());
        group.add(new amao004());
        group.add(new amao005());
        group.add(new amao006());
        group.add(new amao007());
        group.add(new amao008());
        group.add(new amao009());
        group.add(new amao010());
        group.add(new amao011());
        group.add(new amao012());
        group.add(new amao013());
        group.add(new amao014());
        group.add(new amao015());
        group.add(new amao016());
        group.add(new fktn001());
        group.add(new fktn002());
        group.add(new fktn003());
        group.add(new fktn004());
        group.add(new fktn005());
        group.add(new fktn006());
        group.add(new fktn007());
        group.add(new fktn008());
        group.add(new fktn009());
        group.add(new fktn010());
        group.add(new fktn011());
        group.add(new fktn012());
        group.add(new fktn013());
        group.add(new fktn014());
        group.add(new fktn015());
        group.add(new fktn016());
        group.add(new fktn023());
        group.add(new hmsz001());
        group.add(new hmsz002());
        group.add(new hmsz004());
        group.add(new hmsz005());
        group.add(new hmsz009());
        group.add(new hmsz010());
        group.add(new hmsz014());
        group.add(new hmsz015());
        group.add(new hmsz016());
        group.add(new hrnm001());
        group.add(new hrnm002());
        group.add(new hrnm003());
        group.add(new hrnm004());
        group.add(new hrnm005());
        group.add(new hrnm006());
        group.add(new hrnm007());
        group.add(new hrnm008());
        group.add(new hrnm009());
        group.add(new hrnm010());
        group.add(new hrnm011());
        group.add(new hrnm012());
        group.add(new hrnm013());
        group.add(new hrnm014());
        group.add(new hrnm015());
        group.add(new hrnm016());
        group.add(new hrnm023());
        group.add(new hski001());
        group.add(new hski002());
        group.add(new hski003());
        group.add(new hski004());
        group.add(new hski005());
        group.add(new hski006());
        group.add(new hski007());
        group.add(new hski008());
        group.add(new hski009());
        group.add(new hski010());
        group.add(new hski011());
        group.add(new hski012());
        group.add(new hski013());
        group.add(new hski014());
        group.add(new hski015());
        group.add(new hski016());
        group.add(new hume001());
        group.add(new hume002());
        group.add(new hume004());
        group.add(new hume005());
        group.add(new hume006());
        group.add(new hume007());
        group.add(new hume008());
        group.add(new hume009());
        group.add(new hume010());
        group.add(new hume014());
        group.add(new hume015());
        group.add(new hume016());
        group.add(new jsna001());
        group.add(new jsna002());
        group.add(new jsna003());
        group.add(new jsna004());
        group.add(new jsna005());
        group.add(new jsna008());
        group.add(new jsna009());
        group.add(new jsna010());
        group.add(new jsna011());
        group.add(new jsna012());
        group.add(new jsna013());
        group.add(new jsna014());
        group.add(new jsna015());
        group.add(new jsna016());
        group.add(new kcna001());
        group.add(new kcna002());
        group.add(new kcna003());
        group.add(new kcna004());
        group.add(new kcna005());
        group.add(new kcna006());
        group.add(new kcna007());
        group.add(new kcna008());
        group.add(new kcna009());
        group.add(new kcna010());
        group.add(new kcna011());
        group.add(new kcna012());
        group.add(new kcna013());
        group.add(new kcna014());
        group.add(new kcna015());
        group.add(new kcna016());
        group.add(new kcna022());
        group.add(new kllj001());
        group.add(new kllj002());
        group.add(new kllj003());
        group.add(new kllj004());
        group.add(new kllj005());
        group.add(new kllj006());
        group.add(new kllj007());
        group.add(new kllj008());
        group.add(new kllj009());
        group.add(new kllj010());
        group.add(new kllj011());
        group.add(new kllj012());
        group.add(new kllj013());
        group.add(new kllj014());
        group.add(new kllj015());
        group.add(new kllj016());
        group.add(new nasr013());
        group.add(new shro001());
        group.add(new shro002());
        group.add(new shro003());
        group.add(new shro004());
        group.add(new shro005());
        group.add(new shro006());
        group.add(new shro007());
        group.add(new shro008());
        group.add(new shro009());
        group.add(new shro010());
        group.add(new shro011());
        group.add(new shro012());
        group.add(new shro013());
        group.add(new shro014());
        group.add(new shro015());
        group.add(new shro016());
        group.add(new shro023());
        group.add(new ssmk001());
        group.add(new ssmk002());
        group.add(new ssmk003());
        group.add(new ssmk004());
        group.add(new ssmk005());
        group.add(new ssmk006());
        group.add(new ssmk007());
        group.add(new ssmk008());
        group.add(new ssmk009());
        group.add(new ssmk010());
        group.add(new ssmk011());
        group.add(new ssmk012());
        group.add(new ssmk013());
        group.add(new ssmk014());
        group.add(new ssmk015());
        group.add(new ssmk016());
        group.add(new ssmk023());
        group.add(new ttmr001());
        group.add(new ttmr002());
        group.add(new ttmr003());
        group.add(new ttmr004());
        group.add(new ttmr005());
        group.add(new ttmr006());
        group.add(new ttmr007());
        group.add(new ttmr008());
        group.add(new ttmr009());
        group.add(new ttmr010());
        group.add(new ttmr011());
        group.add(new ttmr012());
        group.add(new ttmr013());
        group.add(new ttmr014());
        group.add(new ttmr015());
        group.add(new ttmr022());
        group.add(new unit001());
        group.add(new unit002());

        System.out.println("已加载音乐卡数量: " + group.size());
    }

    public void clear() {
        this.group.clear();
    }

    public int size() {
        return this.group.size();
    }

    public void render(SpriteBatch sb) {
        Iterator var2 = this.group.iterator();

        while(var2.hasNext()) {
            AbstractMusicCard c = (AbstractMusicCard)var2.next();
            c.render(sb);
        }

    }
}
