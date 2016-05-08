package com.example.wenhuang.puzzlegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;


public class MainActivity extends AppCompatActivity {

    protected CCGLSurfaceView _glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        _glSurfaceView = new CCGLSurfaceView(this);
        setContentView(_glSurfaceView);

        //
        CCDirector director = CCDirector.sharedDirector();
        director.attachInView(_glSurfaceView);
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft); // set orientation
        CCDirector.sharedDirector().setDisplayFPS(true);  //display fps
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);  //set frame rate


        // set background
        CCScene scene = GameLayer.scene(); //
        CCDirector.sharedDirector().runWithScene(scene);


    }

    @Override
    public void onPause()
    {
        super.onPause();
        CCDirector.sharedDirector().pause();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        CCDirector.sharedDirector().resume();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        CCDirector.sharedDirector().end();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

