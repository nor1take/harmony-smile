package com.example.project220704.provider;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_Like;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.timeUtil.Time_Util;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.Date;
import java.util.List;

public class m_Receive_Like_provider extends BaseItemProvider {
    List<Entity_Like> list;
    Context as;
    String usrName;
    Date now = new Date();

    public void setNow(Date now) {
        this.now = now;
    }

    public void setList(List<Entity_Like> list) {
        this.list = list;
    }

    public m_Receive_Like_provider(List<Entity_Like> list, Context as, String usrName) {
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
        m_Receive_Like_provider.ViewHolder holder;

        if (component != null) {
            dl = (DirectionalLayout) component;
            holder = (m_Receive_Like_provider.ViewHolder) dl.getTag();
        } else {
            dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_d_i_receive_like_item, null, false);
            holder = new m_Receive_Like_provider.ViewHolder(dl);
            dl.setTag(holder);
        }
        Entity_Like like = list.get(i);
        holder.newName.setText("@" + like.getUsrName());
        holder.oldName.setText(usrName + "：");
        holder.commentBody.setText(DataUtil.query_Comment(like.getCommentId()).getCommentBody());

        holder.likeTime.setText(Time_Util.setTime(now, like.getLikeTime()));
        return dl;
    }

    class ViewHolder {
        Text newName;
        Text oldName;
        Text commentBody;
        Text likeTime;

        public ViewHolder(Component dl) {
            //tab = (Text) dl.findComponentById(ResourceTable.Id_post_tab);
            newName = (Text) dl.findComponentById(ResourceTable.Id_receiveLike_newName);
            oldName = (Text) dl.findComponentById(ResourceTable.Id_receiveLike_oldName);
            commentBody = (Text) dl.findComponentById(ResourceTable.Id_receiveLike_commentBody);
            likeTime = (Text) dl.findComponentById(ResourceTable.Id_receiveLike_likeTime);
        }
    }
}
