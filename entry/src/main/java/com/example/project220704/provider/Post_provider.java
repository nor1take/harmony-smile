package com.example.project220704.provider;

import com.example.project220704.ResourceTable;
import com.example.project220704.db.Entity_Post;
import com.example.project220704.timeUtil.Time_Util;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.app.Context;

import java.util.Date;
import java.util.List;

public class Post_provider extends BaseItemProvider {

    List<Entity_Post> list; //集合：所有的Item数据
    Context as; //页面：ListContainer要加载到的
    Date now = new Date();
    public void setNow(Date now) {
        this.now = now;
    }

    void setBgColor(Text tab, RgbColor color) {
        ShapeElement element = new ShapeElement();
        element.setShape(ShapeElement.RECTANGLE);
        element.setCornerRadius(18);
        element.setRgbColor(new RgbColor(color));
        tab.setBackground(element);
    }

    void setTabBg(Text tab, String s) {
        switch (s) {
            case "学习": {
                setBgColor(tab, new RgbColor(205, 225, 253));
                break;
            }
            case "生活": {
                setBgColor(tab, new RgbColor(255, 215, 237));
                break;
            }
            case "音乐": {
                setBgColor(tab, new RgbColor(255, 229, 204));
                break;
            }
            case "影视": {
                setBgColor(tab, new RgbColor(252, 219, 219));
                break;
            }
            case "问答": {
                setBgColor(tab, new RgbColor(231, 212, 255));
                break;
            }
            default:
                setBgColor(tab, new RgbColor(210, 210, 210));
                break;
        }
    }



    public Post_provider(List<Entity_Post> list, Context as) {
        this.list = list;
        this.as = as;
    }

    public List<Entity_Post> getList() {
        return list;
    }

    public void setList(List<Entity_Post> list) {
        this.list = list;
    }



    //总数据的个数
    @Override
    public int getCount() {
        return list.size();
    }

    //索引
    @Override
    public Object getItem(int i) {
        if (list != null && i >= 0 && i < list.size())
            return list.get(i);
        return null;
    }

    //返回某一项的id
    @Override
    public long getItemId(int i) {
        return i;
    }

    //返回item中要加载的布局对象
    //参数1：当前要加载哪一行item（item的索引）
    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final DirectionalLayout dl;
        ViewHolder holder;
        if (component != null) {
            dl = (DirectionalLayout) component;
            holder = (ViewHolder) dl.getTag();
        } else {
            dl = (DirectionalLayout) LayoutScatter.getInstance(as).parse(ResourceTable.Layout_i_post_item, null, false);
            holder = new ViewHolder(dl);
            dl.setTag(holder);
        }
        //getInstance(as)：括号里面是上下文：要加载的页面对象
        //null, false：独立的xml的参数

        //获取每一个item里的数据
        Entity_Post post = list.get(i);

        holder.tab.setText(post.getTab());
        setTabBg(holder.tab, post.getTab());

        if (!post.isAnonymous()) holder.poster.setText(post.getPoster());
        holder.title.setText(post.getTitle());
        if (post.getBody().length() > 0) {
            if (post.getBody().length() < 60)
                holder.body.setText(post.getBody());
            else {
                holder.body.setText(post.getBody().substring(0, 59) + "...");
            }
        } else
            holder.body.setVisibility(Component.HIDE);

        //setTime(now, post.getTime());
        holder.time.setText(Time_Util.setTime(now, post.getTime()));

//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm");
//        holder.time.setText(dateFormat.format(post.getTime()));

        holder.view.setText(post.getView() + "");
        holder.commentNum.setText(post.getCommentNum() + "");
        holder.followNum.setText(post.getFollowNum() + "");

        return dl;
    }

    public void updateItem(int i) {
        Entity_Post post = list.get(i);
        post.setView(post.getView() + 1);
        list.set(i, post);
    }

    class ViewHolder {
        Text tab;
        Text poster;
        Text title;
        Text body;
        Text time;
        Text view;
        Text commentNum;
        Text followNum;

        public ViewHolder(Component dl) {
            //把数据加载item的参数中（title/body/...)
            tab = (Text) dl.findComponentById(ResourceTable.Id_post_tab);
            poster = (Text) dl.findComponentById(ResourceTable.Id_post_poster);
            title = (Text) dl.findComponentById(ResourceTable.Id_post_title);
            body = (Text) dl.findComponentById(ResourceTable.Id_post_body);
            time = (Text) dl.findComponentById(ResourceTable.Id_post_time);
            view = (Text) dl.findComponentById(ResourceTable.Id_post_view);
            commentNum = (Text) dl.findComponentById(ResourceTable.Id_post_commentNum);
            followNum = (Text) dl.findComponentById(ResourceTable.Id_post_followNum);
        }
    }
}
