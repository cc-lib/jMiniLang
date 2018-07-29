package com.bajdcc.LALR1.grammar.runtime.service

import com.bajdcc.LALR1.grammar.runtime.RuntimeObject
import com.bajdcc.LALR1.grammar.runtime.data.RuntimeArray

/**
 * 【运行时】运行时用户服务接口
 *
 * @author bajdcc
 */
interface IRuntimeUserService {

    /**
     * 获取服务
     * @return 管道服务
     */
    val pipe: IRuntimeUserPipeService

    /**
     * 获取服务
     * @return 共享服务
     */
    val share: IRuntimeUserShareService

    /**
     * 获取服务
     * @return 文件服务
     */
    val file: IRuntimeUserFileService

    interface IRuntimeUserPipeService {
        /**
         * 读取管道
         * @param id 句柄
         * @return 读取的对象
         */
        fun read(id: Int): RuntimeObject

        /**
         * 写入管道
         * @param id 句柄
         * @param obj 写入的对象
         * @return 是否成功
         */
        fun write(id: Int, obj: RuntimeObject): Boolean
    }

    interface IRuntimeUserShareService {
        /**
         * 获取共享
         * @param id 句柄
         * @return 共享对象
         */
        operator fun get(id: Int): RuntimeObject?

        /**
         * 设置共享
         * @param id 句柄
         * @param obj 共享对象
         * @return 上次保存的内容
         */
        operator fun set(id: Int, obj: RuntimeObject?): RuntimeObject?

        /**
         * 锁定共享
         * @param id 句柄
         * @return 是否成功
         */
        fun lock(id: Int): Boolean

        /**
         * 解锁共享
         * @param id 句柄
         * @return 是否成功
         */
        fun unlock(id: Int): Boolean
    }

    interface IRuntimeUserFileService {

        /**
         * 查询文件
         * @param id 句柄
         * @return 0-不存在，1-文件，2-文件夹
         */
        fun queryFile(id: Int): Long

        /**
         * 创建文件
         * @param id 句柄
         * @return 是否成功
         */
        fun createFile(id: Int): Boolean

        /**
         * 删除文件
         * @param id 句柄
         * @return 是否成功
         */
        fun deleteFile(id: Int): Boolean

        /**
         * 读取文件
         * @param id 句柄
         * @return 是否成功
         */
        fun readFile(id: Int): ByteArray?

        /**
         * 写入文件
         * @param id 句柄
         * @param data 数据
         * @param overwrite 是否覆盖
         * @param createIfNotExist 是否自动创建
         * @return 0-成功，-1:自动创建失败，-2:文件不存在，-3:目标不是文件
         */
        fun writeFile(id: Int, data: ByteArray, overwrite: Boolean, createIfNotExist: Boolean): Long
    }

    /**
     * 创建用户服务句柄
     *
     * @param name 用户服务名称
     * @param page 创建时的页面
     * @return 用户服务句柄
     */
    fun create(name: String, page: String): Int

    /**
     * 销毁用户服务
     */
    fun destroy()

    /**
     * 销毁用户服务句柄
     * @param id 用户服务句柄
     * @return 是否成功
     */
    fun destroy(id: Int): Boolean

    /**
     * 获取列表
     *
     * @param api 是否API调用
     * @return 列表
     */
    fun stat(api: Boolean): RuntimeArray
}
