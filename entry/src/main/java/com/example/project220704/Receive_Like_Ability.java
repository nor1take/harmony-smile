package com.example.project220704;

import com.example.project220704.slice.m_Receive_Like_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Receive_Like_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(m_Receive_Like_AbilitySlice.class.getName());
    }
}
