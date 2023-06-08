package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_Post;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.Post_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;

import java.util.Date;
import java.util.List;

public class MyPost_AbilitySlice extends AbilitySlice implements Component.ClickedListener {
    Component back;
    String userName;
    ListContainer listContainer;
    List<Entity_Post> postList;
    Post_provider itemProvider;

    List<Entity_Post> getData() {
        return DataUtil.query_my_Post(userName);
    }

    void updateList() {
        postList = getData();
        itemProvider.setList(postList);
        itemProvider.setNow(new Date());
        itemProvider.notifyDataChanged();
    }

    void setListContainer(AbilitySlice slice) {
        //找到ListContainer
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_myPost_ListContanier);
        Component notice = findComponentById(ResourceTable.Id_myPost_notice);

        //数据
        postList = getData();
        if (postList.size() > 0) {
            listContainer.setVisibility(Component.VISIBLE);
            notice.setVisibility(Component.HIDE);
        }

        //适配器对象
        itemProvider = new Post_provider(postList, slice);

        listContainer.setItemProvider(itemProvider);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
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
                //if (flag) ToastUtils.showDialog(getContext(), "view++");
                startAbility(in);
            }
        });
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_c_my_post);

        userName = intent.getStringParam("userName");

        setListContainer(this);

        back = findComponentById(ResourceTable.Id_m1_back);

        back.setClickedListener(this);
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

    @Override
    public void onClick(Component component) {
        if (component == back) {
            terminate();
            terminateAbility();
        }
    }
}
