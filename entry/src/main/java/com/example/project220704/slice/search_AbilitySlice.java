package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.ToastUtils.Toast_notice_Util;
import com.example.project220704.db.Entity_Post;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.Post_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class search_AbilitySlice extends AbilitySlice {
    ListContainer listContainer;
    ListContainer search_ListContainer;
    Post_provider itemProvider;
    Post_provider search_ListItemProvider;
    List<Entity_Post> postList;
    List<Entity_Post> search_postList;
    String userName;

    Component classFold;
    Component searchArea;
    Component classArea;
    Component back;
    Image foldNotice;
    boolean classIsFold = true;
    TextField searchInput;

    TabList tabList;
    TabList.Tab tab1;
    TabList.Tab tab2;
    TabList.Tab tab3;
    TabList.Tab tab4;
    TabList.Tab tab5;
    TabList.Tab tab6;

    void initComponent() {
        back = findComponentById(ResourceTable.Id_search_back);
        classFold = findComponentById(ResourceTable.Id_classFold);
        searchArea = findComponentById(ResourceTable.Id_searchArea);
        classArea = findComponentById(ResourceTable.Id_classArea);
        foldNotice = (Image) findComponentById(ResourceTable.Id_fold_notice);
        searchInput = (TextField) findComponentById(ResourceTable.Id_search_input);
        searchInput.setEditorActionListener(new Text.EditorActionListener() {
            @Override
            public boolean onTextEditorAction(int i) {
                if (i == 1) {
                    //点击软键盘的 “搜索”
                    searchInput.clearFocus();
                    if (searchInput.getText().length() > 0) {
                        setSearchListContainer();
                    } else {
                        Toast_notice_Util.showDialog(getContext(), "搜索内容不能为空");
                    }
                    return true;
                }
                return false;
            }
        });
        searchInput.addTextObserver(new Text.TextObserver() {//搜索条件 输入监听事件
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if (s.length() == 0) {
                    search_ListContainer = (ListContainer) findComponentById(ResourceTable.Id_searchArea);
                    search_ListItemProvider = new Post_provider(new ArrayList<>(), getAbility());
                    search_ListContainer.setItemProvider(search_ListItemProvider);
                }
            }

        });
        classFold.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (!classIsFold) {
                    searchArea.setVisibility(Component.VISIBLE);
                    classArea.setVisibility(Component.HIDE);
                    foldNotice.setImageAndDecodeBounds(ResourceTable.Media_up_black);
                    classIsFold = true;
                } else {
                    searchArea.setVisibility(Component.HIDE);
                    classArea.setVisibility(Component.VISIBLE);
                    foldNotice.setImageAndDecodeBounds(ResourceTable.Media_down_black);
                    classIsFold = false;
                }
            }
        });
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
                terminateAbility();
            }
        });
    }

    List<Entity_Post> getData(int tabNum) {
        return DataUtil.query_tab_post(tabNum);
    }

    List<Entity_Post> getData(String searchInput) {
        return DataUtil.search(searchInput);
    }

    void updateList(int tabNum) {
        postList = getData(tabNum);
        itemProvider.setList(postList);
        itemProvider.setNow(new Date());
        itemProvider.notifyDataChanged();
    }

    void update_searchList(String searchInput) {
        postList = getData(searchInput);
        search_ListItemProvider.setList(postList);
        search_ListItemProvider.notifyDataChanged();
    }

    void setListContainer() {
        //找到ListContainer
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_listContanier);

        //数据
        postList = getData(6);

        //适配器对象
        itemProvider = new Post_provider(postList, this);

        listContainer.setItemProvider(itemProvider);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                //System.out.println(component);
                Integer id = postList.get(i).getId();
                Intent in = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.detail_Ability")
                        .build();
                in.setOperation(operation);
                in.setParam("postId", id);
                in.setParam("userName", userName);
                boolean flag = DataUtil.update_Post_Num(id, "v", true);
                startAbility(in);
            }
        });
    }

    void setSearchListContainer() {
        search_ListContainer = (ListContainer) findComponentById(ResourceTable.Id_searchArea);
        search_postList = getData(searchInput.getText());

        search_ListItemProvider = new Post_provider(search_postList, this);
        search_ListContainer.setItemProvider(search_ListItemProvider);

        search_ListContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                //System.out.println(component);
                Integer id = search_postList.get(i).getId();
                Intent in = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.detail_Ability")
                        .build();
                in.setOperation(operation);
                in.setParam("postId", id);
                in.setParam("userName", userName);
                boolean flag = DataUtil.update_Post_Num(id, "v", true);
                startAbility(in);
            }
        });
    }

    void initTabList() {
        tabList = (TabList) findComponentById(ResourceTable.Id_classTabList);
        tab1 = tabList.new Tab(getContext());
        tab2 = tabList.new Tab(getContext());
        tab3 = tabList.new Tab(getContext());
        tab4 = tabList.new Tab(getContext());
        tab5 = tabList.new Tab(getContext());
        tab6 = tabList.new Tab(getContext());

        tab1.setText("自定义");
        tab2.setText("学习");
        tab3.setText("生活");
        tab4.setText("音乐");
        tab5.setText("影视");
        tab6.setText("问答");

        tabList.addTab(tab1);
        tabList.addTab(tab2);
        tabList.addTab(tab3);
        tabList.addTab(tab4);
        tabList.addTab(tab5);
        tabList.addTab(tab6);

        tabList.setFixedMode(true);

        tab1.select();

        ComponentContainer container = (ComponentContainer) findComponentById(ResourceTable.Id_classContainer);
        Component component = LayoutScatter.getInstance(this).parse(ResourceTable.Layout_pure_post_show, null, false);

        container.addComponent(component);
        setListContainer();

        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                // 当某个Tab从未选中状态变为选中状态时的回调
                if (tab == tab1) updateList(6);
                else if (tab == tab2) updateList(1);
                else if (tab == tab3) updateList(2);
                else if (tab == tab4) updateList(3);
                else if (tab == tab5) updateList(4);
                else updateList(5);
                container.removeAllComponents();
                container.addComponent(component);
            }

            @Override
            public void onUnselected(TabList.Tab tab) {
                // 当某个Tab从选中状态变为未选中状态时的回调

            }

            @Override
            public void onReselected(TabList.Tab tab) {
                // 当某个Tab已处于选中状态，再次被点击时的状态回调
            }
        });
    }


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_c_search);
        userName = intent.getStringParam("userName");

        initComponent();
        initTabList();
    }

    @Override
    public void onActive() {
        super.onActive();
        //searchInput.setInputMethodOption(InputAttribute.ENTER_KEY_TYPE_SEARCH);
        if (!classIsFold) {
            if (tabList.getSelectedTab() == tab1) updateList(6);
            else if (tabList.getSelectedTab() == tab2) updateList(1);
            else if (tabList.getSelectedTab() == tab3) updateList(2);
            else if (tabList.getSelectedTab() == tab4) updateList(3);
            else if (tabList.getSelectedTab() == tab5) updateList(4);
            else updateList(5);
        } else {
            if (searchInput.getText().length() > 0)
                update_searchList(searchInput.getText());
        }
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
