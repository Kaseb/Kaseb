package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 11/3/2016.
 */
public class HeaderAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    ArrayList<Integer> mHeaderIcon;
    ArrayList<String> mHeaderTitle;
    ArrayList<String> mHeaderSummary = new ArrayList<>();

    public HeaderAdapter(Context context, ArrayList<Integer> headerIcon, ArrayList<String> headerTitle, ArrayList<String> headerSummary) {
        mContext = context;
        mHeaderIcon = headerIcon;
        mHeaderSummary = headerSummary;
        mHeaderTitle = headerTitle;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        int size = mHeaderIcon.size();
        return size;
    }

    @Override
    public String getItem(int position) {
        return mHeaderTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mHeaderIcon.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.list_item_pref_header,
                    parent, false);
            holder = new HeaderViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.pref_header_icon);
            holder.title = (TextView) view.findViewById(R.id.pref_header_title);
//            holder.summary = (TextView) view.findViewById(R.id.pref_header_summary);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (HeaderViewHolder) view.getTag();
        }
        holder.icon.setImageResource(mHeaderIcon.get(position));
        holder.title.setText(mHeaderTitle.get(position));
//        if (!(mHeaderSummary)) {
//            holder.summary.setVisibility(View.VISIBLE);
//            holder.summary.setText(mHeaderSummary.get(position));
//        } else {
//            holder.summary.setVisibility(View.GONE);
//        }
//        holder.summary.setText(mHeaderSummary.get(position));

        return view;
    }

    private static class HeaderViewHolder {
        ImageView icon;
        TextView title;
        TextView summary;
    }
}
