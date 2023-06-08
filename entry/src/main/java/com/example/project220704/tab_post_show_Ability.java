package com.example.project220704;

import com.example.project220704.slice.tab_post_show_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class tab_post_show_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(tab_post_show_AbilitySlice.class.getName());
    }
}
