package com.miui.maml;

import com.miui.maml.elements.BitmapProvider;
import com.miui.maml.elements.ScreenElement;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ObjectFactory {

    public static abstract class ActionCommandFactory extends ObjectFactoryBase<ActionCommandFactory> {
        public static final String NAME = "ActionCommand";

        public ActionCommandFactory() {
            super("ActionCommand");
        }

        public final ActionCommand create(ScreenElement screenElement, Element element) {
            ActionCommand actionCommandDoCreate = doCreate(screenElement, element);
            if (actionCommandDoCreate != null) {
                return actionCommandDoCreate;
            }
            T t = this.mOld;
            if (t == 0) {
                return null;
            }
            return ((ActionCommandFactory) t).create(screenElement, element);
        }

        public abstract ActionCommand doCreate(ScreenElement screenElement, Element element);
    }

    public static abstract class BitmapProviderFactory extends ObjectFactoryBase<BitmapProviderFactory> {
        public static final String NAME = "BitmapProvider";

        public BitmapProviderFactory() {
            super(NAME);
        }

        public final BitmapProvider create(ScreenElementRoot screenElementRoot, String str) {
            BitmapProvider bitmapProviderDoCreate = doCreate(screenElementRoot, str);
            if (bitmapProviderDoCreate != null) {
                return bitmapProviderDoCreate;
            }
            T t = this.mOld;
            if (t == 0) {
                return null;
            }
            return ((BitmapProviderFactory) t).create(screenElementRoot, str);
        }

        public abstract BitmapProvider doCreate(ScreenElementRoot screenElementRoot, String str);
    }

    public static abstract class ObjectFactoryBase<T extends ObjectFactory> extends ObjectFactory {
        private String mName;
        protected T mOld;

        public ObjectFactoryBase(String str) {
            this.mName = str;
        }

        @Override // com.miui.maml.ObjectFactory
        public String getName() {
            return this.mName;
        }

        @Override // com.miui.maml.ObjectFactory
        public T getOld() {
            return this.mOld;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.miui.maml.ObjectFactory
        public void setOld(ObjectFactory objectFactory) {
            this.mOld = objectFactory;
        }
    }

    public abstract String getName();

    public abstract ObjectFactory getOld();

    public abstract void setOld(ObjectFactory objectFactory);
}
