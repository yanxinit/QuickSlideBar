package com.yanxin.quickslidebar.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yanxin.quickslidebar.library.QuickSlideBar;
import com.yanxin.quickslidebar.library.QuickSlideBarTip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    QuickSlideBar mQuickSlideBar;
    QuickSlideBarTip mQuickSlideBarTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuickSlideBar = (QuickSlideBar) findViewById(R.id.quick_slide_bar);
        mQuickSlideBarTip = (QuickSlideBarTip) findViewById(R.id.quick_slide_bar_tip);
        List<String> letters = new ArrayList<>();
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("J");
        letters.add("K");
        letters.add("L");
        letters.add("M");
        letters.add("N");
        mQuickSlideBar.setLetters(letters);
        mQuickSlideBar.setOnSlideBarTouchListener(new QuickSlideBar.OnSlideBarTouchListener() {
            @Override
            public void onTouch(boolean touched) {
                if (touched)
                    mQuickSlideBarTip.setVisibility(View.VISIBLE);
                else
                    mQuickSlideBarTip.setVisibility(View.GONE);
            }

            @Override
            public void onLetterChanged(String currentLetter, int letterPosition) {
                mQuickSlideBarTip.setText(currentLetter);
            }
        });
    }
}
