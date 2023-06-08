package com.example.project220704;

import com.example.project220704.slice.help_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class help_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(help_AbilitySlice.class.getName());
    }
}
