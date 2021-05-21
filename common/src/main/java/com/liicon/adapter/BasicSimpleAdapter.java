package com.liicon.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BasicSimpleAdapter extends SimpleAdapter {
    private int[] mTo;
    private String[] mFrom;

    private List<? extends Map<String, ?>> mData;

    private int mResource;
    private LayoutInflater mInflater;
	private BaseViewBinder mViewBinder;

    public BasicSimpleAdapter(Context context, List<? extends Map<String, ?>> data,
            int resource, String[] from, int[] to) {
    	super(context, data, resource, from, to);
        mData = data;
        mResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public final View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    private View createViewFromResource(int position, View convertView,
            ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        bindView(position, v);
        return v;
    }

    private void bindView(int position, View viewParent) {
        final Map<String, ?> dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }

        final BaseViewBinder binder = getBaseViewBinder();
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View view = viewParent.findViewById(to[i]);
            if (view != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }

                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(view, data, viewParent, dataSet);
                }

                if (!bound) {
                    if (view instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) view).setChecked((Boolean) data);
                        } else if (view instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) view, text);
                        } else {
                            throw new IllegalStateException(view.getClass().getName() +
                                    " should be bound to a Boolean, not a " +
                                    (data == null ? "<unknown type>" : data.getClass()));
                        }
                    } else if (view instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        setViewText((TextView) view, text);
                    } else if (view instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) view, (Integer) data);                            
                        } else {
                            setViewImage((ImageView) view, text);
                        }
                    } else {
                        throw new IllegalStateException(view.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
        }
    }
    
    public List<? extends Map<String, ?>> getData() {
    	return mData;
    }
    
//    public void setData(List<? extends Map<String, ?>> data) {
//    	mData = data;
//    }

    public BaseViewBinder getBaseViewBinder() {
        return mViewBinder;
    }

    public void setBaseViewBinder(BaseViewBinder viewBinder) {
        mViewBinder = viewBinder;
    }

    public interface BaseViewBinder {
        boolean setViewValue(View view, Object data, View viewParent, Map<String, ?> dataSet);
    }

}
