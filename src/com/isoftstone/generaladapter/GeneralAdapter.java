
package com.isoftstone.generaladapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 通用Adapter基类，其他要实现Adapter的只要继承此类，实现convert和getItemLayoutId方法即可
 * <p>
 * <li> {@link #getItemLayoutId} 实现此方法来设置item的布局资源id，可根据不同的item类型来设置不同的布局
 * <li> {@link #convert} 实现此方法来设置item界面数据
 * </p>
 * 
 * @author hubing
 * @version 1.0.0 2015-4-10
 */
public abstract class GeneralAdapter<E> extends BaseAdapter {

    /*
     * 通用Adapter基类 定义 通用的ViewHolder 类 提供子类实现convert方法填充元素。 可以根据设置 maxCount 来控制界面允许展示的条数。
     */

    private int maxCount = 1000; // ListView中最大显示数据条数

    /** E 对象列表 */
    private List<E> mData = new ArrayList<E>();

    /** 上下文 */
    private Context mContext;

    /** 锁对象 */
    private final Object mLock = new Object();

    /**
     * 构造函数
     * 
     * @param ctx 上下文
     * @param data 需要展示的列表数据
     */
    public GeneralAdapter(Context ctx, List<E> data) {
        this.mContext = ctx;
        setData(data);
    }

    /**
     * 设置Adapter中的数据
     * 
     * @param data 需要设置的列表数据
     */
    public void setData(List<E> data) {
        synchronized (mLock) {
            this.mData.clear();
            if (data != null && data.size() > 0) {
                this.mData.addAll(data);
                checkListSize();
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 获取当前adapter中的列表数据
     * 
     * @return 当前adapter中的列表数据
     */
    public List<E> getData() {
        return mData;
    }

    /**
     * 向Adapter中List添加单个对象
     * 
     * @param object 要追加的对象
     * @author hubing
     */
    public void add(E object) {
        synchronized (mLock) {
            if (object != null) {
                mData.add(object);
                checkListSize();
            }
        }
    }

    /**
     * 向Adapter中List末尾追加列表数据
     * 
     * @param collection 添加的集合
     * @author hubing
     */
    public void addAll(Collection<? extends E> collection) {
        synchronized (mLock) {
            if (collection != null) {
                mData.addAll(collection);
                checkListSize();
            }
        }
    }

    /**
     * 向Adapter中List末尾追加一个数组
     * 
     * @param arrays 添加数组到列表
     * @author hubing
     */
    public void addAll(E[] arrays) {
        synchronized (mLock) {
            if (arrays != null) {
                // Arrays.asList(arrays) 重新封装一层，支持对List 操作
                mData.addAll(new ArrayList<E>(Arrays.asList(arrays)));
                checkListSize();
            }
        }
    }

    /**
     * 向Adapter中List前追加列表数据
     * 
     * @param collection 需要添加的列表集合
     * @author hubing
     */
    public void addAllToHead(Collection<? extends E> collection) {
        synchronized (mLock) {
            if (collection != null) {
                mData.addAll(0, collection);
                checkListSize();
            }
        }
    }

    /**
     * Inserts the specified object at the specified index in the array.
     * 
     * @param object The object to insert into the array.
     * @param index The index at which the object must be inserted.
     * @author hubing
     */
    public void insert(E object, int index) {
        synchronized (mLock) {
            if (object != null) {
                mData.add(index, object);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Removes the specified object from the array.
     * 
     * @param object The object to remove.
     * @author hubing
     */
    public void remove(E object) {
        synchronized (mLock) {
            if (object != null) {
                mData.remove(object);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Remove all elements from the list.
     * 
     * @author hubing
     */
    public void clear() {
        synchronized (mLock) {
            if (mData != null) {
                mData.clear();
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     * 
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    public void sort(Comparator<? super E> comparator) {
        synchronized (mLock) {
            if (comparator != null) {
                Collections.sort(mData, comparator);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 检测当前List中的数据是否超过最大值maxCount
     * 
     * @author hubing
     */
    protected void checkListSize() {
        int totalCount = mData.size();
        if (totalCount > maxCount) {
            mData = mData.subList(totalCount - maxCount, totalCount);
        }
        this.notifyDataSetChanged();
    }

    /**
     * 获取ListView中显示的item最大条数
     * 
     * @return maxCount
     * @author hubing
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * 设置ListView中显示的item最大条数(此方法必需在setData方法之前调用才有效)
     * 
     * @param maxCount 最大条目数
     * @author hubing
     */
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * Returns the context associated with this array adapter.<br>
     * The context is used to create views from the resource passed to the constructor.
     * 
     * @return The Context associated with this adapter.
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return this.mData.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getItem(int position) {
        return mData.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     * 
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(E item) {
        return mData.indexOf(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        ViewHolder holder = ViewHolder.get(mContext, getItemLayoutId(itemViewType), convertView, parent);
        convert(holder, getItem(position), position, itemViewType);
        return holder.getConvertView();
    }

    /**
     * 获取Item布局资源id
     * 
     * @param itemViewType item的视图类型
     * @return 当前Item布局资源id
     * @author hubing
     * @see #getViewTypeCount()
     * @see #getItemViewType(int)
     */
    public abstract int getItemLayoutId(int itemViewType);

    /**
     * 实现此方法，完成item界面设置
     * 
     * @param holder ViewHolder对象
     * @param item getView中ListView对应的实体对象
     * @param position position The position of the item within the adapter's data set of the item whose view we want.
     * @param itemViewType item的视图类型
     */
    public abstract void convert(ViewHolder holder, E item, int position, int itemViewType);

    /**
     * ViewHolder类
     * 
     * @author hubing
     * @version 1.0.0 2015-4-10
     */
    public static class ViewHolder {

        /**
         * The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using. If it is not possible to convert this view
         * to display the correct data, this method can create a new view. Heterogeneous lists can specify their number of view types, so that this View is always of the right type
         * (see {@link #getViewTypeCount()} and {@link #getItemViewType(int)}).
         */
        private View convertView;

        /**
         * 缓存的Views
         */
        private SparseArray<View> views;

        private ViewHolder(Context ctx, int resource, ViewGroup parent) {
            convertView = LayoutInflater.from(ctx).inflate(resource, parent, false);
            convertView.setTag(this);
            views = new SparseArray<View>();
        }

        /**
         * 获取ViewHoler
         * 
         * @param ctx 上下文
         * @param resource 资源
         * @param convertView 征用的View
         * @return
         */
        public static ViewHolder get(Context ctx, int resource, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder(ctx, resource, parent);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            return vh;
        }

        /**
         * 获取ViewHolder绑定的convertView
         * 
         * @return convertView
         */
        public View getConvertView() {
            return this.convertView;
        }

        /**
         * 从ViewHoler中获取已经保持的View
         * 
         * @param widgetId 布局资源id
         * @return View item 对应的view
         */
        public <V extends View> V getView(int widgetId) {
            View view = views.get(widgetId);
            if (view == null) {
                view = convertView.findViewById(widgetId);
                views.append(widgetId, view);
            }
            return (V) view;
        }

    }

}
