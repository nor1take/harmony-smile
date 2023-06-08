package com.example.project220704;

import com.example.project220704.slice.mys_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class my_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(mys_AbilitySlice.class.getName());
    }
}
