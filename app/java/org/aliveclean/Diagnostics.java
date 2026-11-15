package org.aliveclean;

final class Diagnostics {
    /**
     * 诊断开关。true 时 Trace.enabled() 无条件放行（打 T01..T13）。
     * 正式包必须为 false —— 见 docs/kb/05 §10 检查清单。
     */
    static final boolean TRACE=false;
    private Diagnostics(){}
}
