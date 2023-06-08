package com.example.project220704.ToastUtils;

import com.example.project220704.ResourceTable;
import com.example.project220704.dbUtil.DataUtil;
import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class Toast_Center_Util {
    public static void showToast_Center(Context context, String s_title, String s_body, String s_option, int id, boolean isPost) {
        CommonDialog cd = new CommonDialog(context);

        cd.setCornerRadius(36);
        cd.setAutoClosable(true);

        cd.setAlignment(LayoutAlignment.CENTER);
        cd.setSize(800, MATCH_CONTENT);

        DirectionalLayout dl = (DirectionalLayout) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_toast_center_common, null, false);

        Text title = (Text) dl.findComponentById(ResourceTable.Id_toast_title);
        Text body = (Text) dl.findComponentById(ResourceTable.Id_toast_body);
        Button option = (Button) dl.findComponentById(ResourceTable.Id_l_option);
        Button cancel = (Button) dl.findComponentById(ResourceTable.Id_r_cancel);

        title.setText(s_title);
        body.setText(s_body);
        option.setText(s_option);
        option.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                cd.destroy();
                boolean flag;
                if (isPost) {
                    flag = DataUtil.delete_post(id);
                    if (flag) {
                        Toast_notice_Util.showDialog(context, "删除成功");
                        context.terminateAbility(); //返回主页面
                    } else {
                        Toast_notice_Util.showDialog(context, "删除失败");
                    }
                } else {
                    flag = DataUtil.delete_comment(id);
                    if (flag) {
                        //context.restart();
                        Toast_notice_Util.showDialog(context, "删除成功");
                    } else {
                        Toast_notice_Util.showDialog(context, "删除失败");
                    }
                }
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
