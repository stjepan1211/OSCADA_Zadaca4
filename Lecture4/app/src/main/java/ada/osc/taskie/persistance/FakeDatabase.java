package ada.osc.taskie.persistance;

import java.util.ArrayList;
import java.util.List;

import ada.osc.taskie.model.Task;

public class FakeDatabase {

    List<Task> mTasks = new ArrayList<>();

    public void insert(Task task){
        mTasks.add(task);
    }

    public void delete(Task task){
        mTasks.remove(task);
    }

    public void update(Task task, int id){ mTasks.set(id, task); }

    public List<Task> getTasks() {
        return new ArrayList<>(mTasks);
    }

    public Task getTaskById(int id) { return mTasks.get(id); }
}
