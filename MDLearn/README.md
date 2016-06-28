# MD风格学习
  ## statbar沉浸效果
    ### 4.4系统通过如下步骤
    <pre><code>
         //1.指定样式
         <style name="AppTheme" parent="BaseTheme">

            <item name="android:windowTranslucentStatus">true</item>
            <item name="android:windowTranslucentNavigation">true</item>
        </style>
        //2、适应system
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                      android:layout_width="match_parent"
                      android:layout_height="match_parent"
                      android:gravity="center_horizontal"
                      android:orientation="vertical">

            <android.support.v7.widget.Toolbar
                android:layout_width="match_parent"
                android:layout_height="20dip"
                android:background="?attr/colorPrimary"
                android:fitsSystemWindows="true">

            </android.support.v7.widget.Toolbar>
         </LinearLayout>
         //3、默认情况会将toolBar上移,解决上移办法:
         Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(mToolbar);
   </code></pre>

    ### 5.0.1系统(21)版本更改颜色或图片透上去的效果:
    <pre><code>
           1、设置stytle
           //公共的样式
           <style name="AppBaseTheme" parent="Theme.AppCompat.Light.NoActionBar">
                   <item name="colorPrimary">@color/colorPrimary</item>
                   <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
                   <item name="colorAccent">@color/colorAccent</item>
                   <item name="android:windowBackground">@android:color/white</item>
                   <item name="actionBarStyle">@style/ThemeOverlay.AppCompat.Dark.ActionBar</item>
                   <item name="android:actionBarStyle">@style/ThemeOverlay.AppCompat.Dark.ActionBar</item>
               </style>
           <!-以上的style和平台不相关，以下的style是v21的-->
           <style name="AppTheme" parent="AppBaseTheme">
                   <item name="android:windowDrawsSystemBarBackgrounds">true</item>
                   <item name="android:windowContentTransitions">true</item>
                   <item name="android:windowAllowEnterTransitionOverlap">true</item>
                   <item name="android:windowAllowReturnTransitionOverlap">true</item>
                   <item name="android:windowSharedElementEnterTransition">@android:transition/move</item>
                   <item name="android:windowSharedElementExitTransition">@android:transition/move</item>
               </style>

           <style name="DrawerLayoutTheme" parent="AppTheme">
                  <item name="android:statusBarColor">@android:color/transparent</item>
           </style>
          2、activity对应的xml设置:
           <android.support.v4.widget.DrawerLayout xmlns:tools="http://schemas.android.com/tools"
               xmlns:android="http://schemas.android.com/apk/res/android"
               xmlns:app="http://schemas.android.com/apk/res/com.mixiaoxiao.material.zhihudaily"
               android:id="@+id/main_drawerlayout"
               android:layout_width="match_parent"
               android:layout_height="match_parent"
               android:fitsSystemWindows="true"
               tools:context="com.mixiaoxiao.material.zhihudaily.MainActivity" >

               <!-- content -->
               <android.support.design.widget.CoordinatorLayout
                   android:id="@+id/main_coordinatorlayout"
                   android:layout_width="match_parent"
                   android:layout_height="match_parent" >

                   <!-- 省略... -->


               </android.support.design.widget.CoordinatorLayout>

               <!-- drawer -->

               <android.support.design.widget.NavigationView
                   android:layout_width="wrap_content"
                   android:layout_height="match_parent"
                   android:layout_gravity="start"
                   android:fitsSystemWindows="true"
                   app:headerLayout="@layout/drawer_header"
                   app:menu="@menu/drawer" >
               </android.support.design.widget.NavigationView>

           </android.support.v4.widget.DrawerLayout>
    </code></pre>

    ###  开源项目SystemBarTint(https://github.com/lenmoyouziJiangjun/SystemBarTint)

