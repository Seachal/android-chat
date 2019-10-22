package cn.wildfire.chat.kit.search;

import java.util.List;

import cn.wildfire.chat.kit.search.module.ChannelSearchModule;
import cn.wildfire.chat.kit.search.module.ContactSearchModule;
import cn.wildfire.chat.kit.search.module.ConversationSearchModule;
import cn.wildfire.chat.kit.search.module.GroupSearchViewModule;


/**
 * sca: Portal 入口
 */
public class SearchPortalActivity extends SearchActivity {


     /**
     * sca: 配置当前想进行的搜索项
     * @param modules 是一个输出参数，用来添加希望搜索的{@link SearchableModule}
     */
    @Override
    protected void initSearchModule(List<SearchableModule> modules) {

        SearchableModule module = new ContactSearchModule();
        modules.add(module);

        module = new GroupSearchViewModule();
        modules.add(module);

        module = new ConversationSearchModule();
        modules.add(module);
        modules.add(new ChannelSearchModule());
    }
}
