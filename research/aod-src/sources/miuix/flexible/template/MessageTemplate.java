package miuix.flexible.template;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import miuix.flexible.R;

/* JADX INFO: loaded from: classes2.dex */
public class MessageTemplate extends AbstractAreaMappingTemplate {
    @Override // miuix.flexible.template.AbstractAreaMappingTemplate
    public int getTemplateResId() {
        return getLevel() != 1 ? R.layout.template_message_large : R.layout.template_message_normal;
    }

    @Override // miuix.flexible.template.AbstractAreaMappingTemplate, miuix.flexible.template.IHyperCellTemplate
    public void onFinishInflate(@NonNull ViewGroup viewGroup) {
        super.onFinishInflate(viewGroup);
    }

    @Override // miuix.flexible.template.AbstractAreaMappingTemplate
    public void onFinishLayoutMapping(ViewGroup viewGroup) {
        super.onFinishLayoutMapping(viewGroup);
        ((ConstraintLayout.LayoutParams) viewGroup.findViewById(R.id.area_title).getLayoutParams()).setMarginStart(20);
    }
}
