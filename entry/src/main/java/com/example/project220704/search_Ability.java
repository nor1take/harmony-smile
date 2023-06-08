package com.example.project220704;

import com.example.project220704.slice.search_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class search_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(search_AbilitySlice.class.getName());
    }
}
