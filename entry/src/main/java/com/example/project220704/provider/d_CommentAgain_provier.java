package com.example.project220704.provider;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_comment_again;
import com.example.project220704.timeUtil.Time_Util;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.Date;
import java.util.List;

public class d_CommentAgain_provier extends BaseItemProvider {
    List<Entity_comment_again> list;
    Context as;
    String usrName;
    Date now = new Date();

    public void setNow(Date now) {
        this.now = now;
    }

    public d_CommentAgain_provier(List<Entity_comment_again> list, Context as, String usrName) {
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

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final DirectionalLayout dl;
        d_CommentAgain_provier.ViewHolder holder;

        if (component != null) {
            dl = (DirectionalLayout) component;
            holder = (d_CommentAgain_provier.ViewHolder) dl.getTag();
        } else {
            dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_i_comment_again_item, null, false);
            holder = new d_CommentAgain_provier.ViewHolder(dl);
            dl.setTag(holder);
        }

        Entity_comment_again comment = list.get(i);

        holder.newName.setText(comment.getNewName());
        holder.oldName.setText(comment.getOldName());
        holder.commentAgainBody.setText(comment.getCommentBody());
        holder.commentAgainTime.setText(Time_Util.setTime(now, comment.getCommentTime()));

        return dl;
    }

    class ViewHolder {
        Text newName;
        Text oldName;
        Text commentAgainTime;
        Text commentAgainBody;

        public ViewHolder(Component dl) {
            newName = (Text) dl.findComponentById(ResourceTable.Id_newName);
            oldName = (Text) dl.findComponentById(ResourceTable.Id_oldName);
            commentAgainBody = (Text) dl.findComponentById(ResourceTable.Id_commentAgainBody);
            commentAgainTime = (Text) dl.findComponentById(ResourceTable.Id_commentAgainTime);
        }
    }
}
