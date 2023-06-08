package com.example.project220704.provider;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_Post;
import com.example.project220704.db.Entity_comment;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.timeUtil.Time_Util;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.Date;
import java.util.List;

public class m_Post_Reply_provider extends BaseItemProvider {
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

    public m_Post_Reply_provider(List<Entity_comment> list, Context as, String usrName) {
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
        m_Post_Reply_provider.ViewHolder holder;

        if (component != null) {
            dl = (DirectionalLayout) component;
            holder = (m_Post_Reply_provider.ViewHolder) dl.getTag();
        } else {
            dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_d_i_post_reply_item, null, false);
            holder = new m_Post_Reply_provider.ViewHolder(dl);
            dl.setTag(holder);
        }

        Entity_comment comment = list.get(i);
        Integer postId = comment.getPostId();
        Entity_Post post = DataUtil.query_Post(postId);


        holder.commentTime.setText(Time_Util.setTime(now, comment.getCommentTime()));
        if (!comment.getUsrName().equals(usrName))
            holder.newName.setText("@" + comment.getUsrName());
        else {
            if(!post.isAnonymous())
                holder.newName.setText("@" + DataUtil.query_Post(comment.getPostId()).getPoster());
            else holder.newName.setText("#匿名用户");
            holder.notice.setText("  发布的帖子下，你评论道：");
        }
        holder.commentBody.setText(comment.getCommentBody());
        holder.postTitle.setText(post.getTitle());
        holder.postView.setText(post.getView() + "");
        holder.postFollowNum.setText(post.getFollowNum() + "");
        holder.postCommentNum.setText(post.getCommentNum() + "");

        return dl;
    }

    class ViewHolder {
        Text commentTime;
        Text newName;
        Text notice;
        Text commentBody;
        Text postTitle;
        Text postView;
        Text postCommentNum;
        Text postFollowNum;

        public ViewHolder(Component dl) {
            //tab = (Text) dl.findComponentById(ResourceTable.Id_post_tab);
            commentTime = (Text) dl.findComponentById(ResourceTable.Id_postReply_commentTime);
            newName = (Text) dl.findComponentById(ResourceTable.Id_postReply_newName);
            notice = (Text) dl.findComponentById(ResourceTable.Id_postReply_notice);
            commentBody = (Text) dl.findComponentById(ResourceTable.Id_postReply_commentBody);
            postTitle = (Text) dl.findComponentById(ResourceTable.Id_postReply_postTitle);
            postView = (Text) dl.findComponentById(ResourceTable.Id_postReply_post_view);
            postCommentNum = (Text) dl.findComponentById(ResourceTable.Id_postReply_post_commentNum);
            postFollowNum = (Text) dl.findComponentById(ResourceTable.Id_postReply_post_followNum);

        }
    }
}
