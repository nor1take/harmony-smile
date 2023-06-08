package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_Like;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.m_Receive_Like_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;

import java.util.Date;
import java.util.List;

public class m_Receive_Like_AbilitySlice extends AbilitySlice {
    String userName;
    ListContainer listContainer;
    List<Entity_Like> likeList;
    m_Receive_Like_provider itemProvider;
    Component back;

    void back() {
        back = findComponentById(ResourceTable.Id_receiveLike_back);
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
                terminateAbility();
            }
        });
    }

    List<Entity_Like> getData() {
        return DataUtil.query_receive_like(userName);
    }

    void updateList() {
        likeList = getData();
        itemProvider.setList(likeList);
        itemProvider.setNow(new Date());
        itemProvider.notifyDataChanged();
    }

    void setListContainer() {
        //找到ListContainer
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_receiveLike_listContainer);

        //数据
        likeList = getData();

        //适配器对象
        itemProvider = new m_Receive_Like_provider(likeList, this, userName);

        listContainer.setItemProvider(itemProvider);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                //System.out.println(component);
                Integer commentId = likeList.get(i).getCommentId();
                Integer id = DataUtil.query_Comment(commentId).getPostId();
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
        super.setUIContent(ResourceTable.Layout_d_3_receive_like);
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
