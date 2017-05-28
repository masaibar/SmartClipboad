package com.masaibar.smartclipboard.entities;


import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class FavoriteData {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public long time;

    @Column(indexed = true)
    public String text;

    public int getLength() {
        return text.length();
    }

    @Override
    public String toString() {
        return String.format("id = %s, time = %s, text = %s", id, time, text);
    }
}
