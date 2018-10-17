package com.linewell.mvp.contract;

import android.content.Context;
import android.support.annotation.NonNull;

public interface Contract {
    interface V<Presenter extends P>  {
        Context getContext();
    }

    interface P<View extends V>  {
        void attach(@NonNull View view);

        void detach();
    }

    interface M {

    }
}
