package com.codec.faac;

import android.util.Log;

import java.util.Arrays;

/**
 * 辅助类
 */
public class FaacInputSamplesCacheBuffer {

    private static int mPcmSize = -1; //sample: 1600
    private static int mMaxInputSamples = -1;//sample: 2048

    private static int preOut = -1;
    private static int inputSamplePosition = -1;

    private static byte[] mOutPcmBuffer;
    private static byte[] mMaxInputSamplesBuffer;

    /**
     * 初始化参数
     * @param pcmFrameSize PCM一次采集音频样本大小
     * @param maxInputSamples AAC编码器一次最大输入的音频样本
     */
    public static void init(int pcmFrameSize, int maxInputSamples) {

        mPcmSize = pcmFrameSize;
        mMaxInputSamples = maxInputSamples;

        mOutPcmBuffer = new byte[mPcmSize];
        mMaxInputSamplesBuffer = new byte[mMaxInputSamples];

        Arrays.fill(mOutPcmBuffer, (byte) 0x00);
        Arrays.fill(mMaxInputSamplesBuffer, (byte) 0x00);

        preOut = 0;
        inputSamplePosition = 0;

    }

    public static byte[] get(){

        return mMaxInputSamplesBuffer;
    }

    /**
     * 输入PCM数据到缓存区
     * @param pcm 输入的pcm数组长度，必须是 init(int pcmFrameSize, int maxInputSamples)中的pcmFrameSize值
     * @return
     */
    public static boolean put(byte[] pcm){

        if(inputSamplePosition > mMaxInputSamples){
            Log.e("Error", "数组溢出异常");
            inputSamplePosition = 0;
            Arrays.fill(mMaxInputSamplesBuffer, (byte) 0x00);
            return false;
            // throw new RuntimeException("error: pos>max");
        }
        if(pcm.length != mPcmSize){
            Log.e("Error", "pcm.length != mPcmSize");
            return false;
        }

        if(preOut > 0){
            System.arraycopy(mOutPcmBuffer,0, mMaxInputSamplesBuffer, inputSamplePosition,preOut);
            inputSamplePosition = preOut;
            preOut = 0;
        }

        int offset = mMaxInputSamples - inputSamplePosition;
        if(offset > mPcmSize){
            System.arraycopy(pcm,0, mMaxInputSamplesBuffer, inputSamplePosition, mPcmSize);
            inputSamplePosition = inputSamplePosition + mPcmSize;
            return false;
        }else{
            preOut = mPcmSize - offset;
            System.arraycopy(pcm,0, mMaxInputSamplesBuffer, inputSamplePosition,offset);
            if(preOut > 0){
                System.arraycopy(pcm,offset, mOutPcmBuffer,0,preOut);
            }else {
                Arrays.fill(mOutPcmBuffer, (byte) 0x00);
            }
            inputSamplePosition = 0;
            return true;
        }
    }

}
