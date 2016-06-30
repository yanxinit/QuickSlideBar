#QuickSlideBar
右侧QuickSlideBar的高度根据Letter的个数计算，非屏幕的高度，可自行传入要显示的Letter集合。

##效果图：
![](https://github.com/yanxinit/QuickSlideBar/blob/master/Demo.png)

##使用：
```
一、布局文件
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.yanxin.quickslidebar.library.QuickSlideBar
        android:id="@+id/quick_slide_bar"
        android:layout_width="25dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        app:textColor="@android:color/black"
        app:textColorChoose="@android:color/holo_orange_light"
        app:textSize="12sp"
        app:textSizeChoose="14sp"/>

    <com.yanxin.quickslidebar.library.QuickSlideBarTip
        android:id="@+id/quick_slide_bar_tip"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_centerInParent="true"
        android:background="@android:color/holo_orange_dark"
        app:tipTextColor="@android:color/black"
        app:tipTextSize="30sp"/>

</RelativeLayout>

二、代码
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
```

##下载：
[![](https://jitpack.io/v/yanxinit/QuickSlideBar.svg)](https://jitpack.io/#yanxinit/QuickSlideBar/release-1.0.0)
