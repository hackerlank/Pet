package com.gzcbkj.chongbao.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.manager.DataManager;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetFriendGroupFragment extends BaseFragment {

    private ArrayList<BaseFragment> mBaseFragment;
    private BaseFragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_friend_group;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewVisible(R.id.ivRight);
        setImage(R.id.ivRight, R.drawable.search);
        setViewsOnClickListener(R.id.ivRight);
        initFragments();
        initViews();
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new MessageCenterFragment());
        mBaseFragment.add(new FriendOtherFragment());
        mBaseFragment.add(new GroupFriendFragment());
    }

    private void initViews() {
        switchFragment(mBaseFragment.get(0));
        resetTopBottomBar(0);
        LinearLayout llBottom = getView().findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        View itemView;
        for (int i = 0; i < count; ++i) {
            itemView = llBottom.getChildAt(i);
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();
                    switchFragment(mBaseFragment.get(tag));
                    resetTopBottomBar(tag);
                }
            });
        }
    }

    private void resetTopBottomBar(int currentPos) {
        setText(R.id.tvTitle,getTitleId(currentPos));
        setImage(R.id.ivRight,getRightDrawableId(currentPos));
        LinearLayout llBottom = getView().findViewById(R.id.llBottom);
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
                return isCheck ? R.drawable.message_select : R.drawable.message;
            case 1:
                return isCheck ? R.drawable.friend_select : R.drawable.friend;
            case 2:
                return isCheck ? R.drawable.pet_friend : R.drawable.group;
        }
        return isCheck ? R.drawable.friend_select : R.drawable.friend;
    }

    private int getTitleId(int pos) {
        switch (pos) {
            case 0:
                return R.string.message;
            case 1:
                return R.string.friend;
            case 2:
                return R.string.group;
        }
        return R.string.friend;
    }

    private int getRightDrawableId(int pos) {
        switch (pos) {
            case 0:
                return 0;
            case 1:
                return R.drawable.search;
            case 2:
                return R.drawable.group_camera;
        }
        return R.drawable.search;
    }

    public void onResume() {
        super.onResume();
        if (mCurrentFragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.show(mCurrentFragment).commit();
        }
    }


    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(BaseFragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
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
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivRight:
                break;
        }
    }
}