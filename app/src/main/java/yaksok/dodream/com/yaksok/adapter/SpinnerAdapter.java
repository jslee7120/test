package yaksok.dodream.com.yaksok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;
    private LayoutInflater layoutInflater;

    public SpinnerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
            return data.get(position);

    }

    @Override
    public long getItemId(int position) {
            return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
