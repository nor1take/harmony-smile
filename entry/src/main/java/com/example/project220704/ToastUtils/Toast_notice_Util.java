package com.example.project220704.ToastUtils;

import com.example.project220704.ResourceTable;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

public class Toast_notice_Util {
    public static void showDialog(Context context, String message) {
        //xml
        DirectionalLayout dl = (DirectionalLayout) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_toast, null, false);

        //text
        Text toastMsg = (Text) dl.findComponentById(ResourceTable.Id_toastMessage);
        toastMsg.setText(message);

        //toastDialog
        ToastDialog td = new ToastDialog(context);
        td.setCornerRadius(24);
        td.setAlignment(LayoutAlignment.CENTER);
        td.setContentCustomComponent(dl);

        td.show();
    }
}
