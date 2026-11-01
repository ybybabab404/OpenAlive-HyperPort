package miuix.responsive.map;

/* JADX INFO: loaded from: classes2.dex */
public class ResponsiveState {
    public int mActualWindowHeight;
    public int mActualWindowWidth;
    public volatile int mEstimateCategory;
    public volatile int mResponsiveWindowType;
    public volatile int mScreenMode;
    public float mWindowDensity;
    public int mWindowHeightDp;
    public int mWindowWidthDp;

    public static class WindowInfoWrapper {
        public int windowCategory;
        public float windowDensity;
        public int windowHeight;
        public int windowHeightDp;
        public int windowMode;
        public int windowType;
        public int windowWidth;
        public int windowWidthDp;
    }

    public ResponsiveState() {
        setType(-1);
        setScreenMode(4103);
        setCategory(0);
        setWindowHeightDp(0);
        setWindowWidthDp(0);
        setActualWindowWidth(0);
        setActualWindowHeight(0);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ResponsiveState) {
            ResponsiveState responsiveState = (ResponsiveState) obj;
            int i = this.mActualWindowWidth;
            int i2 = responsiveState.mActualWindowWidth;
            boolean z = i == i2 || Math.abs(i - i2) == 1;
            int i3 = this.mActualWindowHeight;
            int i4 = responsiveState.mActualWindowHeight;
            boolean z2 = i3 == i4 || Math.abs(i3 - i4) == 1;
            if (this.mEstimateCategory == responsiveState.mEstimateCategory && this.mScreenMode == responsiveState.mScreenMode && z && z2) {
                return true;
            }
        }
        return false;
    }

    public int getActualWindowHeight() {
        return this.mActualWindowHeight;
    }

    public int getActualWindowWidth() {
        return this.mActualWindowWidth;
    }

    public int getCategory() {
        return this.mEstimateCategory;
    }

    public int getScreenMode() {
        return this.mScreenMode;
    }

    public int getType() {
        return this.mResponsiveWindowType;
    }

    public int getWindowHeightDp() {
        return this.mWindowHeightDp;
    }

    public int getWindowWidthDp() {
        return this.mWindowWidthDp;
    }

    public void setActualWindowHeight(int i) {
        this.mActualWindowHeight = i;
    }

    public void setActualWindowWidth(int i) {
        this.mActualWindowWidth = i;
    }

    public void setCategory(int i) {
        this.mEstimateCategory = i;
    }

    public void setScreenMode(int i) {
        this.mScreenMode = i;
    }

    public void setTo(ResponsiveState responsiveState) {
        if (responsiveState != null) {
            this.mResponsiveWindowType = responsiveState.mResponsiveWindowType;
            this.mScreenMode = responsiveState.mScreenMode;
            this.mWindowWidthDp = responsiveState.mWindowWidthDp;
            this.mWindowHeightDp = responsiveState.mWindowHeightDp;
            this.mActualWindowWidth = responsiveState.mActualWindowWidth;
            this.mActualWindowHeight = responsiveState.mActualWindowHeight;
            this.mEstimateCategory = responsiveState.mEstimateCategory;
        }
    }

    public void setType(int i) {
        this.mResponsiveWindowType = i;
    }

    public void setWindowDensity(float f) {
        this.mWindowDensity = f;
    }

    public void setWindowHeightDp(int i) {
        this.mWindowHeightDp = i;
    }

    public void setWindowWidthDp(int i) {
        this.mWindowWidthDp = i;
    }

    public void toScreenSpec(ScreenSpec screenSpec) {
        screenSpec.type = getType();
        screenSpec.category = getCategory();
        screenSpec.screenMode = getScreenMode();
        screenSpec.widthDp = getWindowWidthDp();
        screenSpec.heightDp = getWindowHeightDp();
        screenSpec.width = getActualWindowWidth();
        screenSpec.height = getActualWindowHeight();
    }

    public String toString() {
        return "ResponsiveState@" + hashCode() + "( type = " + this.mResponsiveWindowType + ", mode = " + this.mScreenMode + ", windowDensity " + this.mWindowDensity + ", wWidthDp " + this.mWindowWidthDp + ", wHeightDp " + this.mWindowHeightDp + ", wWidth " + this.mActualWindowWidth + ", wHeight " + this.mActualWindowHeight + " )";
    }

    public void updateFromWindowInfoWrapper(WindowInfoWrapper windowInfoWrapper) {
        setType(windowInfoWrapper.windowType);
        setScreenMode(windowInfoWrapper.windowMode);
        setWindowWidthDp(windowInfoWrapper.windowWidthDp);
        setWindowHeightDp(windowInfoWrapper.windowHeightDp);
        setActualWindowWidth(windowInfoWrapper.windowWidth);
        setActualWindowHeight(windowInfoWrapper.windowHeight);
        setWindowDensity(windowInfoWrapper.windowDensity);
        setCategory(windowInfoWrapper.windowCategory);
    }
}
