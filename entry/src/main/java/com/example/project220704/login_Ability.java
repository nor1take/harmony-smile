package com.example.project220704;

import com.example.project220704.slice.a_login_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class login_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(a_login_AbilitySlice.class.getName());
    }
}