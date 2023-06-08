package com.example.project220704;

import com.example.project220704.slice.detail_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class detail_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(detail_AbilitySlice.class.getName());
    }
}
