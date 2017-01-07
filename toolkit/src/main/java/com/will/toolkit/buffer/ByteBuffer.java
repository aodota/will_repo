/*
 * $Header: ByteBuffer.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-11-13 下午8:14:45
 * $Owner: will
 */
package com.will.toolkit.buffer;

import java.nio.ByteOrder;

/**
 * ByteBuffer
 * @author will
 * @version 1.0.0.0 2012-11-13 下午8:14:45
 */
public class ByteBuffer {
    /** array */
    protected final byte[] array;
    
    private int readerIndex;
    private int writerIndex;
    private ByteOrder byteOrder;
    
    /**
     * 构造函数
     * @param length
     */
    public ByteBuffer(int length) {
        this(new byte[length], 0, 0, ByteOrder.nativeOrder());
    }
    
    /**
     * 构造函数
     * @param array
     */
    public ByteBuffer(byte[] array) {
        this(array, 0, array.length, ByteOrder.nativeOrder());
    }
    
    /**
     * 构造函数
     * @param array
     * @param readerIndex
     * @param writerIndex
     */
    public ByteBuffer(byte[] array, int readerIndex, int writerIndex) {
        this(array, readerIndex, writerIndex, ByteOrder.nativeOrder());
    }

    /**
     * 构造函数
     * @param array
     * @param readerIndex
     * @param writerIndex
     */
    public ByteBuffer(byte[] array, int readerIndex, int writerIndex, ByteOrder byteOrder) {
        this.array = array;
        this.readerIndex = readerIndex;
        this.writerIndex = writerIndex;
        this.byteOrder = byteOrder;
    }
    
    /**
     * 获取读位置
     * @return
     * @version 1.0.0.0 2012-11-14 下午9:47:43
     */
    public int readerIndex() {
        return readerIndex;
    }

    /**
     * 获取写位置
     * @return
     * @version 1.0.0.0 2012-11-14 下午9:48:06
     */
    public int writerIndex() {
        return writerIndex;
    }
    
    /**
     * 获取数组
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:29:02
     */
    public byte[] array() {
        return this.array;
    }
    
    /**
     * 获取可以读的字节数
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:30:19
     */
    public int readableBytes() {
        return writerIndex - readerIndex;
    }
    
    /**
     * 写字节 -- 1个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:30:31
     */
    public void writeByte(int value) {
        setByte(writerIndex ++, value);
    }

    /**
     * 写Short型 -- 2个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:30:49
     */
    public void writeShort(int value) {
        setShort(writerIndex, value);
        writerIndex += 2;
    }

    /**
     * 写Medium -- 3个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:30:58
     */
    public void writeMedium(int value) {
        setMedium(writerIndex, value);
        writerIndex += 3;
    }

    /**
     * 写Int -- 4个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:31:32
     */
    public void writeInt(int value) {
        setInt(writerIndex, value);
        writerIndex += 4;
    }

    /**
     * 写Long -- 8个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:31:48
     */
    public void writeLong(long value) {
        setLong(writerIndex, value);
        writerIndex += 8;
    }

    /**
     * 写Char -- 2个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:32:00
     */
    public void writeChar(int value) {
        writeShort(value);
    }

    /**
     * 写Float -- 4个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:32:10
     */
    public void writeFloat(float value) {
        writeInt(Float.floatToRawIntBits(value));
    }

    /**
     * 写Double -- 8个字节
     * @param value
     * @version 1.0.0.0 2012-11-13 下午8:32:28
     */
    public void writeDouble(double value) {
        writeLong(Double.doubleToRawLongBits(value));
    }
    
    /**
     * 写Bytes
     * @param src
     * @param srcIndex
     * @param length
     * @version 1.0.0.0 2012-11-13 下午8:32:46
     */
    public void writeBytes(byte[] src, int srcIndex, int length) {
        setBytes(writerIndex, src, srcIndex, length);
        writerIndex += length;
    }

    /**
     * 写Bytes
     * @param src
     * @version 1.0.0.0 2012-11-13 下午8:32:53
     */
    public void writeBytes(byte[] src) {
        writeBytes(src, 0, src.length);
    }
    
    /**
     * 读取Bytes
     * @param dst
     * @param dstIndex
     * @param length
     * @version 1.0.0.0 2012-11-13 下午8:33:35
     */
    public void readBytes(byte[] dst, int dstIndex, int length) {
        checkReadableBytes(length);
        getBytes(readerIndex, dst, dstIndex, length);
        readerIndex += length;
    }

