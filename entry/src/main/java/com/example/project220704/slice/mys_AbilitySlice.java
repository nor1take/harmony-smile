package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Text;

public class mys_AbilitySlice extends AbilitySlice implements Component.ClickedListener {
    Component back;
    Text my_userName;
    String userName;

    Text my_post;
    Text my_follow;
    Text my_comment;
    Component help;

    void initComponent() {
        back = findComponentById(ResourceTable.Id_back);
        my_userName = (Text) findComponentById(ResourceTable.Id_my_userName);
        my_post = (Text) findComponentById(ResourceTable.Id_my_post);
        my_follow = (Text) findComponentById(ResourceTable.Id_my_follow);
        my_comment = (Text) findComponentById(ResourceTable.Id_my_comment);
        help = findComponentById(ResourceTable.Id_help);

        my_userName.setText(userName);

        back.setClickedListener(this);
        my_post.setClickedListener(this);
        my_follow.setClickedListener(this);
        my_comment.setClickedListener(this);
        help.setClickedListener(this);
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_c_my);

        userName = intent.getStringParam("userName");
        initComponent();
    }

    @Override
    public void onActive() {
        super.onActive();
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
        } else if (component == my_post || component == my_follow || component == my_comment || component == help) {
            Intent i = new Intent();
            String AbilityName = "com.example.project220704.";
            if (component == my_post) AbilityName += "MyPost_Ability";
            else if (component == my_follow) AbilityName += "MyFollow_Ability";
            else if (component == my_comment) AbilityName += "MyComment_Ability";
            else AbilityName += "help_Ability";
            Operation operation = new Intent.OperationBuilder()
                    .withDeviceId("")
                    .withBundleName("com.example.project220704")
                    .withAbilityName(AbilityName)
                    .build();
            i.setOperation(operation);
            i.setParam("userName", userName);
            startAbility(i);
        }
    }
}
