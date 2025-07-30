package com.github.therealguru.totemfletching.overlay;

import lombok.Getter;

@Getter
public enum TotemFonts {
    RUNESCAPE("RuneScape"),
    REGULAR("RS Regular"),
    ARIAL("Arial"),
    TIMES_NEW_ROMAN("Times New Roman"),
    VERDANA("Verdana"),
    CAMBRIA("Cambria"),
    ROCKWELL("Rockwell");

    private final String fontName;

    public String toString() {
        return this.fontName;
    }

    TotemFonts(String fontName) {
        this.fontName = fontName;
    }
}
