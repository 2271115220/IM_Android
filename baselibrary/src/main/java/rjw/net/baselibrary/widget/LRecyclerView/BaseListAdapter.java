package rjw.net.baselibrary.widget.LRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 封装adapter
 *
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {
    public String TAG="zhd";
    protected Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    public boolean isScroll = false;
    protected List<T> mDataList = new ArrayList<>();

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    public BaseListAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(getLayoutId(), parent, false);
        return new SuperViewHolder(itemView);
    }
    //局部刷新关键：带payload的这个onBindViewHolder方法必须实现
    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            bindData(holder, position, payloads);
        }

    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, final int position) {
        if (holder != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });
        }
        bindData(holder, position);
    }

    public abstract int getLayoutId();

    public abstract void bindData(SuperViewHolder holder, int position);

    public void bindData(SuperViewHolder holder, int position, List<Object> payloads) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> list, boolean isRefresh) {
        if (isRefresh) {
            setDataList(list);
        } else {
            int lastIndex = this.mDataList.size();
            if (this.mDataList.addAll(list)) {
                notifyItemRangeInserted(lastIndex, list.size());
            }
        }
    }

    public void addAll(Collection<T> list) {
        addAll(list, false);
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if (position != (getDataList().size())) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, this.mDataList.size() - position);
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public void setOnItemClickListener() {

    }
}
