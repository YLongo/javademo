package ch08;

import com.alibaba.fastjson.JSON;

public interface Serializer {

    /**
     * JSON序列化
     */
    byte JSON_SERIALIZER = 1;
    
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlogrithm();

    /**
     * Java对象专程二进制数据
     */
    byte[] serialize(Object object);
    
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
