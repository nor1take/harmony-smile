package com.example.project220704;

import com.example.project220704.slice.tab_add_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class tab_add_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(tab_add_AbilitySlice.class.getName());
    }
}
