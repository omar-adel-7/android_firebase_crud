package modules.basemvp;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import modules.general.utils.utils.LanguageUtil;


public abstract class BaseAppCompatActivity<P extends Base.IPresenter>
        extends AppCompatActivity
        implements Base.IViewAct<P> {
    private P mPresenter;




    @Override
    protected void attachBaseContext(Context newBase) {
        String lang_code = LanguageUtil.getAppLanguage(); //load it from SharedPref
        Context context = LanguageUtil.setAppLanguage(newBase, lang_code);
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getLayoutResource());
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup v = ((ViewGroup) findViewById(android.R.id.content));
        if (v != null) {
            if (getExtraLayout() > 0) {
                View extraView = layoutInflater.inflate(getExtraLayout(), null);
                ViewGroup contentView = (ViewGroup) v.findViewById(getContainerID());
                if (contentView != null)
                    contentView.addView(extraView);
                ButterKnife.bind(this, v);
            } else
                ButterKnife.bind(this);
        } else
            ButterKnife.bind(this);

        mPresenter = injectDependencies();

        if (getPresenter() == null) {
            throw new IllegalArgumentException("You must inject the " +
                    "dependencies before retrieving the presenter");
        } else {
            LanguageUtil.setAppLanguage(getApplicationContext(),  LanguageUtil.ENGLISH_LANGUAGE);
            configureUI();
        }

     }


    @Override
    protected void onDestroy() {
        super.onDestroy();
     }

    @Override
    public P getPresenter() {
        return mPresenter;
    }


}
