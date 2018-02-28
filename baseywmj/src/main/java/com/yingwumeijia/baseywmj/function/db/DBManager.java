package com.yingwumeijia.baseywmj.function.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orhanobut.logger.Logger;
import com.yingwumeijia.baseywmj.function.message.MessageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jam on 16/10/19 下午5:03.
 * Describe:
 */

public class DBManager {

    private Context mContext;

    private MySQLiteOpenHelper mySQLiteOpenHelper;

    private SQLiteDatabase mDb;

    public static final int DB_VERSATION = 2;


    /*数据库名称*/
    public static final String DB_NAME = "conversation.db";
    /*第一次会话记录表*/
    public static final String TB_IM_FIRST_CONVERSATION = "TableFirstConversation";
    /*主键id*/
    public static final String FIELD_PRIMARY_ID = "_id";
    /*用户的im id*/
    public static final String FIELD_IMU_ID = "imu_id";
    /*用户id*/
    public static final String FIELD_USER_ID = "user_id";
    /*会话id*/
    public static final String FIELD_CONVERSATION_ID = "conversation_id";
    /*是否是第一次会话*/
    public static final String FIELD_FIRET_CONVERSATION = "is_first";


    public static final String TB_SYSTEM_MESSAGE = "TableSystemMessage";

    public static final String FIELD_MESSAGE_TYPE = "message_type";

    public static final String FIELD_MESSAGE_ID = "message_id";

    public static final String FIELD_MESSAGE_TITLE = "message_title";

    public static final String FIELD_MESSAGE_CONTENT = "message_content";

    public static final String FIELD_MESSAGE_TARGET_ID = "message_target_id";

    public static final String FIELD_MESSAGE_SEND_DATE = "message_send_date";

    public static final String FIELD_MESSAGE_SEND_ID = "message_send_id";

    public static final String FIELD_MESSAGE_USER_ID = "message_user_id";


    public DBManager(Context context) {
        this.mContext = context;
        mySQLiteOpenHelper = new MySQLiteOpenHelper(mContext);
    }

    public SQLiteDatabase getSQLiteConnect() {
        return mySQLiteOpenHelper.getWritableDatabase();
    }

    public static DBManager getInstnace(Context context) {
        return new DBManager(context);
    }

