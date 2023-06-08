package com.example.project220704;

import com.example.project220704.slice.MyFollow_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MyFollow_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MyFollow_AbilitySlice.class.getName());
    }
}
