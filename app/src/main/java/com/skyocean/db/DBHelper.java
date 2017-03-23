package com.skyocean.db;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by DY on 2016/12/2.
 */

public class DBHelper {

    DbManager.DaoConfig m_daoConfig = new DbManager.DaoConfig()
            .setDbName("db_app")
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {//对应版本升级时应做的工作
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                }
            });



    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     */
    private static class SingletonHolder {
        private static DBHelper instance = new DBHelper();
    }

    /**
     * 私有的构造函数,防止外部构造
     */
    private DBHelper() {
    }

    /**
     * 单例的唯一对外访问接口，这种单例写法，既能保证多线程访问安全，又不会太影响效率
     * @return 自身
     */
    public static DBHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 方便外部创建数据库链接时使用（部分数据结构对象执行关联查询时需要用到）
     * @return
     */
    public DbManager getDbManager(){
        return x.getDb(m_daoConfig);
    }
    /**
     * 设置DB文件存储路径，应在启动页中调用此函数并传入程序可读写路径，否则将默认写到sd卡根目录下
     * @param strPath
     */
    public void setDbDir(String strPath){
        m_daoConfig.setDbDir(new File(strPath));
    }

    //TODO 下面是接口与业务有关的public函数


}
