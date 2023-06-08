package com.example.project220704.db;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {
        Entity_User.class,
        Entity_Post.class,
        Entity_comment.class,
        Entity_comment_again.class,
        Entity_Follow.class,
        Entity_Like.class
}, version = 1)
public abstract class DB extends OrmDatabase {
}
