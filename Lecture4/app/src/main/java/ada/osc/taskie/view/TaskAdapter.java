package ada.osc.taskie.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import ada.osc.taskie.R;
import ada.osc.taskie.model.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> mTasks;
    private TaskClickListener mListener;
    private ToggleClickListener mToggleListener;
    private TaskPriorityClickListener mTaskPriorityClickListener;

    public TaskAdapter(TaskClickListener listener, ToggleClickListener toggleListener, TaskPriorityClickListener taskPriorityClickListener) {
        mListener = listener;
        mTasks = new ArrayList<>();
        mToggleListener = toggleListener;
        mTaskPriorityClickListener = taskPriorityClickListener;

    }

    public void updateTasks(List<Task> tasks){
        mTasks.clear();
        mTasks.addAll(tasks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView, mListener, mToggleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task current = mTasks.get(position);
        holder.mTitle.setText(current.getTitle());
        holder.mDescription.setText(current.getDescription());
        holder.mDeadline.setText(current.getDeadline());

        int color = R.color.taskPriority_Unknown;
        switch (current.getPriority()){
            case LOW: color = R.color.taskpriority_low; break;
            case MEDIUM: color = R.color.taskpriority_medium; break;
            case HIGH: color = R.color.taskpriority_high; break;
        }

        holder.mPriority.setImageResource(color);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_task_title) TextView mTitle;
        @BindView(R.id.textview_task_description) TextView mDescription;
        @BindView(R.id.imageview_task_priority) ImageView mPriority;
        @BindView(R.id.textview_task_deadline) TextView mDeadline;
        @BindView(R.id.togglebutton_task_state) ToggleButton togglebutton_task_state;
        @BindView(R.id.textview_newtask_state) TextView textview_newtask_state;


        public TaskViewHolder(View itemView, TaskClickListener listener, ToggleClickListener toggleListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onTaskClick(){
            mListener.onClick(mTasks.get(getAdapterPosition()));
        }

        @OnLongClick
        public boolean onTaskLongClick(){
            mListener.onLongClick(mTasks.get(getAdapterPosition()));
            return true;
        }

        @OnClick(R.id.togglebutton_task_state)
        public void onToggleClick(){
            mToggleListener.onClick(togglebutton_task_state.isChecked());
            textview_newtask_state.setText(Boolean.toString(togglebutton_task_state.isChecked()));
        }

        @OnClick(R.id.imageview_task_priority)
        public void onPriorityClick(){
            mTaskPriorityClickListener.onClick(mTasks.get(getAdapterPosition()));

        }
    }
}
