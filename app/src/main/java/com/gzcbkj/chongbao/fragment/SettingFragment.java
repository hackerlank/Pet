package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.GBExecutionPool;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class SettingFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.setting);
        setViewsOnClickListener(R.id.rlPassword, R.id.rlClearCache, R.id.tvLogout);
        setText(R.id.tvCache, Utils.getCacheSize());
    }

    @Override
    public void updateUIText() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rlPassword:
                gotoPager(EditPasswordFragment.class, null);
                break;
            case R.id.rlClearCache:
                showClearCacheDialog();
                break;
            case R.id.tvLogout:
                gotoPager(LoginFragment.class, null);
                getActivity().finish();
                DataManager.getInstance().clearMyUserInfo();
                break;
        }
    }

    private void showClearCacheDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.setLayout(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                view.findViewById(R.id.tv1).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.are_you_sure_clear_cache));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.cancel));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.ok));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn2) {
                    GBExecutionPool.getExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            Glide.get(getActivity()).clearDiskCache();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast(R.string.clear_cache_suc);
                                    setText(R.id.tvCache, "0.0B");
                                }
                            });
                        }
                    });
                    Glide.get(getActivity()).clearMemory();
                }
            }
        });
        dialogFragment.show(getActivity().getSupportFragmentManager(),
                "MyDialogFragment");
    }

}
