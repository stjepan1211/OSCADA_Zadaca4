package ada.osc.taskie.helpers;


import java.util.Comparator;

import ada.osc.taskie.model.Task;

public class TaskSortComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        int f = o1.getPriority().compareTo(o2.getPriority());
        return (f != 0) ? f : o1.getPriority().compareTo(o2.getPriority());
    }
}

