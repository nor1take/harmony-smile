package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.ToastUtils.Toast_notice_Util;
import com.example.project220704.db.Entity_User;
import com.example.project220704.dbUtil.DataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.InputAttribute;
import ohos.agp.components.TextField;

public class a_login_AbilitySlice extends AbilitySlice implements Component.ClickedListener {
    Component loginButton;
    Component registerText;

    TextField userName;
    TextField passWord;

    Image visible;
    boolean flag = false;

    boolean isDrak = false;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_a_login);

        //DataUtil.delete_user("zhangran");

        loginButton = findComponentById(ResourceTable.Id_loginButton);
        registerText = findComponentById(ResourceTable.Id_registerText);
        visible = (Image) findComponentById(ResourceTable.Id_l_visible);

        userName = (TextField) findComponentById(ResourceTable.Id_l_userName);
        passWord = (TextField) findComponentById(ResourceTable.Id_l_passWord);
        loginButton.setClickedListener(this);
        registerText.setClickedListener(this);
        visible.setClickedListener(this);
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
        if (component == registerText) {
            Intent i = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withDeviceId("")
                    .withBundleName("com.example.project220704")
                    .withAbilityName("com.example.project220704.register_Ability")
                    .build();
            i.setOperation(operation);
            startAbility(i);
        } else if (component == loginButton) {
            if (userName.getText().length() == 0)
                Toast_notice_Util.showDialog(this, "用户名不能为空");
            else if (passWord.getText().length() == 0)
                Toast_notice_Util.showDialog(this, "密码不能为空");
            else {
                if (!DataUtil.query_User(userName.getText())) {
                    Toast_notice_Util.showDialog(this, "用户未注册");
                } else {
                    Entity_User user = DataUtil.login(userName.getText(), passWord.getText());
                    if (user != null) {
                        Intent i = new Intent();
                        Operation operation = new Intent.OperationBuilder()
                                .withDeviceId("")
                                .withBundleName("com.example.project220704")
                                .withAbilityName("com.example.project220704.tabs_Ability")
                                .build();
                        i.setOperation(operation);
                        i.setParam("userName", user.getUserName());
                        startAbility(i);
                    } else {
                        Toast_notice_Util.showDialog(this, "密码错误");
                    }
                }
            }
        } else if (component == visible) {
            if (!flag) {
                passWord.setTextInputType(InputAttribute.PATTERN_NULL);
                visible.setImageAndDecodeBounds(ResourceTable.Media_ic_public_password_visible);
                flag = true; //可见
            } else {
                passWord.setTextInputType(InputAttribute.PATTERN_PASSWORD);
                visible.setImageAndDecodeBounds(ResourceTable.Media_ic_public_password_invisible);
                flag = false;
            }

        }
    }
}
