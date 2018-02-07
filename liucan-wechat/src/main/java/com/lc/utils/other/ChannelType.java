package com.lc.utils.other;

/**
 * Created by feihaitao on 2017/7/25.
 * 渠道类型
 */
public enum ChannelType {
    CHANNEL("channel", 1),APK("apk", 2),DIVISION_CHANNEL("division_channel", 3),
    STORE_CHANNEL("store_channel", 4),WHOLE_PROCESS("whole_process", 5);

    private String name;
    private int index;


    private ChannelType(String name, int index) {
        this.name = name;
        this.index = index;
    }


    public static String getName(int index) {
        for (ChannelType c : ChannelType .values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static int  getCode(String name) {
        for (ChannelType c : ChannelType .values()) {
            if (c.getName().equals(name) ) {
                return c.index;
            }
        }
        return 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
