package com.masaibar.smartclipboard.entities;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.OnConflict;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class ClipboardData {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public long time;

    @Column(unique = true, indexed = true, uniqueOnConflict = OnConflict.REPLACE)
    public String text;

    /**
     * 文字列長を返す
     */
    public int getLength() {
        return text.length();
    }
}
