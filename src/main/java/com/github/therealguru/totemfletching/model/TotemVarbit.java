package com.github.therealguru.totemfletching.model;

import java.util.Arrays;

public enum TotemVarbit {
    BASE(0),
    BASE_CARVED(1),
    BASE_MULTILOC(2),
    LOW(3),
    MID(4),
    TOP(5),
    DECORATIONS(6),
    ANIMAL_1(7),
    ANIMAL_2(8),
    ANIMAL_3(9),
    DECAY(10),
    POINTS(11),
    MULTIANIMAL_A_1(12),
    MULTIANIMAL_B_1(13),
    MULTIANIMAL_C_1(14),
    MULTIANIMAL_D_1(15),
    MULTIANIMAL_E_1(16),
    ALL_MULTIANIMALS(17);

    private int relativeVarbit;

    TotemVarbit(int relativeVarbit) {
        this.relativeVarbit = relativeVarbit;
    }

    public int getRealVarbit(Totem totem) {
        return totem.getStartingVarbit() + this.relativeVarbit;
    }

    public static TotemVarbit getVarbit(Totem totem, int varbit) {
        return Arrays.stream(TotemVarbit.values())
                .filter(totemVarbit -> totemVarbit.getRealVarbit(totem) == varbit)
                .findFirst()
                .orElse(null);
    }
}