    /**
     * 读取Bytes
     * @param dst
     * @version 1.0.0.0 2012-11-13 下午8:33:48
     */
    public void readBytes(byte[] dst) {
        readBytes(dst, 0, dst.length);
    }
    
    /**
     * 读取字节 -- 1个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:34:10
     */
    public byte readByte() {
        if (readerIndex == writerIndex) {
            throw new IndexOutOfBoundsException("Readable byte limit exceeded: "
                    + readerIndex);
        }
        return getByte(readerIndex ++);
    }

    /**
     * 读取UnsignedByte -- 1个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:34:20
     */
    public short readUnsignedByte() {
        return (short) (readByte() & 0xFF);
    }

    /**
     * 读取Short -- 2个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:34:44
     */
    public short readShort(ByteOrder byteOrder) {
        checkReadableBytes(2);
        short v = getShort(readerIndex);
        readerIndex += 2;
        return v;
    }

    /**
     * 读取无符号型Short -- 2个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:34:53
     */
    public int readUnsignedShort(ByteOrder byteOrder) {
        return readShort(byteOrder) & 0xFFFF;
    }

    /**
     * 读取Medium -- 3个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:35:15
     */
    public int readMedium(ByteOrder byteOrder) {
        int value = readUnsignedMedium(byteOrder);
        if ((value & 0x800000) != 0) {
            value |= 0xff000000;
        }
        return value;
    }

    /**
     * 读取UnsignedMedium -- 3个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:35:28
     */
    public int readUnsignedMedium(ByteOrder byteOrder) {
        checkReadableBytes(3);
        int v = getUnsignedMedium(readerIndex);
        readerIndex += 3;
        return v;
    }

    /**
     * 读取Int -- 4个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:35:47
     */
    public int readInt(ByteOrder byteOrder) {
        checkReadableBytes(4);
        int v = getInt(readerIndex);
        readerIndex += 4;
        return v;
    }

    /**
     * 读取UnsignedInt -- 4个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:35:58
     */
    public long readUnsignedInt(ByteOrder byteOrder) {
        return readInt(byteOrder) & 0xFFFFFFFFL;
    }

    /**
     * 读取Long -- 8个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:36:13
     */
    public long readLong(ByteOrder byteOrder) {
        checkReadableBytes(8);
        long v = getLong(readerIndex);
        readerIndex += 8;
        return v;
    }

    /**
     * 读取Char -- 2个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:36:26
     */
    public char readChar(ByteOrder byteOrder) {
        return (char) readShort(byteOrder);
    }

    /**
     * 读取Float -- 4个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:36:34
     */
    public float readFloat(ByteOrder byteOrder) {
        return Float.intBitsToFloat(readInt(byteOrder));
    }

    /**
     * 读取Double -- 8个字节
     * @return
     * @version 1.0.0.0 2012-11-13 下午8:36:46
     */
    public double readDouble(ByteOrder byteOrder) {
        return Double.longBitsToDouble(readLong(byteOrder));
    }
    
    protected void setByte(int index, int value) {
        array[index] = (byte) value;
    }
    
    protected void setBytes(int index, byte[] src, int srcIndex, int length) {
        System.arraycopy(src, srcIndex, array, index, length);
    }
    
    protected byte getByte(int index) {
        return array[index];
    }
    
    protected void getBytes(int index, byte[] dst, int dstIndex, int length) {
        System.arraycopy(array, index, dst, dstIndex, length);
    }
    
    protected short getUnsignedByte(int index) {
        return (short) (getByte(index) & 0xFF);
    }

    protected int getUnsignedShort(int index) {
        return getShort(index) & 0xFFFF;
    }

    protected int getMedium(int index) {
        int value = getUnsignedMedium(index);
        if ((value & 0x800000) != 0) {
            value |= 0xff000000;
        }
        return value;
    }

    protected long getUnsignedInt(int index) {
        return getInt(index) & 0xFFFFFFFFL;
    }

    protected char getChar(int index) {
        return (char) getShort(index);
    }

    protected float getFloat(int index) {
        return Float.intBitsToFloat(getInt(index));
    }

