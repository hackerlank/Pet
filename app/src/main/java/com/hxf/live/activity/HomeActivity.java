package com.hxf.live.activity;

import android.os.Bundle;
import android.view.View;

import com.hxf.live.R;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

//    private List<BaseFragment> mBaseFragment;
//    private BaseFragment mCurrentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        initFragments();
//        initViews();

//        HttpMethods.getInstance().getGinseng(new ProgressSubscriber<BasicResponse>(new SubscriberOnNextListener<ArrayList<GinsengBean>>() {
//            @Override
//            public void onNext(ArrayList<GinsengBean> list) {
//                Log.e("aaaaaaaa","size:"+list.size());
//            }
//
//        },HomeActivity.this,HomeActivity.this));

//        DatabaseOperate.getInstance().getAll(new Advertis().getTableName(), Advertis.class);
//        DatabaseOperate.getInstance().getAll(new Product().getTableName(), Product.class);
//        DatabaseOperate.getInstance().getAll(new BasicData().getTableName(), BasicData.class);
//        DatabaseOperate.getInstance().getAll(new CustomMade().getTableName(), CustomMade.class);
        // copyDB();
    }

    @Override
    public void onClick(View view) {

    }

//    private void initFragments() {
//        mBaseFragment = new ArrayList<>();
//        mBaseFragment.add(new PrivateCustomFragment());//私人定制
//        mBaseFragment.add(new BodyCustomFragment());//量身定制
//        mBaseFragment.add(new MallFragment());//商城
//        mBaseFragment.add(new MeFragment());//我的
//    }
//
//    private void initViews() {
//        findViewById(R.id.ivLeft).setVisibility(View.GONE);
//        switchFragment(mBaseFragment.get(0));
//        LinearLayout llBottom = findViewById(R.id.llBottom);
//        int count = llBottom.getChildCount();
//        View itemView;
//        for (int i = 0; i < count; ++i) {
//            itemView = llBottom.getChildAt(i);
//            itemView.setTag(i);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int tag = (int) view.getTag();
//                    if (tag == 3 && DataManager.getInstance().getMyUserInfo() == null) {
//                        gotoPager(LoginFragment.class, null);
//                        return;
//                    }
//                    switchFragment(mBaseFragment.get(tag));
//                    resetBottomBar(tag);
//                    resetTopBar(tag);
//                }
//            });
//        }
//        findViewById(R.id.ivRight).setOnClickListener(this);
//    }
//
//    private void resetBottomBar(int currentPos) {
//        LinearLayout llBottom = findViewById(R.id.llBottom);
//        int count = llBottom.getChildCount();
//        ViewGroup itemView;
//        for (int i = 0; i < count; ++i) {
//            itemView = (ViewGroup) llBottom.getChildAt(i);
//            if (i == currentPos) {
//                ((ImageView) itemView.getChildAt(0)).setImageResource(getDrawableByPos(i, true));
//                ((TextView) itemView.getChildAt(1)).setTextColor(getResources().getColor(R.color.color_ae_7c_2e));
//            } else {
//                ((ImageView) itemView.getChildAt(0)).setImageResource(getDrawableByPos(i, false));
//                ((TextView) itemView.getChildAt(1)).setTextColor(getResources().getColor(R.color.color_66_66_66));
//            }
//        }
//    }
//
//    private void resetTopBar(int currentPos) {
//        ((TextView) findViewById(R.id.tvTitle)).setText(getResources().getString(getTitleByPos(currentPos)));
//        findViewById(R.id.ivLeft).setVisibility(View.GONE);
//        findViewById(R.id.ivRight).setVisibility(View.GONE);
//        if (currentPos == 1) {
//            findViewById(R.id.ivRight).setVisibility(View.VISIBLE);
//        }
//    }
//
//    private int getTitleByPos(int pos) {
//        switch (pos) {
//            case 0:
//                return R.string.private_custom;
//            case 1:
//                return R.string.body_custom;
//            case 2:
//                return R.string.mall;
//            case 3:
//                return R.string.me;
//        }
//        return R.string.private_custom;
//    }
//
//    private int getDrawableByPos(int pos, boolean isCheck) {
//        switch (pos) {
//            case 0:
//                return isCheck ? R.drawable.private_custom_check : R.drawable.private_custom;
//            case 1:
//                return isCheck ? R.drawable.body_custom_check : R.drawable.body_custom;
//            case 2:
//                return isCheck ? R.drawable.mall_check : R.drawable.mall;
//            case 3:
//                return isCheck ? R.drawable.me_check : R.drawable.me;
//        }
//        return isCheck ? R.drawable.private_custom_check : R.drawable.private_custom;
//    }
//
//    public void onResume() {
//        super.onResume();
//        if (mCurrentFragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.show(mCurrentFragment).commit();
//        }
//    }
//
//
//    /**
//     * @param to 马上要切换到的Fragment，一会要显示
//     */
//    private void switchFragment(BaseFragment to) {
//        if (mCurrentFragment != to) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            //判断有没有被添加
//            if (!to.isAdded()) {
//                //to没有被添加
//                //from隐藏
//                if (mCurrentFragment != null) {
//                    ft.hide(mCurrentFragment);
//                }
//                //添加to
//                ft.add(R.id.fl, to).commit();
//            } else {
//                //to已经被添加
//                // from隐藏
//                if (mCurrentFragment != null) {
//                    ft.hide(mCurrentFragment);
//                }
//                //显示to
//                ft.show(to).commit();
//            }
//        }
//        mCurrentFragment = to;
//    }
//
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        switch (id) {
//            case R.id.ivRight:
//                gotoPager(HomeCustomFragment.class, null);
//                break;
//        }
//
//    }
//
//    public void copyDB() {
//        InputStream inStream = null;
//        FileOutputStream fs = null;
//        try {
//            File oldfile = new File("/data/data/" + getPackageName()
//                    + "/databases/ecsc.db");
//            if (oldfile.exists()) {
//                inStream = new FileInputStream(oldfile);
//                fs = new FileOutputStream(
//                        Environment.getExternalStorageDirectory() + "/ecsc.db");
//                byte[] buffer = new byte[2048];
//                int length = -1;
//                while ((length = inStream.read(buffer)) != -1) {
//                    fs.write(buffer, 0, length);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (inStream != null) {
//                    inStream.close();
//                }
//                if (fs != null) {
//                    fs.close();
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//    }
}
