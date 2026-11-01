package androidx.transition;

import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class Scene {
    public Runnable mExitAction;
    public ViewGroup mSceneRoot;

    public static Scene getCurrentScene(ViewGroup viewGroup) {
        return (Scene) viewGroup.getTag(R.id.transition_current_scene);
    }

    public static void setCurrentScene(ViewGroup viewGroup, Scene scene) {
        viewGroup.setTag(R.id.transition_current_scene, scene);
    }

    public void exit() {
        Runnable runnable;
        if (getCurrentScene(this.mSceneRoot) != this || (runnable = this.mExitAction) == null) {
            return;
        }
        runnable.run();
    }
}
