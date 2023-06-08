package com.example.project220704;

import com.example.project220704.slice.MyPost_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MyPost_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MyPost_AbilitySlice.class.getName());
    }
}
