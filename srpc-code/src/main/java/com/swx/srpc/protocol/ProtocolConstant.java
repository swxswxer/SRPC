package com.swx.srpc.protocol;

/**
 * 协议常量
 */
public interface ProtocolConstant {
    //消息长度
    int MESSAGE_HEADER_LENGTH = 17;
    //协议魔数
    byte PROTOCOL_MAGIC = 0x1;
    //协议版本号
    byte PROTOCOL_VERSION = 0x1;
}
