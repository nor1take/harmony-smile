package com.example.project220704;

import com.example.project220704.slice.a_register_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class register_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(a_register_AbilitySlice.class.getName());
    }
}
