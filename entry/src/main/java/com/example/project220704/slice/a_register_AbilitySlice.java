package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.ToastUtils.Toast_notice_Util;
import com.example.project220704.db.Entity_User;
import com.example.project220704.dbUtil.DataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.InputAttribute;
import ohos.agp.components.TextField;

public class a_register_AbilitySlice extends AbilitySlice implements Component.ClickedListener {
    Component back;
    TextField userName;
    TextField passWord;
    Component regist;
    Image visible;
    boolean flag = false;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_a_register);

        back = findComponentById(ResourceTable.Id_back);
        userName = (TextField) findComponentById(ResourceTable.Id_userName);
        passWord = (TextField) findComponentById(ResourceTable.Id_passWord);
        regist = findComponentById(ResourceTable.Id_button_regist);
        visible = (Image) findComponentById(ResourceTable.Id_visible);

        back.setClickedListener(this);
        regist.setClickedListener(this);
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
        if (component == regist) {
            if (userName.getText().length() == 0)
                Toast_notice_Util.showDialog(this, "用户名不能为空");
            else if (passWord.getText().length() == 0)
                Toast_notice_Util.showDialog(this, "密码不能为空");
            else {
                if (DataUtil.query_User(userName.getText())) {
                    Toast_notice_Util.showDialog(this, "用户名已存在");
                } else {
                    Entity_User user = new Entity_User(userName.getText(), passWord.getText());
                    boolean isSuccess = DataUtil.insert_User(user);
                    if (isSuccess) {
                        Toast_notice_Util.showDialog(this, "注册成功");
                    } else {
                        Toast_notice_Util.showDialog(this, "注册失败");
                    }
                    //返回到登录界面
                    terminate();
                    terminateAbility();
                }
            }

        } else if (component == back) {
            terminate();
            terminateAbility();
        } else if (component == visible) {
            if (!flag) {
                passWord.setTextInputType(InputAttribute.PATTERN_NULL);
                visible.setImageAndDecodeBounds(ResourceTable.Media_ic_public_password_visible);
                flag = true;
            } else {
                passWord.setTextInputType(InputAttribute.PATTERN_PASSWORD);
                visible.setImageAndDecodeBounds(ResourceTable.Media_ic_public_password_invisible);
                flag = false;
            }

        }

    }
}
