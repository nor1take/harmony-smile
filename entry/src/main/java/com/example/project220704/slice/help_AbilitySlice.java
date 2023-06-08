package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_Help_Item;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

public class help_AbilitySlice extends AbilitySlice {
    TabList tabList;
    TabList.Tab tab1;
    TabList.Tab tab2;
    TabList.Tab tab3;
    TabList.Tab tab4;
    TabList.Tab tab5;
    TabList.Tab tab6;
    Component component;


    Entity_Help_Item[] helpItems = {
            new Entity_Help_Item("如何发布帖子？", "点击主页面底部Tab中间的“+”即可来到发帖界面。\n\n\n" +
                    "*  发帖注意事项：\n\n" +
                    "1、帖子的标题和标签是必填项。\n\n" +
                    "2、可以自定义标签。自定义标签将收纳在“分类”中的“自定义”标签中。\n\n" +
                    "3、可以匿名发帖。\n\n\n" +
                    "发布的帖子可以在“我的”中“我的发帖”进行查看。"),
            new Entity_Help_Item("帖子的关注/删除/举报", "进入帖子详情页后，点击“关注帖子”就可以对帖子进行关注，" +
                    "以关注帖子的后续发展。\n\n" +
                    "关注的帖子可以在“我的”中“我的关注”进行查看。\n\n\n" +
                    "进入帖子详情页后，点击帖子右上方按钮对帖子进行举报或删除。"),
            new Entity_Help_Item("评论相关", "进入帖子详情页后，帖子的评论区默认折起。通过点击“n 条评论”所在的条状" +
                    "区域即可展开和收起评论区。\n\n\n" +
                    "* 回复帖子：直接在页面底部的输入框中输入并发送的，是对帖子的评论。\n\n" +
                    "* 回复评论：点击帖子下的评论主体，当输入提示变成“回复 @xxx”时，即可对该评论进行回复。\n\n\n" +
                    "发布的评论可以在“我的”中“我的评论”进行查看。\n\n" +
                    "别人对自己的帖子或评论的回复在“消息”中查看。"),
            new Entity_Help_Item("我的", "点击主页面右上角按钮进入个人界面：\n\n\n" +
                    "我的发帖：查看我的发帖。\n\n" +
                    "我的关注：查看我关注的帖子\n\n" +
                    "我的评论：查看我对帖子和对评论的回复\n\n" +
                    "使用帮助：查看使用帮助。"),
            new Entity_Help_Item("消息", "帖子回复：别人对自己发布帖子的评论\n\n" +
                    "评论回复：别人对自己评论的回复\n\n" +
                    "收到的赞：别人对自己评论的点赞"),
            new Entity_Help_Item("搜索", "点击主页面顶部输入框来到搜索界面。\n\n\n" +
                    "在搜索前，可以通过搜索界面中的“分类”对帖子进行粗略的查找。\n\n" +
                    "支持搜索标签/标题/正文的关键字词。")
    };

    void updateList(int tabNum) {
        Text title = (Text) component.findComponentById(ResourceTable.Id_help_title);
        Text body = (Text) component.findComponentById(ResourceTable.Id_help_body);
        title.setText(helpItems[tabNum - 1].getTitle());
        body.setText(helpItems[tabNum - 1].getBody());
    }

    void initTabList() {
        tabList = (TabList) findComponentById(ResourceTable.Id_help_TabList);
        tab1 = tabList.new Tab(getContext());
        tab2 = tabList.new Tab(getContext());
        tab3 = tabList.new Tab(getContext());
        tab4 = tabList.new Tab(getContext());
        tab5 = tabList.new Tab(getContext());
        tab6 = tabList.new Tab(getContext());

        tab1.setText("发帖");
        tab2.setText("帖子相关");
        tab3.setText("评论");
        tab4.setText("我的");
        tab5.setText("消息");
        tab6.setText("搜索");

        tabList.addTab(tab1);
        tabList.addTab(tab2);
        tabList.addTab(tab3);
        tabList.addTab(tab4);
        tabList.addTab(tab5);
        tabList.addTab(tab6);

        tabList.setFixedMode(true);

        tab1.select();

        ComponentContainer container = (ComponentContainer) findComponentById(ResourceTable.Id_help_Container);
        component = LayoutScatter.getInstance(this).parse(ResourceTable.Layout_help_item, null, false);
        //将list的值赋给component
        updateList(1);
        container.addComponent(component);

        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                // 当某个Tab从未选中状态变为选中状态时的回调
                if (tab == tab1) updateList(1);
                else if (tab == tab2) updateList(2);
                else if (tab == tab3) updateList(3);
                else if (tab == tab4) updateList(4);
                else if (tab == tab5) updateList(5);
                else updateList(6);

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
        super.setUIContent(ResourceTable.Layout_help);

        Component back;
        back = findComponentById(ResourceTable.Id_help_back);
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
                terminateAbility();
            }
        });

        initTabList();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
