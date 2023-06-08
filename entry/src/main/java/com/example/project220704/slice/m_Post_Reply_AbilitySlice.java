package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_comment;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.m_Post_Reply_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;

import java.util.Date;
import java.util.List;

public class m_Post_Reply_AbilitySlice extends AbilitySlice {
    String userName;
    ListContainer listContainer;
    List<Entity_comment> commentList;
    m_Post_Reply_provider itemProvider;

    Component back;

    void back() {
        back = findComponentById(ResourceTable.Id_postReply_back);
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
                terminateAbility();
            }
        });
    }

    List<Entity_comment> getData() {
        return DataUtil.query_post_reply(userName);
    }

    void updateList() {
        commentList = getData();
        itemProvider.setList(commentList);
        itemProvider.setNow(new Date());
        itemProvider.notifyDataChanged();
    }

    void setListContainer() {
        //找到ListContainer
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_postReply_listContainer);

        //数据
        commentList = getData();

        //适配器对象
        itemProvider = new m_Post_Reply_provider(commentList, this, userName);

        listContainer.setItemProvider(itemProvider);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                //System.out.println(component);
                Integer id = commentList.get(i).getPostId();
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

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_d_1_post_reply);
        userName = intent.getStringParam("userName");

        setListContainer();
        back();
    }

    @Override
    public void onActive() {
        super.onActive();
        updateList();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
