package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.ToastUtils.Toast_Buttom_Util;
import com.example.project220704.ToastUtils.Toast_notice_Util;
import com.example.project220704.db.Entity_Follow;
import com.example.project220704.db.Entity_Post;
import com.example.project220704.db.Entity_comment;
import com.example.project220704.db.Entity_comment_again;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.d_Comment_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.service.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class detail_AbilitySlice extends AbilitySlice implements Component.ClickedListener {
    Component back;
    Component postArea;

    Component commentFold;
    Text commentFoldhHint;
    Component commentArea;
    boolean c_isFold = true;
    boolean isSendForPost = true;
    int clickCommentId;

    Button send;

    Text tab;
    Text poster;
    Text title;
    Text body;
    Text time;
    Text view;
    Text commentNum;
    Text followNum;

    Image cIsFold;
    Button follow;
    boolean isFollow = false;

    int CommentListSize;

    Component more_post;

    String wholeBody;
    boolean isFold = true;

    int postId;
    String usrName;

    TextField bottomInput;

    ListContainer listContainer;
    d_Comment_provider commentProvider;
    List<Entity_comment> commentList;

    Component sortArea;
    Image sortIcon;
    Text sortText;
    int sortClass = 2;


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
        }
    }

    void initPost(int postId) {
        Entity_Post entityPost = DataUtil.query_Post(postId);
        tab = (Text) findComponentById(ResourceTable.Id_detail_tab);
        poster = (Text) findComponentById(ResourceTable.Id_detail_poster);
        title = (Text) findComponentById(ResourceTable.Id_detail_title);
        body = (Text) findComponentById(ResourceTable.Id_detail_body);
        time = (Text) findComponentById(ResourceTable.Id_detail_time);
        view = (Text) findComponentById(ResourceTable.Id_detail_view);
        commentNum = (Text) findComponentById(ResourceTable.Id_detail_commentNum);
        followNum = (Text) findComponentById(ResourceTable.Id_detail_followNum);

        commentFold = findComponentById(ResourceTable.Id_commentFold);
        commentFoldhHint = (Text) findComponentById(ResourceTable.Id_commentFoldhHint);

        bottomInput = (TextField) findComponentById(ResourceTable.Id_bottom_text_field);

        tab.setText(entityPost.getTab());
        setTabBg(tab, entityPost.getTab());

        if (!entityPost.isAnonymous()) poster.setText(entityPost.getPoster());
        title.setText(entityPost.getTitle());
        if (entityPost.getBody().length() > 0) {
            if (entityPost.getBody().length() < 60)
                body.setText(entityPost.getBody());
            else {
                wholeBody = entityPost.getBody();
                body.setText(entityPost.getBody().substring(0, 59) + "...\n[展开↓]");
            }
        } else
            body.setVisibility(Component.HIDE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm");
        time.setText(dateFormat.format(entityPost.getTime()));

        view.setText(entityPost.getView() + "");
        commentNum.setText(entityPost.getCommentNum() + "");
        commentFoldhHint.setText(entityPost.getCommentNum() + " 条评论");
        followNum.setText(entityPost.getFollowNum() + "");

        body.setClickedListener(this);
    }

    List<Entity_comment> getData() {
        return DataUtil.query_Comment(postId, usrName, sortClass);
    }

    void updateCommentList() {
        commentList = getData();
        commentProvider.setList(commentList);
        commentProvider.notifyDataChanged();
    }

    public void setListContainer(AbilitySlice slice) {
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_commentList);
        commentList = getData();
        CommentListSize = commentList.size();

        //适配器对象
        commentProvider = new d_Comment_provider(commentList, slice, usrName);

        listContainer.setItemProvider(commentProvider);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                isSendForPost = false;
                clickCommentId = commentList.get(i).getId();
                //获取焦点
                bottomInput.requestFocus();
                //弹起软键盘
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bottomInput.simulateClick();
                    }
                }, 300);
                //更改hint
                bottomInput.setHint("回复 @" + DataUtil.query_usrNameOfComment(clickCommentId));
            }
        });

        listContainer.setItemLongClickedListener(new ListContainer.ItemLongClickedListener() {
            @Override
            public boolean onItemLongClicked(ListContainer listContainer, Component component, int i, long l) {
                Integer commentId = commentList.get(i).getId();
                boolean flag = DataUtil.isSend(usrName, commentId, Entity_comment.class);
                if (flag) {
                    //Toast_Buttom_Util.showToast_Buttom(slice, "删除", commentId, false);
                    CommonDialog cd = new CommonDialog(slice);

                    cd.setCornerRadius(36);
                    cd.setAutoClosable(true);
                    cd.setAlignment(LayoutAlignment.BOTTOM);

                    DirectionalLayout dl = (DirectionalLayout) LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_toast_buttom_common, null, false);

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
                                //Toast_Center_Util.showToast_Center(slice, "提示", "删除后相关评论也会删除，\n是否确定删除？", "删除", id, isPost);
                                CommonDialog cd = new CommonDialog(slice);

                                String s_title = "提示";
                                String s_body = "删除后相关评论也会删除，\n是否确定删除？";
                                String s_option = "删除";

                                cd.setCornerRadius(36);
                                cd.setAutoClosable(true);

                                cd.setAlignment(LayoutAlignment.CENTER);
                                cd.setSize(800, MATCH_CONTENT);

                                DirectionalLayout dl = (DirectionalLayout) LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_toast_center_common, null, false);

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
                                        flag = DataUtil.delete_comment(commentId);
                                        if (flag) {
                                            setListContainer(slice);
                                            initPost(postId);
                                            Toast_notice_Util.showDialog(slice, "删除成功");
                                        } else {
                                            Toast_notice_Util.showDialog(slice, "删除失败");
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
                                Toast_notice_Util.showDialog(slice, "举报");
                            else
                                Toast_notice_Util.showDialog(slice, s);
                        }
                    });
                    cancel.setClickedListener(new Component.ClickedListener() {
                        @Override
                        public void onClick(Component component) {
                            cd.destroy();
                        }
                    });
                } else
                    Toast_Buttom_Util.showToast_Buttom(slice, "举报", commentId, false);
                return true;
            }
        });
    }

    @Override
    public void onStart(Intent intent) {
        //输入框跟随键盘的高度
        this.getWindow().setInputPanelDisplayType(WindowManager.LayoutConfig.INPUT_ADJUST_PAN);

        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_c_detail);

        postId = intent.getIntParam("postId", 0);
        usrName = intent.getStringParam("userName");

        initPost(postId);
        setListContainer(this);

        isFollow = DataUtil.query_Follow(usrName, postId);

        back = findComponentById(ResourceTable.Id_d_back);
        send = (Button) findComponentById(ResourceTable.Id_button_send);
        commentArea = findComponentById(ResourceTable.Id_commentArea);
        more_post = findComponentById(ResourceTable.Id_more_post);
        cIsFold = (Image) findComponentById(ResourceTable.Id_c_isFold);
        follow = (Button) findComponentById(ResourceTable.Id_button_follow);
        postArea = findComponentById(ResourceTable.Id_postArea);

        sortArea = findComponentById(ResourceTable.Id_sortArea);
        sortIcon = (Image) findComponentById(ResourceTable.Id_sortIcon);
        sortText = (Text) findComponentById(ResourceTable.Id_sortText);
        //未关注使用默认样式；已关注修改样式
        if (isFollow) {
            follow.setText("已关注");
            ShapeElement shapeElement = new ShapeElement();
            shapeElement.setRgbColor(new RgbColor(121, 196, 255));
            shapeElement.setShape(ShapeElement.RECTANGLE);
            shapeElement.setCornerRadius(36);
            follow.setBackground(shapeElement);
        }

        back.setClickedListener(this);
        send.setClickedListener(this);
        commentFold.setClickedListener(this);
        more_post.setClickedListener(this);
        follow.setClickedListener(this);
        postArea.setClickedListener(this);
        sortArea.setClickedListener(this);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public void onClick(Component component) {
        if (component == sortArea) {
            if (sortClass == 0) {
                Toast_notice_Util.showDialog(getContext(), "最新评论 优先");
                sortClass = 1;
                sortText.setText("按最新");
            } else if (sortClass == 1) {
                Toast_notice_Util.showDialog(getContext(), "赞数最多评论 优先");
                sortClass = 2;
                sortText.setText("按赞数");
                sortIcon.setImageAndDecodeBounds(ResourceTable.Media_ic_files_size_drive);
            } else {
                Toast_notice_Util.showDialog(getContext(), "最早评论 优先");
                sortClass = 0;
                sortText.setText("按最早");
                sortIcon.setImageAndDecodeBounds(ResourceTable.Media_ic_files_time_drive);
            }
            updateCommentList();

        } else if (component == back) {
            terminate();
            terminateAbility();
        } else if (component == postArea) {
            isSendForPost = true;
            bottomInput.setHint("在这里输入对此帖的评论");
            bottomInput.clearFocus();
        } else if (component == body) {
            if (body.getText().length() >= 60) {
                if (isFold) {
                    body.setText(wholeBody + "\n[收起↑]");
                    isFold = false;
                } else {
                    body.setText(wholeBody.substring(0, 59) + "...\n[展开↓]");
                    isFold = true;
                }
            }

        } else if (component == send) {
            if (bottomInput.getText().length() == 0) {
                Toast_notice_Util.showDialog(getContext(), "评论不能为空");
            } else {
                Date time = new Date();
                if (isSendForPost) {
                    Entity_comment comment = new Entity_comment(postId, 0, usrName, bottomInput.getText(), time, 0, 0, false);
                    boolean flag = DataUtil.insert_Comment(comment);
                    if (flag) {
                        Toast_notice_Util.showDialog(getContext(), "评论成功");
                        DataUtil.update_Post_Num(postId, "c", true);
                        bottomInput.setText("");
                        updateCommentList();//重置comment
                        initPost(postId);//重置post
                    } else
                        Toast_notice_Util.showDialog(getContext(), "评论失败");
                } else {
                    String oldName = bottomInput.getHint().substring(4);
                    Entity_comment_again entityCommentAgain = new Entity_comment_again(clickCommentId, usrName, oldName, bottomInput.getText() + "", time);
                    boolean flag = DataUtil.insert_Comment_Again(entityCommentAgain);
                    if (flag) {
                        Toast_notice_Util.showDialog(getContext(), "评论成功");
                        DataUtil.update_Post_Num(postId, "c", true);
                        DataUtil.update_Comment_Num(clickCommentId, "c", true);
                        bottomInput.setText("");
                        updateCommentList();//重置comment
                        initPost(postId);//重置post
                    } else
                        Toast_notice_Util.showDialog(getContext(), "评论失败");

                }
            }
        } else if (component == commentFold) {
            if (c_isFold) {
                commentArea.setVisibility(Component.VISIBLE);
                cIsFold.setImageAndDecodeBounds(ResourceTable.Media_down);
                c_isFold = false;
            } else {
                commentArea.setVisibility(Component.HIDE);
                cIsFold.setImageAndDecodeBounds(ResourceTable.Media_up);
                c_isFold = true;
            }
        } else if (component == more_post) {
            boolean flag = DataUtil.isSend(usrName, postId, Entity_Post.class);
            if (flag) {
                Toast_Buttom_Util.showToast_Buttom(this, "删除", postId, true);
            } else
                Toast_Buttom_Util.showToast_Buttom(this, "举报", postId, true);
        } else if (component == follow) {
            if (isFollow) {
                boolean flag = DataUtil.delete_follow(usrName, postId);
                if (flag) {
                    followNum.setText((Integer.valueOf(followNum.getText()) - 1) + "");
                    Toast_notice_Util.showDialog(this, "取消关注");
                    follow.setText("关注帖子");
                    ShapeElement shapeElement = new ShapeElement();
                    shapeElement.setRgbColor(new RgbColor(16, 174, 255));
                    shapeElement.setShape(ShapeElement.RECTANGLE);
                    shapeElement.setCornerRadius(36);
                    follow.setBackground(shapeElement);
                    isFollow = false;
                } else {
                    Toast_notice_Util.showDialog(this, "取消关注失败");
                }

            } else {
                Date time = new Date();
                Entity_Follow entityFollow = new Entity_Follow(usrName, postId, time);
                boolean flag = DataUtil.insert_Follow(entityFollow);
                boolean flag2 = DataUtil.update_Post_Num(postId, "f", true);
                if (flag && flag2) {
                    followNum.setText((Integer.valueOf(followNum.getText()) + 1) + "");
                    Toast_notice_Util.showDialog(this, "关注成功");
                    follow.setText("已关注");
                    ShapeElement shapeElement = new ShapeElement();
                    shapeElement.setRgbColor(new RgbColor(121, 196, 255));
                    shapeElement.setShape(ShapeElement.RECTANGLE);
                    shapeElement.setCornerRadius(36);
                    follow.setBackground(shapeElement);
                    isFollow = true;
                } else {
                    Toast_notice_Util.showDialog(this, "关注失败");
                }

            }

        }
    }

}
