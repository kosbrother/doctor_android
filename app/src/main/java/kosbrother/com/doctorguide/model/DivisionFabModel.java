package kosbrother.com.doctorguide.model;

public class DivisionFabModel {

    private int lastPagePosition = 0;

    public void setLastPagePosition(int changedPosition) {
        lastPagePosition = changedPosition;
    }

    public boolean showAddDoctor(int changedPosition) {
        return changedPosition == 0;
    }

    public boolean showAddComment(int changedPosition) {
        return changedPosition == 1 && lastPagePosition == 0;
    }
}
