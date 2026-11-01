package androidx.constraintlayout.core.parser;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class CLKey extends CLContainer {
    public static ArrayList sections;

    static {
        ArrayList arrayList = new ArrayList();
        sections = arrayList;
        arrayList.add("ConstraintSets");
        sections.add("Variables");
        sections.add("Generate");
        sections.add("Transitions");
        sections.add("KeyFrames");
        sections.add("KeyAttributes");
        sections.add("KeyPositions");
        sections.add("KeyCycles");
    }
}
