package com.example.project220704.slice;

import com.example.project220704.ResourceTable;
import com.example.project220704.ToastUtils.Toast_notice_Util;
import com.example.project220704.db.Entity_Post;
import com.example.project220704.dbUtil.DataUtil;
import com.example.project220704.provider.Post_provider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.global.resource.NotExistException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class tabs_AbilitySlice extends AbilitySlice implements Component.ClickedListener {
    ListContainer listContainer;
    Post_provider itemProvider;
    List<Entity_Post> postList;

    String userName;

    Component firstContent;
    Component secondContent;
    Component thirdContent;

    TabList tabList;
    TabList.Tab tab1;
    TabList.Tab tab2;
    TabList.Tab tab3;


    Button send;
    TextField title;
    TextField body;
    boolean isChecked = false;
    Text[] tab;
    String selectedTab = "学习";
    int tabNum = 6; //标签个数
    boolean isDIY = false;

    Component post_reply;
    Component comment_reply;
    Component recevie_like;

    void initMessage() {
        post_reply = findComponentById(ResourceTable.Id_post_reply);
        comment_reply = findComponentById(ResourceTable.Id_comment_reply);
        recevie_like = findComponentById(ResourceTable.Id_recevie_like);

        post_reply.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.Post_Reply_Ability")
                        .build();
                i.setOperation(operation);
                i.setParam("userName", userName);
                startAbility(i);
            }
        });
        comment_reply.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.Comment_Reply_Ability")
                        .build();
                i.setOperation(operation);
                i.setParam("userName", userName);
                startAbility(i);
            }
        });
        recevie_like.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.Receive_Like_Ability")
                        .build();
                i.setOperation(operation);
                i.setParam("userName", userName);
                startAbility(i);
            }
        });
    }

    void matchTabContent(Text tabItem) {
        String[] tabList = {"学习", "生活", "音乐", "影视", "问答", "自定义"};
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == tabItem) selectedTab = tabList[i];
        }
    }

    void setTabImage(TabList.Tab tab, int image_id) {
        try {
            tab.setIconElement(new PixelMapElement(getResourceManager().getResource(image_id)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    void menu() {
        Component goToSearch;
        Component menu;
        menu = findComponentById(ResourceTable.Id_menu);
        goToSearch = findComponentById(ResourceTable.Id_goToSearch);
        menu.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.my_Ability")
                        .build();
                i.setOperation(operation);
                i.setParam("userName", userName);
                startAbility(i);
            }
        });
        goToSearch.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.search_Ability")
                        .build();
                i.setOperation(operation);
                i.setParam("userName", userName);
                startAbility(i);
            }
        });
    }

    void send(AbilitySlice slice) {
        send = (Button) findComponentById(ResourceTable.Id_button_send);
        title = (TextField) findComponentById(ResourceTable.Id_title);
        body = (TextField) findComponentById(ResourceTable.Id_body);

        send.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                tab[5].clearFocus();
                if (title.getText().length() == 0) {
                    Toast_notice_Util.showDialog(slice, "标题不能为空");
                } else if (selectedTab.length() == 0) {
                    Toast_notice_Util.showDialog(slice, "标签不能为空");
                } else {
                    Date date = new Date();
                    Entity_Post post = new Entity_Post(userName, title.getText(), body.getText(), selectedTab, isChecked, "", date, 0, 0, 0);
                    boolean flag = DataUtil.insert_Post(post);
                    if (flag) {
                        Toast_notice_Util.showDialog(slice, "发布成功");
                        tab1.select();
                        setListContainer(slice);
                    } else
                        Toast_notice_Util.showDialog(slice, "发布失败");
                }
            }
        });
    }

    void InitTabs() {
        tab = new Text[tabNum];
        tab[0] = (Text) findComponentById(ResourceTable.Id_tab_0);
        tab[1] = (Text) findComponentById(ResourceTable.Id_tab_1);
        tab[2] = (Text) findComponentById(ResourceTable.Id_tab_2);
        tab[3] = (Text) findComponentById(ResourceTable.Id_tab_3);
        tab[4] = (Text) findComponentById(ResourceTable.Id_tab_4);
        tab[5] = (Text) findComponentById(ResourceTable.Id_tab_5);

        for (Text i : tab) {
            i.setClickedListener(this);
        }
        tab[5].setFocusChangedListener(new Component.FocusChangedListener() {
            @Override
            public void onFocusChange(Component component, boolean isFocused) {
                if (!isFocused) {
                    // 失去焦点
                    if (isDIY)
                        selectedTab = tab[5].getText();
                }
            }
        });
    }

    boolean isExist(Component c) {
        for (Text text : tab) {
            if (text == c) {
                return true;
            }
        }
        return false;
    }

    void btnSwitch() {
        Switch btnSwitch = (Switch) findComponentById(ResourceTable.Id_btn_switch);

        AbsButton.CheckedStateChangedListener listener = new AbsButton.CheckedStateChangedListener() {
            @Override
            public void onCheckedChanged(AbsButton absButton, boolean b) {
                isChecked = b;
            }
        };
        btnSwitch.setCheckedStateChangedListener(listener);
    }

    List<Entity_Post> getData() {
        return DataUtil.query_Post();
    }

    void updateList() {
        postList = getData();
        itemProvider.setList(postList);
        itemProvider.setNow(new Date());
        itemProvider.notifyDataChanged();
    }

    void setListContainer(AbilitySlice slice) {
        //找到ListContainer
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_listContanier);

        //数据
        postList = getData();

        //适配器对象
        itemProvider = new Post_provider(postList, slice);

        listContainer.setItemProvider(itemProvider);

        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                //System.out.println(component);
                Integer id = postList.get(i).getId();
                Intent in = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.project220704")
                        .withAbilityName("com.example.project220704.detail_Ability")
                        .build();
                in.setOperation(operation);
                in.setParam("postId", id);
                in.setParam("userName", userName);
                boolean flag = DataUtil.update_Post_Num(id, "v", true);
                startAbility(in);
            }
        });

        listContainer.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                // ListContainer初始化时数据统一在provider中创建，不直接调用这个接口；
                // 建议在onComponentBoundToWindow监听或者其他事件监听中调用。
                itemProvider.notifyDataChanged();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
            }
        });
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_b_0_tabs);

        //接收参数
        userName = intent.getStringParam("userName");

        tabList = (TabList) findComponentById(ResourceTable.Id_bottomTab_list);

        tab1 = tabList.new Tab(getContext());
        tab2 = tabList.new Tab(getContext());
        tab3 = tabList.new Tab(getContext());

        setTabImage(tab1, ResourceTable.Media_ic_public_notes_filled);
        setTabImage(tab2, ResourceTable.Media_ic_public_add_norm);
        setTabImage(tab3, ResourceTable.Media_ic_public_community_messages);

        tabList.addTab(tab1);
        tabList.addTab(tab2);
        tabList.addTab(tab3);

        tab1.select();

        AbilitySlice slice = this;

        ComponentContainer container = (ComponentContainer) findComponentById(ResourceTable.Id_tab_content);
        firstContent = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_b_1_tab_post_show, null, false);
        container.addComponent(firstContent);
        menu();
        setListContainer(slice);

        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                // 当某个Tab从未选中状态变为选中状态时的回调
                if (tab == tab1) {
                    setTabImage(tab1, ResourceTable.Media_ic_public_notes_filled);
                    container.removeAllComponents();
                    container.addComponent(firstContent);
                    menu();
                    updateList();
                } else if (tab == tab2) {
                    setTabImage(tab2, ResourceTable.Media_ic_public_add_norm_filled);
                    secondContent = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_b_2_tab_add, null, false);
                    container.removeAllComponents();
                    container.addComponent(secondContent);
                    send(slice);
                    InitTabs();
                    btnSwitch();
                } else {
                    setTabImage(tab3, ResourceTable.Media_ic_public_community_messages_filled);
                    thirdContent = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_b_3_tab_message, null, false);
                    container.removeAllComponents();
                    container.addComponent(thirdContent);
                    initMessage();
                }
            }

            @Override
            public void onUnselected(TabList.Tab tab) {
                // 当某个Tab从选中状态变为未选中状态时的回调
                if (tab == tab1) {
                    setTabImage(tab1, ResourceTable.Media_ic_public_notes);
                } else if (tab == tab2) {
                    setTabImage(tab2, ResourceTable.Media_ic_public_add_norm);
                } else {
                    setTabImage(tab3, ResourceTable.Media_ic_public_community_messages);
                }
            }

            @Override
            public void onReselected(TabList.Tab tab) {
                // 当某个Tab已处于选中状态，再次被点击时的状态回调
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
        if (tabList.getSelectedTab() == tab1) {
            updateList();
        }
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public void onClick(Component component) {
        if (isExist(component)) {
            if (component == tab[5]) isDIY = true;
            else {
                isDIY = false;
                tab[5].clearFocus();
                tab[5].setText("");
            }
            for (Text t : tab) {
                if (t == component) {
                    matchTabContent(t); //selectedTab
                    ShapeElement element = new ShapeElement();
                    element.setRgbColor(new RgbColor(16, 174, 255));
                    element.setCornerRadius(36);
                    t.setBackground(element);
                    t.setTextColor(Color.WHITE);
                } else {
                    ShapeElement element = new ShapeElement();
                    element.setRgbColor(new RgbColor(255, 255, 255));
                    element.setCornerRadius(36);
                    t.setBackground(element);
                    t.setTextColor(new Color(Color.rgb(172, 172, 172)));
                }
            }
        }
    }
}
