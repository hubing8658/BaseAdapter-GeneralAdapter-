package com.isoftstone.generaladapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 通用Adapter基类，其他要实现Adapter的只要继承此类，实现convert方法即可
 * 
 * @author hubing
 * @version 1.0.0 2015-4-10
 */
public abstract class GeneralAdapter<E> extends BaseAdapter {

	private List<E> data;
	private int resource; // ListView中Item布局id
	private Context ctx;

	private final Object mLock = new Object();

	/**
	 * 
	 * @param resource
	 *            item布局文件
	 * @param data
	 */
	public GeneralAdapter(Context ctx, int resource, List<E> data) {
		this.ctx = ctx;
		this.resource = resource;
		setData(data);
	}

	/**
	 * 设置Adapter中的数据
	 * 
	 * @param data
	 */
	public void setData(List<E> data) {
		synchronized (mLock) {
			if (data == null) {
				data = new ArrayList<E>();
			}
		}
		this.data = data;
	}

	public List<E> getData() {
		return data;
	}

	/**
	 * 向Adapter中List添加单个对象
	 * 
	 * @author hubing
	 * @param object
	 */
	public void add(E object) {
		synchronized (mLock) {
			if (data != null) {
				data.add(object);
			}
		}
	}

	/**
	 * 向Adapter中List末尾追加列表数据
	 * 
	 * @param data
	 */
	public void addAll(Collection<? extends E> collection) {
		synchronized (mLock) {
			if (data != null) {
				data.addAll(data);
			}
		}
	}

	/**
	 * Inserts the specified object at the specified index in the array.
	 * 
	 * @param object
	 *            The object to insert into the array.
	 * @param index
	 *            The index at which the object must be inserted.
	 */
	public void insert(E object, int index) {
		synchronized (mLock) {
			if (data != null) {
				data.add(index, object);
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * Removes the specified object from the array.
	 * 
	 * @param object
	 *            The object to remove.
	 */
	public void remove(E object) {
		synchronized (mLock) {
			if (data != null) {
				data.remove(object);
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * Remove all elements from the list.
	 */
	public void clear() {
		synchronized (mLock) {
			if (data != null) {
				data.clear();
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * Returns the context associated with this array adapter. The context is
	 * used to create views from the resource passed to the constructor.
	 * 
	 * @return The Context associated with this adapter.
	 */
	public Context getContext() {
		return ctx;
	}

	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public E getItem(int position) {
		return data.get(position);
	}

	/**
	 * Returns the position of the specified item in the array.
	 * 
	 * @param item
	 *            The item to retrieve the position of.
	 * 
	 * @return The position of the specified item.
	 */
	public int getPosition(E item) {
		return data.indexOf(item);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.get(ctx, resource, convertView);
		convert(holder, getItem(position), position);
		return holder.getConvertView();
	}

	/**
	 * 实现此方法，完成item界面设置
	 */
	public abstract void convert(ViewHolder holder, E item, int position);

	/**
	 * 
	 * @author hubing
	 * @version 1.0.0 2015-4-10
	 */

	public static class ViewHolder {

		private View convertView;
		private SparseArray<View> views;

		private ViewHolder(Context ctx, int resource) {
			convertView = LayoutInflater.from(ctx).inflate(resource, null);
			convertView.setTag(this);
			views = new SparseArray<View>();
		}

		/**
		 * 获取ViewHoler
		 * 
		 * @param ctx
		 * @param resource
		 * @param convertView
		 * @return
		 */
		public static ViewHolder get(Context ctx, int resource, View convertView) {
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder(ctx, resource);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			return vh;
		}

		public View getConvertView() {
			return this.convertView;
		}

		/**
		 * 从ViewHoler中获取已经保持的View
		 * 
		 * @param resId
		 * @return
		 */
		public View getView(int widgetId) {
			View view = views.get(widgetId);
			if (view == null) {
				view = convertView.findViewById(widgetId);
				views.append(widgetId, view);
			}
			return view;
		}

	}

}