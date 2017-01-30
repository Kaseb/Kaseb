package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 11/2/2016.
 */
public class TypesSettingAdapter extends CursorAdapter {

    String columnName;
    LayoutInflater mInflater;
    private String typePointer;

    public TypesSettingAdapter(Context context, Cursor c, int flags, String column) {
        super(context, c, flags);
        columnName = column;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item_state_type_setting, parent, false);
        TextView typeView = new TextView(context);

        typeView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        typeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        if (columnName.equals(KasebContract.State.COLUMN_STATE_POINTER))
            return view;
        return typeView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (columnName.equals(KasebContract.State.COLUMN_STATE_POINTER)) {
            TextView typeName = (TextView) view.findViewById(R.id.type_setting_state_name);
            ImageView starState = (ImageView) view.findViewById(R.id.state_color_star);
            typeName.setText(cursor.getString(cursor.getColumnIndex(columnName)));
            String selection = KasebContract.State._ID + " = ?";
            String[] selecArg = new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(KasebContract.State._ID)))};
            Cursor colorCursor = context.getContentResolver().query(KasebContract.State.CONTENT_URI,
                    new String[]{KasebContract.State._ID, KasebContract.State.COLUMN_STATE_COLOR}, selection, selecArg, null);
            if (colorCursor.moveToFirst())
                starState.setColorFilter(colorCursor.getInt(colorCursor.getColumnIndex(KasebContract.State.COLUMN_STATE_COLOR)));
            colorCursor.close();
        } else {
            TextView textView = (TextView) view;
            typePointer = cursor.getString(cursor.getColumnIndex(columnName));
            textView.setText(typePointer);
        }
    }
}
