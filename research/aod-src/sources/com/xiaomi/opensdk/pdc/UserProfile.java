package com.xiaomi.opensdk.pdc;

/* JADX INFO: loaded from: classes2.dex */
public class UserProfile extends Profile {
    public long birthday;
    public Gender gender;

    public enum Gender {
        MALE,
        FEMALE
    }

    public boolean equals(Object obj) {
        if (obj instanceof UserProfile) {
            UserProfile userProfile = (UserProfile) obj;
            if (this.birthday == userProfile.birthday && this.gender.equals(userProfile.gender)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return "Profile [birthday=" + this.birthday + ", gender=" + this.gender + "]";
    }
}
