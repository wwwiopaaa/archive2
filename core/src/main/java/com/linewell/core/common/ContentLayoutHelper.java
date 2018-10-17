package com.linewell.core.common;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ContentLayoutHelper {
    private ContentLayoutHelper() {
        throw new IllegalAccessError();
    }

    public static int getLayoutResByAnnotation(Class<?> clazz) {
        if (clazz == null || clazz == Object.class) {
            return -1;
        }
        ContentLayout contentLayout = clazz.getAnnotation(ContentLayout.class);
        if (contentLayout != null) {
            return contentLayout.value();
        }

        return getLayoutResByAnnotation(clazz.getSuperclass());
    }

    public static void setContentView(BaseActivity activity) {
        int layoutRes = getLayoutResByAnnotation(activity.getClass());
        if (layoutRes > 0) {
            activity.setContentView(layoutRes);
        }
    }

    public static void setContentView(BaseFragment fragment) {
        int layoutRes = getLayoutResByAnnotation(fragment.getClass());
        if (layoutRes > 0) {
            fragment.setContentView(layoutRes);
        }
    }
}