    public static String CREATE_TB_FIRST_CALL() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table ");
        sql.append(TB_IM_FIRST_CONVERSATION);
        sql.append(" (");
        sql.append(FIELD_PRIMARY_ID + " text primary key, ");
        sql.append(FIELD_IMU_ID + " text, ");
        sql.append(FIELD_USER_ID + " text, ");
        sql.append(FIELD_CONVERSATION_ID + " text, ");
        sql.append(FIELD_FIRET_CONVERSATION + " integer");
        sql.append(");");
        return sql.toString();
    }

    public static String CREATE_TB_MESSAGE() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table ");
        sql.append(TB_SYSTEM_MESSAGE);
        sql.append(" (");
        sql.append(FIELD_PRIMARY_ID + " text primary key, ");
        sql.append(FIELD_MESSAGE_TYPE + " integer, ");
        sql.append(FIELD_MESSAGE_ID + " integer, ");
        sql.append(FIELD_MESSAGE_TITLE + " text, ");
        sql.append(FIELD_MESSAGE_CONTENT + " text, ");
        sql.append(FIELD_MESSAGE_TARGET_ID + " text, ");
        sql.append(FIELD_MESSAGE_SEND_DATE + " text, ");
        sql.append(FIELD_MESSAGE_SEND_ID + " text");
        sql.append(FIELD_MESSAGE_USER_ID + " text");
        sql.append(");");
        return sql.toString();
    }


    public void insertForFIRST_CALL(FirstConversationBean firstConversationBean) {
        mDb = getSQLiteConnect();
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        ContentValues values = new ContentValues();
        values.put(FIELD_PRIMARY_ID, str);
        values.put(FIELD_IMU_ID, firstConversationBean.getImu_id());
        values.put(FIELD_USER_ID, firstConversationBean.getUser_id());
        values.put(FIELD_CONVERSATION_ID, firstConversationBean.getConversation_id());
        values.put(FIELD_FIRET_CONVERSATION, firstConversationBean.isFirst());
        mDb.insert(TB_IM_FIRST_CONVERSATION, null, values);
        mDb.close();
    }


    public boolean isFirstConversation(String conversationId, String imuId) {
        boolean isFirst = false;
        FirstConversationBean firstConversationBean = null;
        mDb = getSQLiteConnect();
        Cursor cursor = mDb.rawQuery("select * from " + TB_IM_FIRST_CONVERSATION + " where " + FIELD_CONVERSATION_ID + " like ? and " + FIELD_IMU_ID + " like ?", new String[]{conversationId, imuId});

        if (cursor.getCount() == 0) {
            isFirst = true;
        }
        cursor.close();
        mDb.close();
        return isFirst;
    }

    private void deletedFirstConversation(String conversationId, String imuId) {
        mDb = getSQLiteConnect();
        mDb.delete(TB_IM_FIRST_CONVERSATION, FIELD_CONVERSATION_ID + " like ? and " + FIELD_IMU_ID + " like ?", new String[]{conversationId, imuId});
        mDb.close();
    }

    public static class FirstConversationBean {

        String _id;
        String imu_id;
        String user_id;
        String conversation_id;
        int isFirst;

        public FirstConversationBean(String _id, String imu_id, String user_id, String conversation_id, int isFirst) {
            this._id = _id;
            this.imu_id = imu_id;
            this.user_id = user_id;
            this.conversation_id = conversation_id;
            this.isFirst = isFirst;
        }


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getImu_id() {
            return imu_id;
        }

        public void setImu_id(String imu_id) {
            this.imu_id = imu_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getConversation_id() {
            return conversation_id;
        }

        public void setConversation_id(String conversation_id) {
            this.conversation_id = conversation_id;
        }

        public int isFirst() {
            return isFirst;
        }

        public void setFirst(int first) {
            isFirst = first;
        }
    }


    /**
     * 查询本地消息列表
     *
     * @return
     */
    public List<MessageBean> getMessageBean(String userId) {
        List<MessageBean> messageBeanList = new ArrayList<>();
        mDb = getSQLiteConnect();
        if (mDb.isOpen()) {
            Cursor cursor = mDb.rawQuery("select * from " + TB_SYSTEM_MESSAGE, null);
            while (cursor.moveToNext()) {
                MessageBean messageBean = new MessageBean();
                messageBean.setMessageId(cursor.getInt(cursor.getColumnIndex(FIELD_MESSAGE_ID)));
                messageBean.setMessageType(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE_TYPE)));
                messageBean.setMessageTitle(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE_TITLE)));
                messageBean.setMessageContetnt(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE_CONTENT)));
                messageBean.setMessageTargetId(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE_TARGET_ID)));
                messageBean.setMessageSendDate(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE_SEND_DATE)));
                messageBean.setMessageSendId(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE_SEND_ID)));
                messageBean.setMessageUserId(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE_USER_ID)));
                if (userId.equals(messageBean.getMessageUserId()))
                    messageBeanList.add(messageBean);
            }
            cursor.close();
            mDb.close();

        }

        Logger.d(messageBeanList);

        return messageBeanList;
    }

    /**
     * 插入一条消息到数据库里面
     *
     * @param messageBean
     */
    public void insertMessage(MessageBean messageBean) {


        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        mDb = getSQLiteConnect();
        ContentValues valuse = new ContentValues();
        valuse.put(FIELD_PRIMARY_ID, str);
        valuse.put(FIELD_MESSAGE_ID, messageBean.getMessageId());
        valuse.put(FIELD_MESSAGE_TYPE, messageBean.getMessageType());
        valuse.put(FIELD_MESSAGE_TITLE, messageBean.getMessageTitle());
        valuse.put(FIELD_MESSAGE_CONTENT, messageBean.getMessageContetnt());
        valuse.put(FIELD_MESSAGE_TARGET_ID, messageBean.getMessageTargetId());
        valuse.put(FIELD_MESSAGE_SEND_DATE, messageBean.getMessageSendDate());
        valuse.put(FIELD_MESSAGE_SEND_ID, messageBean.getMessageSendId());
        valuse.put(FIELD_MESSAGE_USER_ID, messageBean.getMessageUserId());
        mDb.insert(TB_SYSTEM_MESSAGE, null, valuse);
        mDb.close();
    }

    /**
     * 删除一条消息
     *
     * @param messageBean
     */
    public void deleteMessage(MessageBean messageBean) {
        mDb = getSQLiteConnect();
        mDb.delete(TB_SYSTEM_MESSAGE, FIELD_MESSAGE_ID + " like ? and " + FIELD_MESSAGE_TYPE + " like ?", new String[]{String.valueOf(messageBean.getMessageId()), messageBean.getMessageType()});
        mDb.close();
    }
}
