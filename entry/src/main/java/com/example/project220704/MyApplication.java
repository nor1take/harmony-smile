package com.example.project220704;

import ohos.aafwk.ability.AbilityPackage;
import com.example.project220704.dbUtil.DataUtil;

public class MyApplication extends AbilityPackage {
    @Override
    public void onInitialize() {
        super.onInitialize();
        //启动数据库
        DataUtil.onInitialize(this);
    }
}
