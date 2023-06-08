package com.example.project220704.dbUtil;

import com.example.project220704.db.*;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    private static OrmContext ormContext;

    //启动数据库；初始化数据库
    public static void onInitialize(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        ormContext = helper.getOrmContext("DB", "DB.db", DB.class);
    }

    public static boolean insert_User(Entity_User user) {
        ormContext.insert(user);
        return ormContext.flush();
    }

    public static boolean insert_Post(Entity_Post post) {
        ormContext.insert(post);
        return ormContext.flush();
    }

    public static boolean insert_Comment(Entity_comment comment) {
        ormContext.insert(comment);
        return ormContext.flush();
    }

    public static boolean insert_Comment_Again(Entity_comment_again comment) {
        ormContext.insert(comment);
        return ormContext.flush();
    }

    public static boolean insert_Follow(Entity_Follow follow) {
        ormContext.insert(follow);
        return ormContext.flush();
    }

    public static boolean insert_Like(Entity_Like like) {
        ormContext.insert(like);
        return ormContext.flush();
    }

    public static boolean update_Post_Num(int postId, String s, boolean isAdd) {
        OrmPredicates predicates = ormContext.where(Entity_Post.class).equalTo("id", postId);
        List<Entity_Post> entityPostList = ormContext.query(predicates);
        Entity_Post entityPost = entityPostList.get(0);
        if (isAdd) {
            if (s.equals("v")) {
                entityPost.setView(entityPost.getView() + 1);
            } else if (s.equals("c")) {
                entityPost.setCommentNum(entityPost.getCommentNum() + 1);
            } else if (s.equals("f")) {
                entityPost.setFollowNum(entityPost.getFollowNum() + 1);
            }
        } else {
            if (s.equals("c")) {
                entityPost.setCommentNum(entityPost.getCommentNum() - 1);
            } else if (s.equals("f")) {
                entityPost.setFollowNum(entityPost.getFollowNum() - 1);
            }
        }

        ormContext.update(entityPost);
        return ormContext.flush();
    }

    public static boolean update_Comment_Num(int commentId, String s, boolean isAdd) {
        OrmPredicates predicates = ormContext.where(Entity_comment.class).equalTo("id", commentId);
        List<Entity_comment> entityCommentList = ormContext.query(predicates);
        Entity_comment comment = entityCommentList.get(0);
        if (isAdd) {
            if (s.equals("l")) {
                comment.setLikeNum(comment.getLikeNum() + 1);
            } else if (s.equals("c")) {
                comment.setCommentNum(comment.getCommentNum() + 1);
            }
        } else {
            if (s.equals("l")) {
                comment.setLikeNum(comment.getLikeNum() - 1);
            } else if (s.equals("c")) {
                comment.setCommentNum(comment.getCommentNum() - 1);
            }
        }

        ormContext.update(comment);
        return ormContext.flush();
    }

    public static boolean query_User(String usrName) {
        OrmPredicates predicates = ormContext.where(Entity_User.class).equalTo("userName", usrName);
        List<Entity_User> entitys = ormContext.query(predicates);
        return entitys.size() != 0;
    }

    public static List<Entity_Post> query_Post() {
        OrmPredicates predicates = ormContext
                .where(Entity_Post.class)
                .orderByDesc("time");
        List<Entity_Post> entitys = ormContext.query(predicates);
        return entitys;
    }

    public static List<Entity_Post> query_my_Post(String s) {
        OrmPredicates predicates = ormContext
                .where(Entity_Post.class)
                .equalTo("poster", s)
                .orderByDesc("time");
        List<Entity_Post> entitys = ormContext.query(predicates);
        return entitys;
    }

    public static List<Entity_comment> query_Comment(int postId, String usrName, int sortClass) {
        OrmPredicates predicates;
        if (sortClass == 0) {
            predicates = ormContext
                    .where(Entity_comment.class)
                    .equalTo("postId", postId);
        } else if (sortClass == 1) {
            predicates = ormContext
                    .where(Entity_comment.class)
                    .equalTo("postId", postId)
                    .orderByDesc("commentTime");
        } else {
            predicates = ormContext
                    .where(Entity_comment.class)
                    .equalTo("postId", postId)
                    .orderByDesc("likeNum");
        }

        List<Entity_comment> entityCommentList = ormContext.query(predicates);

        predicates = ormContext
                .where(Entity_Like.class)
                .equalTo("usrName", usrName);
        List<Entity_Like> entityLikeList = ormContext.query(predicates);

        for (Entity_Like i : entityLikeList) {
            for (Entity_comment j : entityCommentList) {
                if (j.getId() == i.getCommentId()) {
                    j.setIsLiker(true);
                    break;
                }
            }
        }
        return entityCommentList;
    }

    public static Entity_comment query_Comment(int commentId) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment.class)
                .equalTo("id", commentId);
        List<Entity_comment> entitys = ormContext.query(predicates);
        return entitys.get(0);
    }

    public static String query_usrNameOfComment(int commentId) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment.class)
                .equalTo("id", commentId);
        List<Entity_comment> entityCommentList = ormContext.query(predicates);
        return entityCommentList.get(0).getUsrName();

    }

    public static List<Entity_comment_again> query_CommentAgain(int commentId) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment_again.class)
                .equalTo("commentId", commentId)
                .orderByAsc("commentTime");
        List<Entity_comment_again> entityCommentList = ormContext.query(predicates);

        return entityCommentList;
    }

    public static Entity_Post query_Post(int id) {
        OrmPredicates predicates = ormContext
                .where(Entity_Post.class)
                .equalTo("id", id);
        List<Entity_Post> entitys = ormContext.query(predicates);
        return entitys.get(0);
    }

    public static boolean query_Follow(String usrName, int postId) {
        OrmPredicates predicates = ormContext
                .where(Entity_Follow.class)
                .equalTo("usrName", usrName)
                .equalTo("postId", postId);
        List<Entity_Follow> entityFollowList = ormContext.query(predicates);
        if (entityFollowList.size() == 0) return false;
        else return true;
    }

    public static List<Entity_Post> query_my_FollowPost(String usrName) {
        List<Entity_Post> postList = new ArrayList<>();
        OrmPredicates predicates = ormContext
                .where(Entity_Follow.class)
                .equalTo("usrName", usrName)
                .orderByDesc("followTime");
        List<Entity_Follow> entityFollowList = ormContext.query(predicates);
        for (Entity_Follow i : entityFollowList) {
            Integer postId = i.getPostId();
            predicates = ormContext
                    .where(Entity_Post.class)
                    .equalTo("id", postId);
            List<Entity_Post> entityPostList = ormContext.query(predicates);
            postList.add(entityPostList.get(0));
        }
        return postList;
    }

    public static List<Entity_comment> query_post_reply(String usrName) {
        List<Entity_comment> commentList = new ArrayList<>();
        OrmPredicates predicates = ormContext
                .where(Entity_Post.class)
                .equalTo("poster", usrName)
                .orderByDesc("time");
        List<Entity_Post> postList = ormContext.query(predicates);
        for (Entity_Post i : postList) {
            if (i.getCommentNum() > 0) {
                Integer postId = i.getId();
                predicates = ormContext
                        .where(Entity_comment.class)
                        .equalTo("postId", postId)
                        .notEqualTo("usrName", usrName)
                        .orderByDesc("commentTime");
                List<Entity_comment> commentList1 = ormContext.query(predicates);
                commentList.addAll(commentList1);
            }
        }
        return commentList;
    }

    public static List<Entity_comment> query_myComment_topost(String usrName) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment.class)
                .equalTo("usrName", usrName)
                .orderByDesc("commentTime");
        List<Entity_comment> commentList = ormContext.query(predicates);
        return commentList;
    }

    public static List<Entity_comment_again> query_comment_reply(String usrName) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment_again.class)
                .equalTo("oldName", usrName)
                .notEqualTo("newName", usrName)
                .orderByDesc("commentTime");
        List<Entity_comment_again> commentAgainList = ormContext.query(predicates);
        return commentAgainList;
    }

    public static List<Entity_comment_again> query_myComment_toComment(String usrName) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment_again.class)
                .equalTo("newName", usrName)
                .notEqualTo("oldName", usrName)
                .orderByDesc("commentTime");
        List<Entity_comment_again> commentAgainList = ormContext.query(predicates);
        return commentAgainList;
    }

    public static List<Entity_Like> query_receive_like(String oldName) {
        OrmPredicates predicates = ormContext
                .where(Entity_Like.class)
                .equalTo("oldName", oldName)
                .notEqualTo("usrName", oldName)
                .orderByDesc("likeTime");
        List<Entity_Like> likeList = ormContext.query(predicates);
        return likeList;
    }

    public static List<Entity_Post> query_tab_post(int tabNum) {
        OrmPredicates predicates;
        if (tabNum == 1) {
            predicates = ormContext
                    .where(Entity_Post.class)
                    .equalTo("tab", "学习")
                    .orderByDesc("time");
        } else if (tabNum == 2) {
            predicates = ormContext
                    .where(Entity_Post.class)
                    .equalTo("tab", "生活")
                    .orderByDesc("time");
        } else if (tabNum == 3) {
            predicates = ormContext
                    .where(Entity_Post.class)
                    .equalTo("tab", "音乐")
                    .orderByDesc("time");
        } else if (tabNum == 4) {
            predicates = ormContext
                    .where(Entity_Post.class)
                    .equalTo("tab", "影视")
                    .orderByDesc("time");
        } else if (tabNum == 5) {
            predicates = ormContext
                    .where(Entity_Post.class)
                    .equalTo("tab", "问答")
                    .orderByDesc("time");
        } else {
            predicates = ormContext
                    .where(Entity_Post.class)
                    .notEqualTo("tab", "学习")
                    .notEqualTo("tab", "生活")
                    .notEqualTo("tab", "音乐")
                    .notEqualTo("tab", "影视")
                    .notEqualTo("tab", "问答")
                    .orderByDesc("time");
        }
        List<Entity_Post> postList = ormContext.query(predicates);
        return postList;
    }

    public static boolean delete_comment(int commentId) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment.class)
                .equalTo("id", commentId);
        List<Entity_comment> entityCommentList = ormContext.query(predicates);
        int size = delete_comments_byCommentId(commentId); //删除该评论下的评论
        Integer postId = entityCommentList.get(0).getPostId();

        boolean flag = true;
        for (int i = 0; i < size + 1; i++) {
            flag = flag && update_Post_Num(postId, "c", false);
        }
        ormContext.delete(entityCommentList.get(0)); //删除该评论
        return ormContext.flush() && flag;
    }

    public static boolean delete_comment_again(int commentAgainId) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment_again.class)
                .equalTo("id", commentAgainId);
        List<Entity_comment_again> entityCommentAgainList = ormContext.query(predicates);
        Integer commentId = entityCommentAgainList.get(0).getCommentId();
        predicates = ormContext
                .where(Entity_comment.class)
                .equalTo("id", commentId);
        List<Entity_comment> entityCommentList = ormContext.query(predicates);
        Integer postId = entityCommentList.get(0).getPostId();
        boolean flag = update_Post_Num(postId, "c", false);
        boolean flag2 = update_Comment_Num(commentId, "c", false);
        ormContext.delete(entityCommentAgainList.get(0));
        return ormContext.flush() && flag && flag2;
    }

    public static boolean delete_follow(String usrName, int postId) {
        OrmPredicates predicates = ormContext
                .where(Entity_Follow.class)
                .equalTo("usrName", usrName)
                .equalTo("postId", postId);
        List<Entity_Follow> entityFollowList = ormContext.query(predicates);
        boolean flag = update_Post_Num(postId, "f", false);
        ormContext.delete(entityFollowList.get(0));
        return ormContext.flush() && flag;
    }

    public static boolean delete_like(String usrName, int commentId) {
        OrmPredicates predicates = ormContext
                .where(Entity_Like.class)
                .equalTo("usrName", usrName)
                .equalTo("commentId", commentId);
        List<Entity_Like> entityLikeList = ormContext.query(predicates);
        ormContext.delete(entityLikeList.get(0));
        return ormContext.flush();
    }

    public static boolean delete_comments_byPostId(int postId) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment.class)
                .equalTo("postId", postId);
        List<Entity_comment> entitys = ormContext.query(predicates);
        if (entitys.size() == 0) return true;
        for (int i = 0; i < entitys.size(); i++) {
            ormContext.delete(entitys.get(i));
        }
        return ormContext.flush();
    }

    public static int delete_comments_byCommentId(int commentId) {
        OrmPredicates predicates = ormContext
                .where(Entity_comment_again.class)
                .equalTo("commentId", commentId);
        List<Entity_comment_again> entitys = ormContext.query(predicates);
        int size = entitys.size();
        if (entitys.size() == 0) return 0;
        for (int i = 0; i < entitys.size(); i++) {
            ormContext.delete(entitys.get(i));
        }
        ormContext.flush();
        return size;
    }

    public static boolean delete_post(int postId) {
        OrmPredicates predicates = ormContext
                .where(Entity_Post.class)
                .equalTo("id", postId);
        List<Entity_Post> entitys = ormContext.query(predicates);
        ormContext.delete(entitys.get(0));
        boolean flush = ormContext.flush();
        if (delete_comments_byPostId(postId) && flush) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean delete_user(String s) {
        OrmPredicates predicates = ormContext
                .where(Entity_User.class)
                .equalTo("userName", s);
        List<Entity_User> entitys = ormContext.query(predicates);
        ormContext.delete(entitys.get(0));
        return ormContext.flush();
    }

    public static Entity_User login(String userName, String passWord) {
        OrmPredicates predicates = ormContext.where(Entity_User.class);
        predicates.equalTo("userName", userName);
        predicates.equalTo("passWord", passWord);
        List<Entity_User> entitys = ormContext.query(predicates);
        if (entitys.size() == 0)
            return null;
        else
            return entitys.get(0);
    }

    public static boolean isSend(String userName, int id, Class E) {
        OrmPredicates predicates = ormContext.where(E).equalTo("id", id);
        if (E == Entity_Post.class) {
            List<Entity_Post> entityPostList = ormContext.query(predicates);
            Entity_Post entity = entityPostList.get(0);
            if (entity.getPoster().equals(userName)) return true;
        } else if (E == Entity_comment.class) {
            List<Entity_comment> entityPostList = ormContext.query(predicates);
            Entity_comment entity = entityPostList.get(0);
            if (entity.getUsrName().equals(userName)) return true;
        } else if (E == Entity_comment_again.class) {
            List<Entity_comment_again> entityPostList = ormContext.query(predicates);
            Entity_comment_again entity = entityPostList.get(0);
            if (entity.getNewName().equals(userName)) return true;
        }
        return false;
    }

    public static List<Entity_Post> search(String input) {
        List<Entity_Post> postList;
        OrmPredicates predicates = ormContext
                .where(Entity_Post.class)
                .contains("title", input)
                .or()
                .contains("body", input)
                .or()
                .contains("tab", input)
                .orderByDesc("time");
        postList = ormContext.query(predicates);
        return postList;
    }


}
