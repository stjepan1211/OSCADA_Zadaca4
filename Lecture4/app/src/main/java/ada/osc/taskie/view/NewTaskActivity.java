package ada.osc.taskie.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import ada.osc.taskie.R;
import ada.osc.taskie.TaskRepository;
import ada.osc.taskie.model.Task;
import ada.osc.taskie.model.TaskPriority;
import ada.osc.taskie.helpers.StaticHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TASK_ID = "id";
    public static final String LOG_TAG = "mylog";

    TaskRepository mTaskRepository = TaskRepository.getInstance();

    @BindView(R.id.textview_newtask_title) TextView mMessage;
    @BindView(R.id.edittext_newtask_description) EditText mDescription;
    @BindView(R.id.edittext_newtask_title) EditText mTitle;
    @BindView(R.id.edittext_newtask_priority) EditText mPriority;
    @BindView(R.id.calendarview_deadline) CalendarView mDeadline;
    @BindView(R.id.button_newtask_create) Button mCreate;
    @BindView(R.id.button_newtask_update) Button mUpdate;

    int mSelectedDay = 0;
    int mSelectedMonth = 0;
    int mSelectedYear = 0;
    Integer taskId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);

        Intent startingIntent = this.getIntent();
        if(startingIntent.hasExtra(EXTRA_MESSAGE)){
            mCreate.setVisibility(View.VISIBLE);
            String title = startingIntent.getStringExtra(EXTRA_MESSAGE);
            mMessage.setText(title);
        }
        if(startingIntent.hasExtra(EXTRA_TASK_ID)){
            mUpdate.setVisibility(View.VISIBLE);
            taskId = startingIntent.getIntExtra(EXTRA_TASK_ID,0);
            setGuiValuesForUpdateTask(taskId);
        }

        mDeadline.setMinDate(System.currentTimeMillis());

        mDeadline.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //month = this month - 1 ??????
                month += 1;
                mSelectedDay = dayOfMonth;
                mSelectedMonth = month;
                mSelectedYear = year;
            }
        });
    }

    private void setGuiValuesForUpdateTask(Integer taskId) {
        Task task = mTaskRepository.getTaskById(taskId);
        mDescription.setText(task.getDescription());
        mTitle.setText(task.getTitle());
        mPriority.setText(String.valueOf(task.getPriority()));
    }


    @OnClick(R.id.button_newtask_create)
    public void returnNewTask(){

        try {
            Task task = createTask();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(TasksActivity.EXTRA_TASK, task);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
        catch (NullPointerException ex) {
            return;
        }
    }

    @OnClick(R.id.button_newtask_update)
    public void returnUpdatedTask(){

        try {
            Task task = createTask();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(TasksActivity.EXTRA_TASK_UPDATE, task);
            resultIntent.putExtra(TasksActivity.EXTRA_TASK_ID, taskId);
            setResult(RESULT_OK, resultIntent);
            Log.d(LOG_TAG, task.toString());
            finish();
        }
        catch (NullPointerException ex) {
            return;
        }
    }

    private Task createTask() {
        String title;
        String description;
        String priorityEntry;
        String deadlineDate;
        int priority;

        if(checkUserInput(mTitle.getText().toString(), R.id.edittext_newtask_title)) {
            title = mTitle.getText().toString();
        }
        else{
            throw new NullPointerException();
        }

        if(checkUserInput(mDescription.getText().toString(), R.id.edittext_newtask_description)) {
            description = mDescription.getText().toString();
        }
        else{
            throw new NullPointerException();
        }

        if(checkUserInput(mPriority.getText().toString(), R.id.edittext_newtask_priority)) {
            priorityEntry = mPriority.getText().toString();
            priority = Integer.parseInt(priorityEntry);
        }
        else{
            throw new NullPointerException();
        }

        if(checkUserInput(String.valueOf(mDeadline.getDate()), R.id.calendarview_deadline)) {
            StringBuilder builder = new StringBuilder();
            deadlineDate = builder.append(mSelectedDay)
                    .append(".")
                    .append(mSelectedMonth)
                    .append(".")
                    .append(mSelectedYear)
                    .append(".")
                    .toString();
        }
        else{
            throw new NullPointerException();
        }
        return new Task(title, description, TaskPriority.values()[0], deadlineDate);
    }

    private boolean checkUserInput(String inputValue, int viewControlId){
        switch (viewControlId) {
            case R.id.edittext_newtask_title:
                if (StaticHelper.checkRegex(inputValue)){
                    return true;
                }
                else {
                    StaticHelper.displayToast(getResources().getString(R.string.invalidTitleInputMessage),
                            this);
                    return false;
                }
            case R.id.edittext_newtask_description:
                if (StaticHelper.checkRegex(inputValue)){
                    return true;
                }
                else {
                    StaticHelper.displayToast(getResources().getString(R.string.invalidDescriptionInputMessage),
                            this);
                    return false;
                }
            case R.id.edittext_newtask_priority:
                if (StaticHelper.tryParseInt(inputValue)){
                    return true;
                }
                else {
                    StaticHelper.displayToast(getResources().getString(R.string.invalidNumberInputMessage),
                            this);
                    return false;
                }
            case R.id.calendarview_deadline:
                if (StaticHelper.isValidDate(mSelectedDay, mSelectedMonth, mSelectedYear)){
                    return true;
                }
                else {
                    StaticHelper.displayToast(getResources().getString(R.string.invalidDateInputMessage),
                            this);
                    return false;
                }
            default:
                return true;
        }
    }
}
