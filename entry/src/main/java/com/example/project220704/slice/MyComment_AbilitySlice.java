package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_comment;
import com.example.project220704.db.Entity_comment_again;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.m_Comment_Reply_provider;
import com.example.project220704.provider.m_Post_Reply_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

import java.util.Date;
import java.util.List;

public class MyComment_AbilitySlice extends AbilitySlice {
    TabList tabList;
    TabList.Tab tab1;
    TabList.Tab tab2;

    String userName;
    ListContainer listContainer_toPost;
    List<Entity_comment> commentList_toPost;
    m_Post_Reply_provider itemProvider_toPost;

    ListContainer listContainer_toComment;
    List<Entity_comment_again> commentAgainList_toComment;
    m_Comment_Reply_provider itemProvider_toComment;

    List<Entity_comment> getData_toPost() {
        return DataUtil.query_myComment_topost(userName);
    }

    void updateList_toPost() {
        commentList_toPost = getData_toPost();
        itemProvider_toPost.setList(commentList_toPost);
        itemProvider_toPost.setNow(new Date());
        itemProvider_toPost.notifyDataChanged();
    }

    void setListContainer_ToPost() {
        listContainer_toPost = (ListContainer) findComponentById(ResourceTable.Id_myComment_ToPost);

        //数据
        commentList_toPost = getData_toPost();

        //适配器对象
        itemProvider_toPost = new m_Post_Reply_provider(commentList_toPost, this, userName);

        listContainer_toPost.setItemProvider(itemProvider_toPost);

        listContainer_toPost.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer_toPost, Component component, int i, long l) {
                //System.out.println(component);
                Integer id = commentList_toPost.get(i).getPostId();
                Intent in = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.detail_Ability")
                        .build();
                in.setOperation(operation);
                in.setParam("postId", id);
                in.setParam("userName", userName);
                DataUtil.update_Post_Num(id, "v", true);
                startAbility(in);
            }
        });

    }

    List<Entity_comment_again> getData_toComment() {
        return DataUtil.query_myComment_toComment(userName);
    }

    void updateList_toComment() {
        commentAgainList_toComment = getData_toComment();
        itemProvider_toComment.setList(commentAgainList_toComment);
        itemProvider_toComment.setNow(new Date());
        itemProvider_toComment.notifyDataChanged();
    }

    void setListContainer_toComment() {
        //找到ListContainer
        listContainer_toComment = (ListContainer) findComponentById(ResourceTable.Id_myComment_ToComment);

        //数据
        commentAgainList_toComment = getData_toComment();

        //适配器对象
        itemProvider_toComment = new m_Comment_Reply_provider(commentAgainList_toComment, this, userName);

        listContainer_toComment.setItemProvider(itemProvider_toComment);

        listContainer_toComment.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer_toComment, Component component, int i, long l) {
                //System.out.println(component);
                Integer commentId = commentAgainList_toComment.get(i).getCommentId();
                Entity_comment comment = DataUtil.query_Comment(commentId);
                Integer id = comment.getPostId();
                Intent in = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.detail_Ability")
                        .build();
                in.setOperation(operation);
                in.setParam("postId", id);
                in.setParam("userName", userName);
                DataUtil.update_Post_Num(id, "v", true);
                startAbility(in);
            }
        });
    }

    void initTabList() {
        tabList = (TabList) findComponentById(ResourceTable.Id_myComment_tabList);
        tab1 = tabList.new Tab(getContext());
        tab2 = tabList.new Tab(getContext());

        tab1.setText("对帖子");
        tab2.setText("对评论");

        tabList.addTab(tab1);
        tabList.addTab(tab2);

        tabList.setFixedMode(true);

        tab1.select();

        ComponentContainer container = (ComponentContainer) findComponentById(ResourceTable.Id_myComment_Container);
        Component component_commentToPost = LayoutScatter.getInstance(this).parse(ResourceTable.Layout_c_my_comment_topost, null, false);
        Component component_commentToComment = LayoutScatter.getInstance(this).parse(ResourceTable.Layout_c_my_comment_tocomment, null, false);

        container.addComponent(component_commentToPost);
        setListContainer_ToPost();

        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                // 当某个Tab从未选中状态变为选中状态时的回调
                if (tab == tab1) {
                    container.removeAllComponents();
                    container.addComponent(component_commentToPost);
                    setListContainer_ToPost();
                } else {
                    container.removeAllComponents();
                    container.addComponent(component_commentToComment);
                    setListContainer_toComment();
                }
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
        super.setUIContent(ResourceTable.Layout_c_my_comment);
        userName = intent.getStringParam("userName");

        initTabList();
        Component back;
        back = findComponentById(ResourceTable.Id_m3_back);
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
                terminateAbility();
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
        if (tabList.getSelectedTab() == tab1) updateList_toPost();
        else updateList_toComment();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
