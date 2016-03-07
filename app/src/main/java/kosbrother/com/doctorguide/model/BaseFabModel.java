package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.R;

public class BaseFabModel {

    public int getFabDrawableId(boolean opened) {
        return opened ? R.mipmap.ic_close : R.mipmap.ic_fab;
    }

}
