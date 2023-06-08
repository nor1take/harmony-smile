package com.example.project220704;

import com.example.project220704.slice.m_Comment_Reply_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Comment_Reply_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(m_Comment_Reply_AbilitySlice.class.getName());
    }
}
