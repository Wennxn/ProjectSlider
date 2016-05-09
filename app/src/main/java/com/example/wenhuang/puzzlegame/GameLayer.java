package com.example.wenhuang.puzzlegame;

import android.content.Context;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.utils.CCFormatter;

/**
 * Created by WenHuang on 2016-05-07.
 */

public class GameLayer extends CCLayer{
    private static final int TILE_NODE_TAG = 23;  //keeps track of each tile on the scene
    private static   float TILE_SQUARE_SIZE = 3;  //the height of each of our tiles
    private static final int NUM_ROWS = 3;    //Number of rows our game would support
    private static final int NUM_COLUMNS = 3;  // Number of colums supported
    private int toppoint = 0 ;     //top cordinate from which we would start laying out our tiles
    private int topleft = 0;
    CCBitmapFontAtlas statusLabel ;    //status label
    private static CGPoint emptyPosition ;   //keeps track of the position of the empty slot on our game
    //float generalscalefactor = 0.0f ;     //a scaling factor to ensure our game looks good on diff screen sizes
    private int moves = 0 ;                //number of moves
    private Context appcontext;             //a reference to the android context variable
    public static boolean gameover = false ;    //track if the game has been solved




    private static final int STATUS_LABEL_TAG = 20;
    private static final int TIMER_LABEL_TAG = 30;
    private static final int MOVES_LABEL_TAG = 40;
    private static CGSize screenSize;
    private static float generalscalefactor;
    private static int thetime = 0;
    public GameLayer(){

        screenSize = CCDirector.sharedDirector().winSize();
        generalscalefactor = CCDirector.sharedDirector().winSize().height / 500 ;
        CCSprite background = CCSprite.sprite("background.jpg");
        background.setScale(screenSize.width / background.getContentSize().width);
        background.setAnchorPoint(CGPoint.ccp(0f,1f)) ;
        background.setPosition(CGPoint.ccp(0, screenSize.height));
        addChild(background,-5);

        // Add Game Status Label
        CCBitmapFontAtlas statusLabel = CCBitmapFontAtlas.bitmapFontAtlas ("Tap Tiles to Begin", "bionic.fnt");
        statusLabel.setScale(1.3f* generalscalefactor); //scaled
        statusLabel.setAnchorPoint(CGPoint.ccp(0,1));
        statusLabel.setPosition( CGPoint.ccp( 25* generalscalefactor , screenSize.height - 10* generalscalefactor));
        addChild(statusLabel,-2, STATUS_LABEL_TAG);

        // Add Timer Label to track time
        CCBitmapFontAtlas timerLabel = CCBitmapFontAtlas.bitmapFontAtlas ("00:00", "bionic.fnt");
        timerLabel.setScale(1.5f* generalscalefactor);
        timerLabel.setAnchorPoint(1f,1f);
        timerLabel.setColor(ccColor3B.ccc3(50, 205, 50));
        timerLabel.setPosition(CGPoint.ccp(screenSize.width - 25* generalscalefactor , screenSize.height - 10* generalscalefactor ));
        addChild(timerLabel,-2,TIMER_LABEL_TAG);

        // Add Moves Label to track number of moves
        CCBitmapFontAtlas movesLabel = CCBitmapFontAtlas.bitmapFontAtlas ("Moves : 000", "bionic.fnt");
        movesLabel.setScale(0.8f* generalscalefactor);
        movesLabel.setAnchorPoint(1f,0f);
        movesLabel.setColor(ccColor3B.ccc3(50, 205, 50));
        movesLabel.setPosition(CGPoint.ccp(screenSize.width - 25* generalscalefactor, timerLabel.getPosition().y - timerLabel.getContentSize().height* generalscalefactor - 10* generalscalefactor - timerLabel.getContentSize().height* generalscalefactor));
        addChild(movesLabel,-2,MOVES_LABEL_TAG);

        schedule("updateTimeLabel", 1.0f);
    }

    public void updateTimeLabel(float dt) {
        thetime += 1;
        String string = CCFormatter.format("%02d:%02d", (int)(thetime /60) , (int)thetime % 60 );
        CCBitmapFontAtlas timerLabel = (CCBitmapFontAtlas) getChildByTag(TIMER_LABEL_TAG) ;
        timerLabel.setString(string);
    }


    public static CCScene scene()
    {
        CCScene scene = CCScene.node();
        CCLayer layer = new GameLayer();
        scene.addChild(layer);
        return scene;
    }

    public void generateTiles(){

        //We create a Node element to hold all our tiles
        CCNode tilesNode = CCNode.node();
        tilesNode.setTag(TILE_NODE_TAG);
        addChild(tilesNode);
        float scalefactor ;   // a value we compute to help scale our tiles
        int useableheight  ;
        int tileIndex = 0 ;

        //We attempt to calculate the right size for the tiles given the screen size and
        //space left after adding the status label at the top
        int nextval ;

        int[] tileNumbers = {5,1,2,8,7,6,0,4,3};  //random but solvable sequence of numbers

        //TILE_SQUARE_SIZE = (int) ((screenSize.height  *generalscalefactor)/NUM_ROWS) ;
        int useablewidth = (int) (screenSize.width - statusLabel.getContentSize().width*generalscalefactor ) ;
        useableheight =  (int) (screenSize.height  - 40*generalscalefactor - statusLabel.getContentSize().height * 1.3f*generalscalefactor) ;

        TILE_SQUARE_SIZE = (int) Math.min((useableheight/NUM_ROWS) , (useablewidth/NUM_COLUMNS)) ;

        toppoint = (int) (useableheight  - (TILE_SQUARE_SIZE / 2) + 30*generalscalefactor)   ;
        scalefactor = TILE_SQUARE_SIZE / 150.0f ;

        topleft = (int) ((TILE_SQUARE_SIZE / 2) + 15*generalscalefactor) ;

        CCSprite tile = CCSprite.sprite("tile.png");
        //CCSprite tilebox = CCSprite.sprite("tilebox.png");

        for (int j = toppoint ; j > toppoint - (TILE_SQUARE_SIZE * NUM_ROWS); j-= TILE_SQUARE_SIZE){
            for (int i = topleft ; i < (topleft - 5*generalscalefactor) + (TILE_SQUARE_SIZE * NUM_COLUMNS); i+= TILE_SQUARE_SIZE){ 				if (tileIndex >= (NUM_ROWS * NUM_COLUMNS)) {
                break ;
            }
                nextval = tileNumbers[tileIndex ];
                CCNodeExt eachNode =  new  CCNodeExt();
                eachNode.setContentSize(tile.getContentSize());
                //
                //Layout Node based on calculated postion
                eachNode.setPosition(i, j);
                eachNode.setNodeText(nextval + "");

                //Add Tile number
                CCBitmapFontAtlas tileNumber = CCBitmapFontAtlas.bitmapFontAtlas ("00", "bionic.fnt");
                tileNumber.setScale(1.4f);

                eachNode.setScale(scalefactor);
                eachNode.addChild(tile,1,1);
                tileNumber.setString(nextval + "");
                eachNode.addChild(tileNumber,2 );

                if( nextval != 0){
                    tilesNode.addChild(eachNode,1,nextval);
                }else {
                    emptyPosition = CGPoint.ccp(i, j);
                }

                //Add each Node to a HashMap to note its location
                tileIndex++;
            }
        }
//
    }

}







