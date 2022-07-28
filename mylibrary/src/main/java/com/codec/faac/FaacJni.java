package com.codec.faac;

public class FaacJni {

    static {
        System.loadLibrary("faac-jni");
    }

    /**
     * 初始化，打开用于AAC编码的Handle
     * @param sampleRate，如：8000
     * @param bitRate, Bit速率：
     *                 1. 设置为sampleRate的值，这要求【音频样本】为【最大输入的音频样本inputSampleArray[1]】；
     *                    否则声音会变调。
     *                 2. 【音频样本】值设置在[inputSampleArray[0] , inputSampleArray[1]]范围
     * @param bitsPerSample 采样精度，如：16
     * @param numChannels 声道数量，如：1
     * @param isADTS ADTS是每帧数据带的解码信息。直播流要ADTS；存文件不需要。
     * @param inputSampleArray 这是输出参数，数组长度是2，
     *                         第一个元素是【AAC编码器一次最小需要输入的音频样本】；
     *                         第二个元素是【AAC编码器一次最大输入的音频样本】；
     */
    public native static void encOpen(long sampleRate, long bitRate, int bitsPerSample,
                                     int numChannels, boolean isADTS, long[] inputSampleArray);

    /**
     * 编码
     * @param pcm
     * @param pcmLength
     * @return
     */
    public native static byte[] encEncode(byte[] pcm,int pcmLength);

    /**
     * 获取AAC编码配置信息
     * @return @nullable
     */
    public native static byte[] getConfig();

    public native static void encClose();

}
