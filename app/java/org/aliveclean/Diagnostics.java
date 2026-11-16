package org.aliveclean;

/**
 * 诊断总开关。发布包必须为 false，见 {@code docs/kb/ai/02-开发硬性要求.md} §6.1。
 */
final class Diagnostics {
    /**
     * 诊断开关。true 时 Trace.enabled() 无条件放行（打 T01..T13）。
     * 正式包必须为 false —— 见 docs/kb/05 §10 检查清单。
     */
    static final boolean TRACE=false;
    private Diagnostics(){}
}
