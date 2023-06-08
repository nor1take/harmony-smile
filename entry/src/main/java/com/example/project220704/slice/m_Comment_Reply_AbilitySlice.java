package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_comment;
import com.example.project220704.db.Entity_comment_again;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.m_Comment_Reply_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;

import java.util.Date;
import java.util.List;

public class m_Comment_Reply_AbilitySlice extends AbilitySlice {
    String userName;
    ListContainer listContainer;
    List<Entity_comment_again> commentAgainList;
    m_Comment_Reply_provider itemProvider;
    Component back;

    void back() {
        back = findComponentById(ResourceTable.Id_commentReply_back);
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
                terminateAbility();
            }
        });
    }


    List<Entity_comment_again> getData() {
        return DataUtil.query_comment_reply(userName);
    }

    void updateList() {
        commentAgainList = getData();
        itemProvider.setList(commentAgainList);
        itemProvider.setNow(new Date());
        itemProvider.notifyDataChanged();
    }

    void setListContainer() {
        //找到ListContainer
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_commentReply_listContainer);

        //数据
        commentAgainList = getData();

        //适配器对象
        itemProvider = new m_Comment_Reply_provider(commentAgainList, this, userName);

        listContainer.setItemProvider(itemProvider);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                //System.out.println(component);
                Integer commentId = commentAgainList.get(i).getCommentId();
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

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_d_2_comment_reply);
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
