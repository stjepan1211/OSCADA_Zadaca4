package ada.osc.taskie.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import ada.osc.taskie.R;
import ada.osc.taskie.TaskRepository;
import ada.osc.taskie.helpers.TaskSortComparator;
import ada.osc.taskie.model.Task;
import ada.osc.taskie.model.TaskPriority;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksActivity extends AppCompatActivity {

    private static final String TAG = TasksActivity.class.getSimpleName();
    private static final int REQUEST_NEW_TASK = 10;
    private static final int REQUEST_UPDATE_TASK = 20;
    public static final String EXTRA_TASK = "newTask";
    public static final String EXTRA_TASK_UPDATE = "updateTask";
    public static final String EXTRA_TASK_ID = "taskId";

    private TaskRepository mTaskRepository = TaskRepository.getInstance();
    private TaskAdapter mTaskAdapter;

    @BindView(R.id.button_tasks_createNew) Button mCreateNewTask;
    @BindView(R.id.recyclerview_tasks) RecyclerView mTasksRecyclerView;
    @BindView(R.id.button_tasks_sort) Button button_tasks_sort;

    TaskClickListener mListener = new TaskClickListener() {
        @Override
        public void onClick(Task task) {
            toastTask(task);
        }

        @Override
        public void onLongClick(Task task) {
            mTaskRepository.delete(task);
            updateTasksDisplay();
        }
    };

    ToggleClickListener mToggleListener = new ToggleClickListener() {
        @Override
        public void onClick(boolean isChecked) {
            Toast.makeText(getApplicationContext(),Boolean.toString(isChecked), Toast.LENGTH_SHORT).show();
        }
    };

    TaskPriorityClickListener mPriorityListener = new TaskPriorityClickListener() {
        @Override
        public void onClick(Task task) {
            updateTaskPriority(task);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        int orientation = LinearLayoutManager.VERTICAL;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                this,
                orientation,
                false
        );

        RecyclerView.ItemDecoration decoration =
                new DividerItemDecoration(this, orientation);

        RecyclerView.ItemAnimator animator = new DefaultItemAnimator();

        mTaskAdapter = new TaskAdapter(mListener, mToggleListener, mPriorityListener);

        mTasksRecyclerView.setLayoutManager(layoutManager);
        mTasksRecyclerView.addItemDecoration(decoration);
        mTasksRecyclerView.setItemAnimator(animator);
        mTasksRecyclerView.setAdapter(mTaskAdapter);
    }

    @OnClick(R.id.button_tasks_createNew)
    public void startCreateTaskActivity(){

        Intent newTaskIntent = new Intent(this, NewTaskActivity.class);

        newTaskIntent.putExtra(NewTaskActivity.EXTRA_MESSAGE, "Bok ada osc");

        startActivityForResult(newTaskIntent, REQUEST_NEW_TASK);
    }

    @OnClick(R.id.button_tasks_sort)
    public void sortTasksAsc(){
        Collections.sort(mTaskRepository.getTasks(), new TaskSortComparator());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_NEW_TASK){
            if(resultCode == RESULT_OK){
                if (data.hasExtra(EXTRA_TASK)){
                    Task task = (Task) data.getSerializableExtra(EXTRA_TASK);
                    Log.d(TAG, task.getTitle());
                    mTaskRepository.save(task);
                    updateTasksDisplay();
                }
            }
        }
        else if(requestCode == REQUEST_UPDATE_TASK){
            if(resultCode == RESULT_OK){
                if (data.hasExtra(EXTRA_TASK_UPDATE)){
                    if (data.hasExtra(EXTRA_TASK_ID)) {
                        Task task = (Task) data.getSerializableExtra(EXTRA_TASK_UPDATE);
                        Integer taskId = data.getIntExtra(EXTRA_TASK_ID, 0);
                        Log.d(TAG, task.toString());
                        Log.d(TAG, Integer.toString(taskId));
                        mTaskRepository.update(task, taskId);
                        updateTasksDisplay();
                    }
                }
            }
        }
    }

    private void updateTasksDisplay() {
        List<Task> tasks = mTaskRepository.getTasks();
        mTaskAdapter.updateTasks(tasks);
        for (Task t : tasks){
            Log.d(TAG, t.getTitle());
        }
    }

    private void toastTask(Task t) {
        Toast.makeText(this, t.toString(),Toast.LENGTH_SHORT).show();
    }

    private void updateTaskPriority(Task task) {
        switch (task.getPriority()){
            case LOW: task.setPriority(TaskPriority.MEDIUM); break;
            case MEDIUM: task.setPriority(TaskPriority.HIGH); break;
            case HIGH: task.setPriority(TaskPriority.LOW); break;
        }
        updateTasksDisplay();
    }
}
