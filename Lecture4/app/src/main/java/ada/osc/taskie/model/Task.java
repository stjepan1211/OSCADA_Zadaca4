package ada.osc.taskie.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    private TaskPriority mPriority;
    private String mTitle;
    private String mDescription;
    private String mDeadline;
    private boolean isFinished;

    public Task(String title, String description, TaskPriority priority) {
        mTitle = title;
        mDescription = description;
        mPriority = priority;
    }

    public Task(String mTitle, String mDescription, TaskPriority priority, String mDate) {
        this.mPriority = priority;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDeadline = mDate;
    }

    public Task(TaskPriority priority, String mTitle, String mDescription, String mDeadline, boolean isFinished) {
        this.mPriority = priority;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDeadline = mDeadline;
        this.isFinished = isFinished;
    }

    public TaskPriority getPriority() {
        return mPriority;
    }

    public void setPriority(TaskPriority priority) {
        mPriority = priority;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDeadline() { return mDeadline; }

    public void setDeadline(String mDeadline) { this.mDeadline = mDeadline; }

    public boolean isFinished() { return isFinished; }

    public void setFinished(boolean finished) { isFinished = finished; }

    @Override
    public String toString() {
        return "Task{" +
                "mPriority=" + mPriority +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mDeadline='" + mDeadline + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
