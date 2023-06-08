package com.example.project220704.provider;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_comment;
import com.example.project220704.db.Entity_comment_again;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.timeUtil.Time_Util;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.Date;
import java.util.List;

public class m_Comment_Reply_provider extends BaseItemProvider {
    List<Entity_comment_again> list;
    Context as;
    String usrName;
    Date now = new Date();

    public void setNow(Date now) {
        this.now = now;
    }

    public void setList(List<Entity_comment_again> list) {
        this.list = list;
    }

    public m_Comment_Reply_provider(List<Entity_comment_again> list, Context as, String usrName) {
        this.list = list;
        this.as = as;
        this.usrName = usrName;
    }

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
        m_Comment_Reply_provider.ViewHolder holder;

        if (component != null) {
            dl = (DirectionalLayout) component;
            holder = (m_Comment_Reply_provider.ViewHolder) dl.getTag();
        } else {
            dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_d_i_comment_reply_item, null, false);
            holder = new m_Comment_Reply_provider.ViewHolder(dl);
            dl.setTag(holder);
        }

        Entity_comment_again commentAgain = list.get(i);
        Integer commentId = commentAgain.getCommentId();
        Entity_comment comment = DataUtil.query_Comment(commentId);


        holder.commentTime.setText(Time_Util.setTime(now, commentAgain.getCommentTime()));
        if (!commentAgain.getNewName().equals(usrName)) {
            holder.newName.setText("@" + commentAgain.getNewName());
            holder.oldName.setText(usrName + "：");
        } else {
            holder.newName.setText("@" + commentAgain.getOldName());
            holder.notice.setText("  发布的评论下，你回复道：");
            holder.oldName.setText(commentAgain.getOldName() + "：");
        }
        holder.commentAgainBody.setText(commentAgain.getCommentBody());
        holder.commentBody.setText(comment.getCommentBody());

        return dl;
    }

    class ViewHolder {
        Text commentTime;
        Text newName;
        Text notice;
        Text commentAgainBody;
        Text oldName;
        Text commentBody;

        public ViewHolder(Component dl) {
            //tab = (Text) dl.findComponentById(ResourceTable.Id_post_tab);
            newName = (Text) dl.findComponentById(ResourceTable.Id_commentReply_newName);
            notice = (Text) dl.findComponentById(ResourceTable.Id_commentReply_notice);
            commentTime = (Text) dl.findComponentById(ResourceTable.Id_commentReply_commentTime);
            commentAgainBody = (Text) dl.findComponentById(ResourceTable.Id_commentReply_commentAgainBody);
            oldName = (Text) dl.findComponentById(ResourceTable.Id_commentReply_oldName);
            commentBody = (Text) dl.findComponentById(ResourceTable.Id_commentReply_commentBody);
        }
    }
}
