package com.example.project220704.provider;

import com.example.project220704.ResourceTable;
import com.example.project220704.ToastUtils.Toast_Buttom_Util;
import com.example.project220704.ToastUtils.Toast_notice_Util;
import com.example.project220704.db.Entity_Like;
import com.example.project220704.db.Entity_comment;
import com.example.project220704.db.Entity_comment_again;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.timeUtil.Time_Util;
import ohos.agp.components.*;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;

import java.util.Date;
import java.util.List;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class d_Comment_provider extends BaseItemProvider {
    List<Entity_comment> list;
    Context as;
    String usrName;
    Date now = new Date();

    public void setNow(Date now) {
        this.now = now;
    }

    public void setList(List<Entity_comment> list) {
        this.list = list;
    }

    public d_Comment_provider(List<Entity_comment> list, Context as, String usrName) {
        this.list = list;
        this.as = as;
        this.usrName = usrName;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if (list != null && i >= 0 && i < list.size())
            return list.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final DirectionalLayout dl;
        d_Comment_provider.ViewHolder holder;

        dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_i_comment_item, null, false);
        holder = new d_Comment_provider.ViewHolder(dl);
//        if (component != null) {
//            dl = (DirectionalLayout) component;
//            holder = (Comment_provider.ViewHolder) dl.getTag();
//        } else {
//            dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_comment_item, null, false);
//            holder = new Comment_provider.ViewHolder(dl);
//            dl.setTag(holder);
//        }

        Context context = dl.getContext();

        Entity_comment comment = list.get(i);

        holder.commentUsrName.setText(comment.getUsrName());
        holder.commentBody.setText(comment.getCommentBody());
        holder.commentTime.setText(Time_Util.setTime(now, comment.getCommentTime()));
        holder.likeNum.setText(comment.getLikeNum() + "");
        holder.commentNum.setText(comment.getCommentNum() + "");

        holder.setIsLiker(comment.isIsLiker());
        holder.setCommentId(comment.getId());

        List<Entity_comment_again> commentAgainList = DataUtil.query_CommentAgain(comment.getId());

        //commentAgain_ListContainer：嵌套在comment内
        ListContainer listContainer = (ListContainer) dl.findComponentById(ResourceTable.Id_commentAgainList);
        d_CommentAgain_provier commentAgain_provier = new d_CommentAgain_provier(commentAgainList, context, usrName);
        listContainer.setItemProvider(commentAgain_provier);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {

            }
        });

        listContainer.setItemLongClickedListener(new ListContainer.ItemLongClickedListener() {
            @Override
            public boolean onItemLongClicked(ListContainer listContainer, Component component, int i, long l) {
                Integer commentAgainId = commentAgainList.get(i).getId();
                boolean flag = DataUtil.isSend(usrName, commentAgainId, Entity_comment_again.class);
                if (flag) {

                    CommonDialog cd = new CommonDialog(as);

                    cd.setCornerRadius(36);
                    cd.setAutoClosable(true);
                    cd.setAlignment(LayoutAlignment.BOTTOM);

                    DirectionalLayout dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_toast_buttom_common, null, false);

                    Button option = (Button) dl.findComponentById(ResourceTable.Id_option);
                    Button cancel = (Button) dl.findComponentById(ResourceTable.Id_cancel);

                    String s = "删除";
                    option.setText(s);

                    cd.setContentCustomComponent(dl);
                    cd.show();

                    option.setClickedListener(new Component.ClickedListener() {
                        @Override
                        public void onClick(Component component) {
                            if (s == "删除") {
                                cd.destroy();
                                //Toast_Center_Util.showToast_Center(as, "提示", "删除后相关评论也会删除，\n是否确定删除？", "删除", id, isPost);
                                CommonDialog cd = new CommonDialog(as);

                                String s_title = "提示";
                                String s_body = "删除后相关评论也会删除，\n是否确定删除？";
                                String s_option = "删除";

                                cd.setCornerRadius(36);
                                cd.setAutoClosable(true);

                                cd.setAlignment(LayoutAlignment.CENTER);
                                cd.setSize(800, MATCH_CONTENT);

                                DirectionalLayout dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_toast_center_common, null, false);

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
                                        flag = DataUtil.delete_comment_again(commentAgainId);
                                        if (flag) {
                                            as.restart(); //重启页面
                                            Toast_notice_Util.showDialog(as, "删除成功");
                                        } else {
                                            Toast_notice_Util.showDialog(as, "删除失败");
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
                            } else if (s == "举报")
                                Toast_notice_Util.showDialog(as, "举报");
                            else
                                Toast_notice_Util.showDialog(as, s);
                        }
                    });
                    cancel.setClickedListener(new Component.ClickedListener() {
                        @Override
                        public void onClick(Component component) {
                            cd.destroy();
                        }
                    });
                } else
                    Toast_Buttom_Util.showToast_Buttom(as, "举报", commentAgainId, false);
                return true;
            }
        });

        return dl;
    }

    class ViewHolder {
        Text commentUsrName;
        Text commentBody;
        Text commentTime;
        Text likeNum;
        Text commentNum;
        Component likeArea;
        Image likeButton;
        boolean isLiker = false;
        int commentId = 0;

        public void setIsLiker(boolean liker) {
            isLiker = liker;
            if (isLiker) {
                int argb = Color.rgb(250, 81, 81);
                Color color = new Color(argb);
                likeNum.setTextColor(color);
                likeButton.setImageAndDecodeBounds(ResourceTable.Media_ic_public_thumbsup_filled_red);
            }
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public ViewHolder(Component dl) {
            //tab = (Text) dl.findComponentById(ResourceTable.Id_post_tab);
            commentUsrName = (Text) dl.findComponentById(ResourceTable.Id_commentUsrName);
            commentBody = (Text) dl.findComponentById(ResourceTable.Id_commentBody);
            commentTime = (Text) dl.findComponentById(ResourceTable.Id_commentTime);
            likeNum = (Text) dl.findComponentById(ResourceTable.Id_likeNum);
            commentNum = (Text) dl.findComponentById(ResourceTable.Id_c_commentNum);
            likeArea = dl.findComponentById(ResourceTable.Id_likeArea);
            likeButton = (Image) dl.findComponentById(ResourceTable.Id_likeButton);

            likeArea.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    if (isLiker == false) {
                        Date date = new Date();
                        String oldName = DataUtil.query_Comment(commentId).getUsrName();
                        Entity_Like entityLike = new Entity_Like(usrName, oldName, commentId, date);
                        boolean flag = DataUtil.insert_Like(entityLike);
                        boolean flag2 = DataUtil.update_Comment_Num(commentId, "l", true);
                        if (flag && flag2) {
                            likeNum.setText((Integer.valueOf(likeNum.getText()) + 1) + "");
                            int argb = Color.rgb(250, 81, 81);
                            Color color = new Color(argb);
                            likeNum.setTextColor(color);
                            likeButton.setImageAndDecodeBounds(ResourceTable.Media_ic_public_thumbsup_filled_red);
                            isLiker = true;
                        }
                    } else {
                        boolean flag = DataUtil.delete_like(usrName, commentId);
                        boolean flag2 = DataUtil.update_Comment_Num(commentId, "l", false);
                        if (flag && flag2) {
                            likeNum.setText((Integer.valueOf(likeNum.getText()) - 1) + "");
                            int argb = Color.rgb(210, 210, 210);
                            Color color = new Color(argb);
                            likeNum.setTextColor(color);
                            likeButton.setImageAndDecodeBounds(ResourceTable.Media_ic_public_thumbsup_filled);
                            isLiker = false;
                        }
                    }
                }
            });
        }
    }
}
