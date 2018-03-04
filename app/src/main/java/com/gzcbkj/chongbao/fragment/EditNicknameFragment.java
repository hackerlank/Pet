package com.gzcbkj.chongbao.fragment;

import android.text.TextUtils;
import android.view.View;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class EditNicknameFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_nickname;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.edit_nickname);
        setViewVisible(R.id.tvRight);
        setText(R.id.tvRight,R.string.submit);
        setViewsOnClickListener(R.id.tvRight);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvRight:
                final String nick=getTextById(R.id.etNickname);
                if(TextUtils.isEmpty(nick)){
                    showToast(R.string.please_input_nickname_2);
                    return;
                }
                hideKeyBoard();
                HttpMethods.getInstance().updateUserName(nick,new ProgressSubscriber(new SubscriberOnNextListener<BaseBean>() {
                    @Override
                    public void onNext(BaseBean bean) {
                        if(!TextUtils.isEmpty(bean.getMsg())){
                            showToast(bean.getMsg());
                        }
                        UserInfoBean userInfoBean= DataManager.getInstance().getMyUserInfo();
                        userInfoBean.setUsername(nick);
                        DataManager.getInstance().saveMyUserInfo(userInfoBean);
                        goBack();
                    }
                },getActivity(),(BaseActivity) getActivity()));
                break;
        }
    }
}
