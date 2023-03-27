package com.dlg.diary.domain.email;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lingui
 * @Date 2023/3/24 18:34
 */
@Getter
public enum FileType {

    DIARY(new String[]{"txt"}),
    PICTURE(new String[]{"jpg","png","jpeg"}),
    VOICE(new String[]{"mp3","mp4"}),
    OTHER(new String[]{"other"});

    private final String [] code;

    private final List<String> codes;

    FileType(String[] code) {
        this.code = code;
        this.codes = Arrays.asList(code);
    }

    public static FileType name2item(String name) {
        for (FileType item:FileType.values()){
            for (String code:item.getCode()){
                if (name.endsWith(code)){
                    return item;
                }
            }
        }
        return OTHER;
    }
}
