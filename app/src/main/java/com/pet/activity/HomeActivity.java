package com.pet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pet.R;
import com.pet.fragment.BaseFragment;
import com.pet.fragment.HomeFragment;
import com.pet.fragment.LocationRingFragment;
import com.pet.fragment.MeFragment;
import com.pet.fragment.PetCenterFragment;
import com.pet.fragment.PetFriendGroupFragment;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<BaseFragment> mBaseFragment;
    private BaseFragment mCurrentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initFragments();
        initViews();
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new HomeFragment());//首页
        mBaseFragment.add(new PetCenterFragment());//宠物中心
        mBaseFragment.add(new LocationRingFragment());//定位环
        mBaseFragment.add(new MeFragment());//我的
    }

    private void initViews() {
        switchFragment(mBaseFragment.get(0));
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        View itemView;
        for (int i = 0; i < count; ++i) {
            itemView = llBottom.getChildAt(i);
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();
                    if(tag==2){
                        gotoPager(PetFriendGroupFragment.class,null);
                        return;
                    }
//                    if (tag == 4 && DataManager.getInstance().getMyUserInfo() == null) {
//                        gotoPager(LoginFragment.class, null);
//                        return;
//                    }
                    switchFragment(mBaseFragment.get(tag>2?tag-1:tag));
                    resetBottomBar(tag);
                }
            });
        }
    }

    private void resetBottomBar(int currentPos) {
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        ViewGroup itemView;
        for (int i = 0; i < count; ++i) {
            itemView = (ViewGroup) llBottom.getChildAt(i);
            if (i == currentPos) {
                ((ImageView) itemView.getChildAt(0)).setImageResource(getDrawableByPos(i, true));
                ((TextView) itemView.getChildAt(1)).setTextColor(getResources().getColor(R.color.color_33_33_33));
            } else {
                ((ImageView) itemView.getChildAt(0)).setImageResource(getDrawableByPos(i, false));
                ((TextView) itemView.getChildAt(1)).setTextColor(getResources().getColor(R.color.color_66_66_66));
            }
        }
    }

    private int getDrawableByPos(int pos, boolean isCheck) {
        switch (pos) {
            case 0:
                return isCheck ? R.drawable.home_select : R.drawable.home_unselect;
            case 1:
                return isCheck ? R.drawable.pet_center_select : R.drawable.pet_center_unselect;
            case 2:
                return R.drawable.pet_friend;
            case 3:
                return isCheck ? R.drawable.location_ring_select: R.drawable.location_ring_unselect;
            case 4:
                return isCheck ? R.drawable.me_select : R.drawable.me_unselect;
        }
        return isCheck ? R.drawable.home_select : R.drawable.home_unselect;
    }

    public void onResume() {
        super.onResume();
        if (mCurrentFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.show(mCurrentFragment).commit();
        }
    }


    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(BaseFragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                //添加to
                ft.add(R.id.fl, to).commit();
            } else {
                //to已经被添加
                // from隐藏
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                //显示to
                ft.show(to).commit();
            }
        }
        mCurrentFragment = to;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

        }

    }
}
