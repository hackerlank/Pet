package com.gzcbkj.chongbao.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class EditNicknameFragment extends BaseFragment {

    private int mEditType;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_nickname;
    }

    @Override
    protected void onViewCreated(View view) {
        mEditType=getArguments().getInt(Constants.KEY_BASE_BEAN,Constants.EDIT_USER_NICK);
        String nick=getArguments().getString(Constants.KEY_BASE_BEAN_2,"");
        if(mEditType==Constants.EDIT_PET_NICK){
            setText(R.id.tvTitle, R.string.pet_name);
            ((EditText) fv(R.id.etNickname)).setHint(getString(R.string.please_input_pet_nickname));
        }else {
            setText(R.id.tvTitle, R.string.edit_nickname);
        }
        setViewVisible(R.id.tvRight);
        setText(R.id.tvRight, R.string.submit);
        setViewsOnClickListener(R.id.tvRight);
        setText(R.id.etNickname,nick);
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
                if(mEditType==Constants.EDIT_PET_NICK){
                    if(nick.length()>16){
                        showToast(R.string.please_input_pet_nickname);
                        return;
                    }
                    DataManager.getInstance().setObjectType(Constants.OBJECT_TYPE_NICKNAME);
                    DataManager.getInstance().setObject(nick);
                    goBack();
                    return;
                }
                hideKeyBoard();
                HttpMethods.getInstance().updateUserName(nick,new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean bean) {
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
