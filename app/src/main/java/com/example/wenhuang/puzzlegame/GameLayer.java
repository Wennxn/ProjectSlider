package com.example.wenhuang.puzzlegame;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * Created by WenHuang on 2016-05-07.
 */

public class GameLayer extends CCLayer{

    private static CGSize screenSize;
    private static float generalscalefactor;

    public GameLayer(){

        screenSize = CCDirector.sharedDirector().winSize();
        generalscalefactor = CCDirector.sharedDirector().winSize().height / 500 ;
        CCSprite background = CCSprite.sprite("background.jpg");
        background.setScale(screenSize.width / background.getContentSize().width);
        background.setAnchorPoint(CGPoint.ccp(0f,1f)) ;
        background.setPosition(CGPoint.ccp(0, screenSize.height));
        addChild(background,-5);

    }

    public static CCScene scene()
    {
        CCScene scene = CCScene.node();
        CCLayer layer = new GameLayer();
        scene.addChild(layer);
        return scene;
    }


}







