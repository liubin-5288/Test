package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;

/**
 * Created by liubin on 2017/5/20.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    MainActivity sampleActivity;
    EditText etTitle,etContent,etPrice;
    Button btnReseale;
    LinearLayout llPrice;
    CheckBox cbFree;

    @Before
    public void setUp() throws Exception {
        sampleActivity = Robolectric.setupActivity(MainActivity.class);
        btnReseale = (Button) sampleActivity.findViewById(R.id.btn_release_news);
        etTitle = (EditText) sampleActivity.findViewById(R.id.et_news_title);
        etContent = (EditText) sampleActivity.findViewById(R.id.et_content);
        etPrice = (EditText) sampleActivity.findViewById(R.id.et_price);
        cbFree = (CheckBox) sampleActivity.findViewById(R.id.cb_free);
        llPrice = (LinearLayout) sampleActivity.findViewById(R.id.ll_price);

    }

    @Test
    public void testLifecycle() {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).create().start();
        Activity activity = activityController.get();
        activityController.resume();
        activityController.destroy();
    }

    @Test
    public void showToast() throws Exception {
        assertNotNull(sampleActivity);
        TextView textView = (TextView) sampleActivity.findViewById(R.id.btn_release_news);
        textView.performClick();
        assertEquals("呵呵", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void jumpNewList() throws Exception {
        ShadowActivity shadowActivity = Shadows.shadowOf(sampleActivity);

        TextView button = (TextView) sampleActivity.findViewById(R.id.tv_newsList);
        button.performClick();

        Intent intent = new Intent(sampleActivity, ListActivity.class);
        sampleActivity.startActivity(intent);

        assertEquals(shadowActivity.getNextStartedActivity(), intent);
    }


    @Test
    public void writeNews() throws Exception {
        assertNotNull(etContent);
        assertNotNull(etTitle);
        assertNotNull(btnReseale);
        etContent.setText("aaaaa");
        etTitle.setText("cccdddd");
        sampleActivity.writeNews();
        assertEquals(btnReseale.isEnabled(),true);
    }


    @Test
    public void freeRelease() throws Exception{
        cbFree.setChecked(false);
        sampleActivity.freeReselea();
        assertEquals(llPrice.getVisibility(), View.VISIBLE);

    }

    @Test
    public void doubleUnit() throws Exception{
        etPrice.setText("1111");
        assertEquals(sampleActivity.doubleUnit(),"1111.00");
    }


}