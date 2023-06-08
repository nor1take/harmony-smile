package com.example.project220704;

import com.example.project220704.slice.MyComment_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MyComment_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MyComment_AbilitySlice.class.getName());
    }
}
