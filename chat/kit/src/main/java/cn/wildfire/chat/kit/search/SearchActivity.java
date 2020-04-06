package cn.wildfire.chat.kit.search;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.WfcBaseNoToolbarActivity;
import cn.wildfire.chat.kit.widget.SearchView;
import cn.wildfirechat.chat.R;
import cn.wildfirechat.remote.ChatManager;

/**
 * 如果启动{@link android.content.Intent}里面包含keyword，直接开始搜索
 */
public abstract class SearchActivity extends WfcBaseNoToolbarActivity {
    private SearchFragment searchFragment;
    private List<SearchableModule> modules = new ArrayList<>();

    @BindView(R.id.search_view)
    SearchView searchView;

    @OnClick(R.id.cancel)
    public void onCancelClick() {
        finish();
    }

    /**
     * 子类如果替换布局，它的布局中必须要包含 R.layout.search_bar
     *
     * @return 布局资源id
     */
    protected int contentLayout() {
        return R.layout.search_portal_activity;
    }

    protected void beforeViews() {
        setStatusBarColor(R.color.gray5);
    }

    protected void afterViews() {
        initSearchView();
        initSearchFragment();
        String initialKeyword = getIntent().getStringExtra("keyword");
        ChatManager.Instance().getMainHandler().post(() -> {
            if (!TextUtils.isEmpty(initialKeyword)) {
                searchView.setQuery(initialKeyword);
            }
        });
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(this::search);
    }

    private void initSearchFragment() {
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.containerFrameLayout, searchFragment)
            .commit();
        initSearchModule(modules);
    }

    void search(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            searchFragment.search(keyword, modules);
        } else {
            searchFragment.reset();
        }
    }

    /**
     * @param modules 是一个输出参数，用来添加希望搜索的{@link SearchableModule}
     */
    protected abstract void initSearchModule(List<SearchableModule> modules);
}
