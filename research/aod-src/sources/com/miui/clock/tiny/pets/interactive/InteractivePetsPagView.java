package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.util.AttributeSet;
import com.miui.clock.tiny.pets.PetsPagView;
import java.util.EnumSet;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsPagView extends PetsPagView {
    public HashMap mPetsActionCountMap;
    public int region;

    public InteractivePetsPagView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.region = 0;
        this.mPetsActionCountMap = new HashMap();
        initRegionActionCount();
    }

    public final String getActionKey(int i, int i2) {
        return "" + i + InteractivePetsStyle.PetsRegion.getRegion(i2);
    }

    public final void initRegionActionCount() {
        for (InteractivePetsStyle.InteractivePetsTypeInfo interactivePetsTypeInfo : EnumSet.allOf(InteractivePetsStyle.InteractivePetsTypeInfo.class)) {
            this.mPetsActionCountMap.put(getActionKey(interactivePetsTypeInfo.type, InteractivePetsStyle.PetsRegion.Switch.region), Integer.valueOf(interactivePetsTypeInfo.switchCount));
            this.mPetsActionCountMap.put(getActionKey(interactivePetsTypeInfo.type, InteractivePetsStyle.PetsRegion.Below.region), Integer.valueOf(interactivePetsTypeInfo.belowCount));
            this.mPetsActionCountMap.put(getActionKey(interactivePetsTypeInfo.type, InteractivePetsStyle.PetsRegion.Top.region), Integer.valueOf(interactivePetsTypeInfo.topCount));
        }
    }
}