    protected double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }
    
    protected void checkReadableBytes(int minimumReadableBytes) {
        if (readableBytes() < minimumReadableBytes) {
            throw new IndexOutOfBoundsException("Not enough readable bytes - Need "
                    + minimumReadableBytes + ", maximum is " + readableBytes());
        }
    }
    
    protected short getShort(int index) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            return (short) (array[index] << 8 | array[index + 1] & 0xFF);
        } else {
            return (short) (array[index] & 0xFF | array[index + 1] << 8);
        }
    }

    protected int getUnsignedMedium(int index) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            return  (array[index]     & 0xff) << 16 |
                    (array[index + 1] & 0xff) <<  8 |
                    array[index + 2] & 0xff;
        } else {
            return array[index] & 0xff |
                   (array[index + 1] & 0xff) <<  8 |
                   (array[index + 2] & 0xff) << 16;
        }
    }

    protected int getInt(int index) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            return  (array[index]     & 0xff) << 24 |
                    (array[index + 1] & 0xff) << 16 |
                    (array[index + 2] & 0xff) <<  8 |
                    array[index + 3] & 0xff;
        } else {
            return array[index] & 0xff |
                   (array[index + 1] & 0xff) <<  8 |
                   (array[index + 2] & 0xff) << 16 |
                   (array[index + 3] & 0xff) << 24;
        }
    }

    protected long getLong(int index) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            return  ((long) array[index]     & 0xff) << 56 |
                    ((long) array[index + 1] & 0xff) << 48 |
                    ((long) array[index + 2] & 0xff) << 40 |
                    ((long) array[index + 3] & 0xff) << 32 |
                    ((long) array[index + 4] & 0xff) << 24 |
                    ((long) array[index + 5] & 0xff) << 16 |
                    ((long) array[index + 6] & 0xff) <<  8 |
                    (long) array[index + 7] & 0xff;
        } else {
            return (long) array[index] & 0xff |
                   ((long) array[index + 1] & 0xff) <<  8 |
                   ((long) array[index + 2] & 0xff) << 16 |
                   ((long) array[index + 3] & 0xff) << 24 |
                   ((long) array[index + 4] & 0xff) << 32 |
                   ((long) array[index + 5] & 0xff) << 40 |
                   ((long) array[index + 6] & 0xff) << 48 |
                   ((long) array[index + 7] & 0xff) << 56;
        }
    }

    protected void setShort(int index, int value) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            array[index]     = (byte) (value >>> 8);
            array[index + 1] = (byte) value;
        } else {
            array[index]     = (byte) value;
            array[index + 1] = (byte) (value >>> 8);
        }
    }

    protected void setMedium(int index, int   value) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            array[index]     = (byte) (value >>> 16);
            array[index + 1] = (byte) (value >>> 8);
            array[index + 2] = (byte) value;
        } else {
            array[index]     = (byte) value;
            array[index + 1] = (byte) (value >>> 8);
            array[index + 2] = (byte) (value >>> 16);
        }
    }

    protected void setInt(int index, int   value) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            array[index]     = (byte) (value >>> 24);
            array[index + 1] = (byte) (value >>> 16);
            array[index + 2] = (byte) (value >>> 8);
            array[index + 3] = (byte) value;
        } else {
            array[index]     = (byte) value;
            array[index + 1] = (byte) (value >>> 8);
            array[index + 2] = (byte) (value >>> 16);
            array[index + 3] = (byte) (value >>> 24);
        }
    }

    protected void setLong(int index, long  value) {
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder)) {
            array[index]     = (byte) (value >>> 56);
            array[index + 1] = (byte) (value >>> 48);
            array[index + 2] = (byte) (value >>> 40);
            array[index + 3] = (byte) (value >>> 32);
            array[index + 4] = (byte) (value >>> 24);
            array[index + 5] = (byte) (value >>> 16);
            array[index + 6] = (byte) (value >>> 8);
            array[index + 7] = (byte) value;
        } else {
            array[index]     = (byte) value;
            array[index + 1] = (byte) (value >>> 8);
            array[index + 2] = (byte) (value >>> 16);
            array[index + 3] = (byte) (value >>> 24);
            array[index + 4] = (byte) (value >>> 32);
            array[index + 5] = (byte) (value >>> 40);
            array[index + 6] = (byte) (value >>> 48);
            array[index + 7] = (byte) (value >>> 56);
        }
    }
}
