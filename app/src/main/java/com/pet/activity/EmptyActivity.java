package com.pet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.pet.BaseApplication;
import com.pet.R;
import com.pet.fragment.BaseFragment;

/**
 * 空的Activty，会替换你传进来的Fragment,当Activity存在时使用存在的Activity，整个程序中只有一个该Activity
 */
public class EmptyActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_empty);
        onNewIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        String fragmentName = intent.getStringExtra("FRAGMENT_NAME");
        BaseFragment fragment = (BaseFragment) Fragment.instantiate(this,
                fragmentName);
        Bundle b = intent.getExtras();
        fragment.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        BaseFragment currentFragment = getVisibleFragment();
//        if (currentFragment != null) {
//            ft.hide(currentFragment);
//        }
        ft.add(R.id.container, fragment, fragmentName);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
        BaseApplication.setCurFragment(fragment);
    }


    @Override
    protected void onFromBackground() {
        super.onFromBackground();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getVisibleFragment() == null || getVisibleFragment().getActivity() == null) {
                finish();
            } else {
                getVisibleFragment().goBack();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}

