package com.example.project220704.ToastUtils;

import com.example.project220704.ResourceTable;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;

public class Toast_Buttom_Util {
    public static void showToast_Buttom(Context context, String s, int id, boolean isPost) {
        CommonDialog cd = new CommonDialog(context);

        cd.setCornerRadius(36);
        cd.setAutoClosable(true);
        cd.setAlignment(LayoutAlignment.BOTTOM);

        DirectionalLayout dl = (DirectionalLayout) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_toast_buttom_common, null, false);

        Button option = (Button) dl.findComponentById(ResourceTable.Id_option);
        Button cancel = (Button) dl.findComponentById(ResourceTable.Id_cancel);

        option.setText(s);
        option.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (s == "删除") {
                    cd.destroy();
                    Toast_Center_Util.showToast_Center(context, "提示", "删除后相关评论也会删除，\n是否确定删除？", "删除", id, isPost);
                } else if (s == "举报")
                    Toast_notice_Util.showDialog(context, "举报");
                else
                    Toast_notice_Util.showDialog(context, s);
            }
        });
        cancel.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                cd.destroy();
            }
        });

        cd.setContentCustomComponent(dl);

        cd.show();
    }
}
